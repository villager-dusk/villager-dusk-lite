
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;

public class Niubi5DangWuPinZaiShouShangMeiKeFaShengProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        if (world instanceof ServerLevel _level)
            _level.sendParticles((ParticleOptions) BuxinModParticleTypes.NIUBI_22.get(), x, y, z, 2, 1, 1, 1, 0);
    }
}
