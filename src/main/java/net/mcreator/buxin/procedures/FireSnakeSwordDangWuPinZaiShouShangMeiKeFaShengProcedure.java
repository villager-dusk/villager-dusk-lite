
package net.mcreator.buxin.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleOptions;

import net.mcreator.buxin.init.BuxinModParticleTypes;

public class FireSnakeSwordDangWuPinZaiShouShangMeiKeFaShengProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20, 3));
		if (world instanceof ServerLevel _level)
			_level.sendParticles((ParticleOptions) (BuxinModParticleTypes.BLOOD.get()), x, y, z, 2, 1, 1, 1, 1);
	}
}
