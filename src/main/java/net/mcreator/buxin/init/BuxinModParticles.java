
package net.mcreator.buxin.init;

import net.mcreator.buxin.client.particle.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BuxinModParticles {
	@SubscribeEvent
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(BuxinModParticleTypes.NIUBI_22.get(), Niubi22Particle::provider);
		event.registerSpriteSet(BuxinModParticleTypes.NIUBI_23.get(), Niubi23Particle::provider);
		event.registerSpriteSet(BuxinModParticleTypes.NIUBI_24.get(), Niubi24Particle::provider);
		event.registerSpriteSet(BuxinModParticleTypes.HUOHUA.get(), HuohuaParticle::provider);
		event.registerSpriteSet(BuxinModParticleTypes.HUOHUA_2.get(), Huohua2Particle::provider);
		event.registerSpriteSet(BuxinModParticleTypes.HUANG_SE_LI_ZI.get(), HuangSeLiZiParticle::provider);
		event.registerSpriteSet(BuxinModParticleTypes.HACK_PAR.get(), HackParParticle::provider);
		event.registerSpriteSet(BuxinModParticleTypes.BLOOD.get(), BloodParticle.Provider::new);
		event.registerSpriteSet(BuxinModParticleTypes.DARK.get(), DarkParticle::provider);
		event.registerSpriteSet(BuxinModParticleTypes.BLOOD_2.get(), Blood2Particle::provider);
		event.registerSpriteSet(BuxinModParticleTypes.RAINBOWPAR.get(), RainbowparParticle::provider);
		event.registerSpriteSet(BuxinModParticleTypes.PINK_LIGHT_PARTICLES.get(), PinkLightParticlesParticle::provider);
		event.registerSpriteSet(BuxinModParticleTypes.ELECTRONIC.get(), ElectronicParticle::provider);
		event.registerSpriteSet(BuxinModParticleTypes.POWER.get(), SnowflakeParticle.Provider::new);
		event.registerSpriteSet(BuxinModParticleTypes.ENDER.get(), EnderParticle.EnderParticleProvider::new);
	}
}
