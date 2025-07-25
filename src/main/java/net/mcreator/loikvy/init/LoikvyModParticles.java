/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.loikvy.init;

import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.mcreator.loikvy.client.particle.StinkParticlesParticle;
import net.mcreator.loikvy.client.particle.NoPartParticle;
import net.mcreator.loikvy.client.particle.BleedingParticleParticle;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LoikvyModParticles {
	@SubscribeEvent
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(LoikvyModParticleTypes.STINK_PARTICLES.get(), StinkParticlesParticle::provider);
		event.registerSpriteSet(LoikvyModParticleTypes.BLEEDING_PARTICLE.get(), BleedingParticleParticle::provider);
		event.registerSpriteSet(LoikvyModParticleTypes.NO_PART.get(), NoPartParticle::provider);
	}
}