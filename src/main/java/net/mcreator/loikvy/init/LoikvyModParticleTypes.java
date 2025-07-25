/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.loikvy.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleType;

import net.mcreator.loikvy.LoikvyMod;

public class LoikvyModParticleTypes {
	public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(Registries.PARTICLE_TYPE, LoikvyMod.MODID);
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> STINK_PARTICLES = REGISTRY.register("stink_particles", () -> new SimpleParticleType(false));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLEEDING_PARTICLE = REGISTRY.register("bleeding_particle", () -> new SimpleParticleType(true));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> NO_PART = REGISTRY.register("no_part", () -> new SimpleParticleType(false));
}