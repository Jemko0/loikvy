/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.loikvy.init;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.GameRules;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class LoikvyModGameRules {
	public static GameRules.Key<GameRules.BooleanValue> DO_DREAMS;
	public static GameRules.Key<GameRules.IntegerValue> GAMBLING_SLOTS_TWO_MATCH_WIN_MULTIPLIER;
	public static GameRules.Key<GameRules.IntegerValue> GAMBLING_SLOTS_THREE_MATCH_MULTIPLIER;
	public static GameRules.Key<GameRules.IntegerValue> GAMBLING_MAX_BET;
	public static GameRules.Key<GameRules.IntegerValue> GAMBLING_MIN_BET;

	@SubscribeEvent
	public static void registerGameRules(FMLCommonSetupEvent event) {
		DO_DREAMS = GameRules.register("doDreams", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
		GAMBLING_SLOTS_TWO_MATCH_WIN_MULTIPLIER = GameRules.register("gamblingSlotsTwoMatchWinMultiplier", GameRules.Category.MISC, GameRules.IntegerValue.create(2));
		GAMBLING_SLOTS_THREE_MATCH_MULTIPLIER = GameRules.register("gamblingSlotsThreeMatchMultiplier", GameRules.Category.MISC, GameRules.IntegerValue.create(3));
		GAMBLING_MAX_BET = GameRules.register("gamblingMaxBet", GameRules.Category.MISC, GameRules.IntegerValue.create(5000));
		GAMBLING_MIN_BET = GameRules.register("gamblingMinBet", GameRules.Category.MISC, GameRules.IntegerValue.create(50));
	}
}