package net.mcreator.loikvy.entity;

import net.neoforged.neoforge.items.wrapper.EntityHandsInvWrapper;
import net.neoforged.neoforge.items.wrapper.EntityArmorInvWrapper;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.phys.AABB;

import net.mcreator.loikvy.world.inventory.CorpseInventoryGUIMenu;

import io.netty.buffer.Unpooled;

import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class PlayerCorpseEntity extends PathfinderMob {
	public static final EntityDataAccessor<Integer> DATA_age = SynchedEntityData.defineId(PlayerCorpseEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<String> DATA_PLAYER_NAME = SynchedEntityData.defineId(PlayerCorpseEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> DATA_PLAYER_UUID = SynchedEntityData.defineId(PlayerCorpseEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Long> DATA_death_time = SynchedEntityData.defineId(PlayerCorpseEntity.class, EntityDataSerializers.LONG);
	
	private GameProfile playerProfile;

	public PlayerCorpseEntity(EntityType<PlayerCorpseEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(true);
		setPersistenceRequired();

		this.setBoundingBox(this.makeBoundingBox());

		if (!world.isClientSide())
		{
        	this.entityData.set(DATA_death_time, world.getDayTime());
    	}
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(DATA_age, 0);
		builder.define(DATA_PLAYER_NAME, "");
		builder.define(DATA_PLAYER_UUID, "");
		builder.define(DATA_death_time, 0L);
	}

	@Override
	public void tick() {
    	super.tick();

    	int currentAge = this.entityData.get(DATA_age);
    	this.entityData.set(DATA_age, currentAge + 1);
	}

	public void setPlayerProfile(GameProfile profile) {
		this.playerProfile = profile;
		this.entityData.set(DATA_PLAYER_NAME, profile.getName());
		this.entityData.set(DATA_PLAYER_UUID, profile.getId().toString());
	}

	public GameProfile getPlayerProfile() {
		if (this.playerProfile == null && !this.entityData.get(DATA_PLAYER_UUID).isEmpty()) {
			String uuidStr = this.entityData.get(DATA_PLAYER_UUID);
			String name = this.entityData.get(DATA_PLAYER_NAME);
			this.playerProfile = new GameProfile(UUID.fromString(uuidStr), name);
		}
		return this.playerProfile;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public Vec3 getPassengerRidingPosition(Entity entity) {
		return super.getPassengerRidingPosition(entity).add(0, -0.35F, 0);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.generic.death"));
	}

	@Override
	public boolean hurt(DamageSource damagesource, float amount) {
		if (damagesource.getDirectEntity() instanceof Player)
			return false;
		if (damagesource.is(DamageTypes.DRAGON_BREATH))
			return false;
		if (damagesource.is(DamageTypes.WITHER) || damagesource.is(DamageTypes.WITHER_SKULL))
			return false;
		return super.hurt(damagesource, amount);
	}

	@Override
	protected AABB makeBoundingBox() {
    	float width = 0.6F;
    	float length = 1.8F;
    	float height = 0.6F;
    
    	float halfWidth = width / 2.0F;
    	float halfLength = length / 2.0F;
    
    	return new AABB(
        	this.getX() - halfWidth, this.getY(), this.getZ() - halfLength,
        	this.getX() + halfWidth, this.getY() + height, this.getZ() + halfLength
    	);
	}


	// Public getter method for age data
	public int getAge() {
		return this.entityData.get(DATA_age);
	}
	
	public int getDaysOld()
	{
    	long deathTime = this.entityData.get(DATA_death_time);
    	long currentTime = this.level().getDayTime();
    	long ticksElapsed = currentTime - deathTime;
    	
    	long dayLength = this.level().dimensionType().fixedTime().orElse(24000L);
    	
    	if (dayLength == 0L) {
    		dayLength = 24000L;
    	}

    	return (int)(ticksElapsed / dayLength);
	}

	private final ItemStackHandler inventory = new ItemStackHandler(36);
	private final CombinedInvWrapper combined = new CombinedInvWrapper(inventory, new EntityHandsInvWrapper(this), new EntityArmorInvWrapper(this));

	public CombinedInvWrapper getCombinedInventory() {
		return combined;
	}

	@Override
	protected void dropEquipment() {
		super.dropEquipment();
		for (int i = 0; i < inventory.getSlots(); ++i) {
			ItemStack itemstack = inventory.getStackInSlot(i);
			if (!itemstack.isEmpty() && !EnchantmentHelper.has(itemstack, EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP)) {
				this.spawnAtLocation(itemstack);
			}
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		
		compound.putInt("Dataage", this.entityData.get(DATA_age));
		compound.putLong("DeathTime", this.entityData.get(DATA_death_time));
		compound.put("InventoryCustom", inventory.serializeNBT(this.registryAccess()));
		
		if (this.playerProfile != null) {
			compound.putString("PlayerName", this.playerProfile.getName());
			compound.putString("PlayerUUID", this.playerProfile.getId().toString());
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		
		super.readAdditionalSaveData(compound);
		if (compound.contains("Dataage"))
			this.entityData.set(DATA_age, compound.getInt("Dataage"));
		if (compound.contains("DeathTime"))
			this.entityData.set(DATA_death_time, compound.getLong("DeathTime"));
		if (compound.get("InventoryCustom") instanceof CompoundTag inventoryTag)
			inventory.deserializeNBT(this.registryAccess(), inventoryTag);
		
		if (compound.contains("PlayerUUID") && compound.contains("PlayerName")) {
			this.playerProfile = new GameProfile(
				UUID.fromString(compound.getString("PlayerUUID")),
				compound.getString("PlayerName")
			);
			this.entityData.set(DATA_PLAYER_NAME, this.playerProfile.getName());
			this.entityData.set(DATA_PLAYER_UUID, this.playerProfile.getId().toString());
		}
	}

	@Override
	public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
		ItemStack itemstack = sourceentity.getItemInHand(hand);
		InteractionResult retval = InteractionResult.sidedSuccess(this.level().isClientSide());
		if (sourceentity instanceof ServerPlayer serverPlayer) {
			serverPlayer.openMenu(new MenuProvider() {
				@Override
				public Component getDisplayName() {
					return Component.literal("Player Corpse");
				}

				@Override
				public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
					FriendlyByteBuf packetBuffer = new FriendlyByteBuf(Unpooled.buffer());
					packetBuffer.writeBlockPos(sourceentity.blockPosition());
					packetBuffer.writeByte(0);
					packetBuffer.writeVarInt(PlayerCorpseEntity.this.getId());
					return new CorpseInventoryGUIMenu(id, inventory, packetBuffer);
				}
			}, buf -> {
				buf.writeBlockPos(sourceentity.blockPosition());
				buf.writeByte(0);
				buf.writeVarInt(this.getId());
			});
		}
		super.mobInteract(sourceentity, hand);
		return retval;
	}

	@Override
	public boolean canDrownInFluidType(FluidType type) {
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();
		Level world = this.level();
		Entity entity = this;
		return false;
	}

	@Override
	public void die(DamageSource damageSource) {
    	super.die(damageSource);
    	// Force removal
    	if (!this.level().isClientSide) {
        	this.remove(RemovalReason.KILLED);
    	}
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.1);
		builder = builder.add(Attributes.MAX_HEALTH, 10);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 1);
		builder = builder.add(Attributes.FOLLOW_RANGE, 1);
		builder = builder.add(Attributes.STEP_HEIGHT, 0.1);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 2);
		return builder;
	}

	public void copyInventoryFromPlayer(Player player)
	{
    CombinedInvWrapper corpseInv = this.getCombinedInventory();
    Inventory playerInv = player.getInventory();
    
    for (int i = 0; i < 36; i++) {
        ItemStack item = playerInv.items.get(i);
        if (!item.isEmpty()) {
            this.inventory.setStackInSlot(i, item.copy());
        }
    }
    
    for (int i = 0; i < 4; i++) {
        ItemStack armorPiece = playerInv.armor.get(i);
        if (!armorPiece.isEmpty()) {
            corpseInv.setStackInSlot(36 + 2 + i, armorPiece.copy());
        }
    }
    

    if (!playerInv.offhand.get(0).isEmpty()) {
        corpseInv.setStackInSlot(37, playerInv.offhand.get(0).copy()); // Offhand
    }
    
    playerInv.clearContent();
	}
}