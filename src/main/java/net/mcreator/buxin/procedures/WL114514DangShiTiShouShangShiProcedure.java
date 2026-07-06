
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;

public class WL114514DangShiTiShouShangShiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        if (Math.random() < 0.6) {
            if (Math.random() < 0.6) {
                if (world instanceof ServerLevel _level)
                    _level.sendParticles((ParticleOptions) BuxinModParticleTypes.HUOHUA.get(), x, y, z, 40, 0.25, 0.3, 0.25, 0.2);
            }
        }
    }
}
