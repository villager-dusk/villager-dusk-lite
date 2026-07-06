package net.mcreator.buxin.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

public class DarkLegendarySwordDangShiTiBeiGaiWuPinJiZhongProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || world.isClientSide())
            return;
        if (world instanceof ServerLevel _level)
            _level.sendParticles(ParticleTypes.LARGE_SMOKE, x, y, z, 100, 0, 0, 0, 0.3);
        if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
            _entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 2));
    }
}