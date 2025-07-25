package net.mcreator.loikvy.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.Component;

import net.mcreator.loikvy.network.LoikvyModVariables;

import java.util.ArrayList;

public class DetermineTraitsProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double trait_chance = 0;
		trait_chance = Mth.nextDouble(RandomSource.create(), -1, 1);
		if (trait_chance < -0.5) {
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsAgoraphobic = true;
				_vars.syncPlayerVariables(entity);
			}
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsClaustrophobic = false;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (trait_chance > 0.5) {
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsAgoraphobic = false;
				_vars.syncPlayerVariables(entity);
			}
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsClaustrophobic = true;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (trait_chance > -0.5 && trait_chance < 0.5) {
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsAgoraphobic = false;
				_vars.syncPlayerVariables(entity);
			}
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsClaustrophobic = false;
				_vars.syncPlayerVariables(entity);
			}
		}
		trait_chance = Mth.nextDouble(RandomSource.create(), -1, 1);
		if (trait_chance < 0) {
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsScaredOfHeights = true;
				_vars.syncPlayerVariables(entity);
			}
		} else {
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsScaredOfHeights = false;
				_vars.syncPlayerVariables(entity);
			}
		}
		trait_chance = Mth.nextDouble(RandomSource.create(), -1, 1);
		if (trait_chance < -0.5) {
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsProneToInjury = true;
				_vars.syncPlayerVariables(entity);
			}
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsTough = false;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (trait_chance > 0.5) {
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsProneToInjury = true;
				_vars.syncPlayerVariables(entity);
			}
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsTough = false;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (trait_chance > -0.5 && trait_chance < 0.5) {
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsTough = false;
				_vars.syncPlayerVariables(entity);
			}
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsProneToInjury = false;
				_vars.syncPlayerVariables(entity);
			}
		}
		trait_chance = Mth.nextDouble(RandomSource.create(), -1, 1);
		if (trait_chance < -0.5) {
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsProneToSickness = true;
				_vars.syncPlayerVariables(entity);
			}
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsResistantToSickness = false;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (trait_chance > 0.5) {
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsProneToSickness = true;
				_vars.syncPlayerVariables(entity);
			}
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsResistantToSickness = false;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (trait_chance > -0.5 && trait_chance < 0.5) {
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsProneToSickness = false;
				_vars.syncPlayerVariables(entity);
			}
			{
				LoikvyModVariables.PlayerVariables _vars = entity.getData(LoikvyModVariables.PLAYER_VARIABLES);
				_vars.gPlayerIsResistantToSickness = false;
				_vars.syncPlayerVariables(entity);
			}
		}
		for (Entity entityiterator : new ArrayList<>(world.players())) {
			if (entity.hasPermissions(4)) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(("Prone To Sickness: " + entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerIsProneToSickness + " / " + "Resistant To Sickness: "
							+ entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerIsResistantToSickness + " / " + "Prone To Injury: " + entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerIsProneToInjury + " / " + "Tough: "
							+ entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerIsTough + " / " + "Scared Of Heights: " + entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerIsScaredOfHeights + " / " + "Agoraphobic: "
							+ entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerIsAgoraphobic + " / " + "Claustrophobic: " + entity.getData(LoikvyModVariables.PLAYER_VARIABLES).gPlayerIsClaustrophobic)), false);
			}
		}
	}
}