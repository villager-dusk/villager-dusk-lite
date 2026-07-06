
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.init.BuxinModMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class FireSnakeSwordDangShiTiBeiGaiWuPinJiZhongProcedure {
    public static void execute(Entity entity) {
        if (entity == null)
            return;
        if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
            _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.DAMAGE.get(), 60, 1));
    }
}
