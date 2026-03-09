package net.mcreator.loikvy.world.inventory;

import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.capabilities.Capabilities;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.mcreator.loikvy.init.LoikvyModMenus;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class ComputerAssemblyGUIMenu extends AbstractContainerMenu implements LoikvyModMenus.MenuAccessor {
	public final Map<String, Object> menuState = new HashMap<>() {
		@Override
		public Object put(String key, Object value) {
			if (!this.containsKey(key) && this.size() >= 6)
				return null;
			return super.put(key, value);
		}
	};
	public final Level world;
	public final Player entity;
	public int x, y, z;
	private ContainerLevelAccess access = ContainerLevelAccess.NULL;
	private IItemHandler internal;
	private final Map<Integer, Slot> customSlots = new HashMap<>();
	private boolean bound = false;
	private Supplier<Boolean> boundItemMatcher = null;
	private Entity boundEntity = null;
	private BlockEntity boundBlockEntity = null;

	public ComputerAssemblyGUIMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
	
	    super(LoikvyModMenus.COMPUTER_ASSEMBLY_GUI.get(), id);
	
	    this.entity = inv.player;
	    this.world = inv.player.level();
	    this.internal = new ItemStackHandler(3);
	
	    BlockPos pos = null;
	
	    if (extraData != null) {
	        pos = extraData.readBlockPos();
	        this.x = pos.getX();
	        this.y = pos.getY();
	        this.z = pos.getZ();
	        access = ContainerLevelAccess.create(world, pos);
	    }
	
	    if (pos != null) {
	        if (extraData.readableBytes() == 1) { // bound to item
	            byte hand = extraData.readByte();
	            ItemStack itemstack = hand == 0 ? this.entity.getMainHandItem() : this.entity.getOffhandItem();
	            this.boundItemMatcher = () -> itemstack == (hand == 0 ? this.entity.getMainHandItem() : this.entity.getOffhandItem());
	            IItemHandler cap = itemstack.getCapability(Capabilities.ItemHandler.ITEM);
	            if (cap != null) {
	                this.internal = cap;
	                this.bound = true;
	            }
	        } else if (extraData.readableBytes() > 1) { // bound to entity
	            extraData.readByte(); // drop padding
	            boundEntity = world.getEntity(extraData.readVarInt());
	            if (boundEntity != null) {
	                IItemHandler cap = boundEntity.getCapability(Capabilities.ItemHandler.ENTITY);
	                if (cap != null) {
	                    this.internal = cap;
	                    this.bound = true;
	                }
	            }
	        } else { // might be bound to block
	            boundBlockEntity = this.world.getBlockEntity(pos);
	            if (boundBlockEntity instanceof BaseContainerBlockEntity baseContainerBlockEntity) {
	                this.internal = new InvWrapper(baseContainerBlockEntity);
	                this.bound = true;
	            }
	        }
	    }
	
	    // Custom slots for your container
	    this.customSlots.put(0, this.addSlot(new SlotItemHandler(internal, 0, 40, 25))); //CPU
	    this.customSlots.put(1, this.addSlot(new SlotItemHandler(internal, 1, 40 + 18 * 1, 25))); //GPU
	    this.customSlots.put(2, this.addSlot(new SlotItemHandler(internal, 1, 40 + 18 * 2, 25))); //DRIVE
	
	    // Player inventory (standard layout)
	    addPlayerInventory(inv, 40, 94);
	}

	// Helper methods
	private void addPlayerInventory(Inventory playerInv, int startX, int startY) {
	    // Main inventory (3 rows of 9)
	    for (int row = 0; row < 3; row++) {
	        for (int col = 0; col < 9; col++) {
	            this.addSlot(new Slot(playerInv, col + (row + 1) * 9, startX + col * 18, startY + row * 18));
	        }
	    }
	    
	    // Hotbar (1 row of 9, 58 pixels below main inventory top)
	    for (int col = 0; col < 9; col++) {
	        this.addSlot(new Slot(playerInv, col, startX + col * 18, startY + 58));
	    }
	}
	
	private void addSlotGrid(IItemHandler inv, int startSlot, int rows, int cols, int startX, int startY) {
	    for (int row = 0; row < rows; row++) {
	        for (int col = 0; col < cols; col++) {
	            int slotIndex = startSlot + (row * cols) + col;
	            this.addSlot(new SlotItemHandler(inv, slotIndex, startX + col * 18, startY + row * 18));
	        }
	    }
	}
	
	private void addSlotRow(IItemHandler inv, int startSlot, int count, int startX, int startY) {
	    for (int i = 0; i < count; i++) {
	        this.addSlot(new SlotItemHandler(inv, startSlot + i, startX + i * 18, startY));
	    }
	}


	@Override
	public boolean stillValid(Player player) {
		if (this.bound) {
			if (this.boundItemMatcher != null)
				return this.boundItemMatcher.get();
			else if (this.boundBlockEntity != null)
				return AbstractContainerMenu.stillValid(this.access, player, this.boundBlockEntity.getBlockState().getBlock());
			else if (this.boundEntity != null)
				return this.boundEntity.isAlive();
		}
		return true;
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (index < 2) {
				if (!this.moveItemStackTo(itemstack1, 2, this.slots.size(), true))
					return ItemStack.EMPTY;
				slot.onQuickCraft(itemstack1, itemstack);
			} else if (!this.moveItemStackTo(itemstack1, 0, 2, false)) {
				if (index < 2 + 27) {
					if (!this.moveItemStackTo(itemstack1, 2 + 27, this.slots.size(), true))
						return ItemStack.EMPTY;
				} else {
					if (!this.moveItemStackTo(itemstack1, 2, 2 + 27, false))
						return ItemStack.EMPTY;
				}
				return ItemStack.EMPTY;
			}
			if (itemstack1.isEmpty()) {
				slot.setByPlayer(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(playerIn, itemstack1);
		}
		return itemstack;
	}

	@Override
	protected boolean moveItemStackTo(ItemStack p_38904_, int p_38905_, int p_38906_, boolean p_38907_) {
		boolean flag = false;
		int i = p_38905_;
		if (p_38907_) {
			i = p_38906_ - 1;
		}
		if (p_38904_.isStackable()) {
			while (!p_38904_.isEmpty() && (p_38907_ ? i >= p_38905_ : i < p_38906_)) {
				Slot slot = this.slots.get(i);
				ItemStack itemstack = slot.getItem();
				if (slot.mayPlace(itemstack) && !itemstack.isEmpty() && ItemStack.isSameItemSameComponents(p_38904_, itemstack)) {
					int j = itemstack.getCount() + p_38904_.getCount();
					int k = slot.getMaxStackSize(itemstack);
					if (j <= k) {
						p_38904_.setCount(0);
						itemstack.setCount(j);
						slot.set(itemstack);
						flag = true;
					} else if (itemstack.getCount() < k) {
						p_38904_.shrink(k - itemstack.getCount());
						itemstack.setCount(k);
						slot.set(itemstack);
						flag = true;
					}
				}
				if (p_38907_) {
					i--;
				} else {
					i++;
				}
			}
		}
		if (!p_38904_.isEmpty()) {
			if (p_38907_) {
				i = p_38906_ - 1;
			} else {
				i = p_38905_;
			}
			while (p_38907_ ? i >= p_38905_ : i < p_38906_) {
				Slot slot1 = this.slots.get(i);
				ItemStack itemstack1 = slot1.getItem();
				if (itemstack1.isEmpty() && slot1.mayPlace(p_38904_)) {
					int l = slot1.getMaxStackSize(p_38904_);
					slot1.setByPlayer(p_38904_.split(Math.min(p_38904_.getCount(), l)));
					slot1.setChanged();
					flag = true;
					break;
				}
				if (p_38907_) {
					i--;
				} else {
					i++;
				}
			}
		}
		return flag;
	}

	@Override
	public void removed(Player playerIn) {
		super.removed(playerIn);
		if (!bound && playerIn instanceof ServerPlayer serverPlayer) {
			if (!serverPlayer.isAlive() || serverPlayer.hasDisconnected()) {
				for (int j = 0; j < internal.getSlots(); ++j) {
					playerIn.drop(internal.getStackInSlot(j), false);
					if (internal instanceof IItemHandlerModifiable ihm)
						ihm.setStackInSlot(j, ItemStack.EMPTY);
				}
			} else {
				for (int i = 0; i < internal.getSlots(); ++i) {
					playerIn.getInventory().placeItemBackInInventory(internal.getStackInSlot(i));
					if (internal instanceof IItemHandlerModifiable ihm)
						ihm.setStackInSlot(i, ItemStack.EMPTY);
				}
			}
		}
	}

	@Override
	public Map<Integer, Slot> getSlots() {
		return Collections.unmodifiableMap(customSlots);
	}

	@Override
	public Map<String, Object> getMenuState() {
		return menuState;
	}
}