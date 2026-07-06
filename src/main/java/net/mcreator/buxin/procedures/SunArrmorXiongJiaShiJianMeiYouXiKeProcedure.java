
package net.mcreator.buxin.procedures;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class SunArrmorXiongJiaShiJianMeiYouXiKeProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null)
            return;
        if (world.isClientSide())
            return;
        if (world instanceof Level level) {
            if (level.isDay() && !level.isThundering() && !level.isRaining()) {
                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20, 2));
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60, 1, false, false));
                }
            }
        }
    }
}
