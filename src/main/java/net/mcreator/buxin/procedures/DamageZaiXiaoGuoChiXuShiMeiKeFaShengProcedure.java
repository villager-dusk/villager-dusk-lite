
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class DamageZaiXiaoGuoChiXuShiMeiKeFaShengProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || world.isClientSide())
            return;
        BuxinMod.queueServerWork(20, () -> {
            entity.hurt(new DamageSources(world.registryAccess()).generic(), 6);
            /*
            if (world instanceof ServerLevel _level)
                _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), x, y, z, 5, 0.12, 0.6, 1, 1);

             */
        });
    }
}
