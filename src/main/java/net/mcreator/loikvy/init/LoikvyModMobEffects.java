/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.loikvy.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.registries.Registries;

import net.mcreator.loikvy.potion.TiredMobEffect;
import net.mcreator.loikvy.potion.SweatingMobEffect;
import net.mcreator.loikvy.potion.StinkingMobEffect;
import net.mcreator.loikvy.potion.StimulatedMobEffect;
import net.mcreator.loikvy.potion.SleepingMobEffect;
import net.mcreator.loikvy.potion.SickMobEffect;
import net.mcreator.loikvy.potion.SadMobEffect;
import net.mcreator.loikvy.potion.RestrictedMovementMobEffect;
import net.mcreator.loikvy.potion.ParalyzedMobEffect;
import net.mcreator.loikvy.potion.PanicMobEffect;
import net.mcreator.loikvy.potion.OverstimulatedMobEffect;
import net.mcreator.loikvy.potion.OverdosingMobEffect;
import net.mcreator.loikvy.potion.MinorInjuryMobEffect;
import net.mcreator.loikvy.potion.MiningEfficiencyMobEffect;
import net.mcreator.loikvy.potion.MajorInjuryMobEffect;
import net.mcreator.loikvy.potion.LoikvianMobEffect;
import net.mcreator.loikvy.potion.IckyMobEffect;
import net.mcreator.loikvy.potion.FoodPoisoningMobEffect;
import net.mcreator.loikvy.potion.ExertionMobEffect;
import net.mcreator.loikvy.potion.DrunkMobEffect;
import net.mcreator.loikvy.potion.DepressedMobEffect;
import net.mcreator.loikvy.potion.DeadMobEffect;
import net.mcreator.loikvy.potion.BleedingMobEffect;
import net.mcreator.loikvy.LoikvyMod;

public class LoikvyModMobEffects {
	public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(Registries.MOB_EFFECT, LoikvyMod.MODID);
	public static final DeferredHolder<MobEffect, MobEffect> WET = REGISTRY.register("wet", () -> new SweatingMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> SICK = REGISTRY.register("sick", () -> new SickMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> ICKY = REGISTRY.register("icky", () -> new IckyMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> STINKING = REGISTRY.register("stinking", () -> new StinkingMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> EXERTION = REGISTRY.register("exertion", () -> new ExertionMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> FOOD_POISONING = REGISTRY.register("food_poisoning", () -> new FoodPoisoningMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> DRUNK = REGISTRY.register("drunk", () -> new DrunkMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> BLEEDING = REGISTRY.register("bleeding", () -> new BleedingMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> RESTRICTED_MOVEMENT = REGISTRY.register("restricted_movement", () -> new RestrictedMovementMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> PANIC = REGISTRY.register("panic", () -> new PanicMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> TIRED = REGISTRY.register("tired", () -> new TiredMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> DEPRESSED = REGISTRY.register("depressed", () -> new DepressedMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> SAD = REGISTRY.register("sad", () -> new SadMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> SLEEPING = REGISTRY.register("sleeping", () -> new SleepingMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> MINOR_INJURY = REGISTRY.register("minor_injury", () -> new MinorInjuryMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> MAJOR_INJURY = REGISTRY.register("major_injury", () -> new MajorInjuryMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> PARALYZED = REGISTRY.register("paralyzed", () -> new ParalyzedMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> STIMULATED = REGISTRY.register("stimulated", () -> new StimulatedMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> OVERSTIMULATED = REGISTRY.register("overstimulated", () -> new OverstimulatedMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> OVERDOSING = REGISTRY.register("overdosing", () -> new OverdosingMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> DEAD = REGISTRY.register("dead", () -> new DeadMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> LOIKVIAN = REGISTRY.register("loikvian", () -> new LoikvianMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> MINING_EFFICIENCY = REGISTRY.register("mining_efficiency", () -> new MiningEfficiencyMobEffect());
}