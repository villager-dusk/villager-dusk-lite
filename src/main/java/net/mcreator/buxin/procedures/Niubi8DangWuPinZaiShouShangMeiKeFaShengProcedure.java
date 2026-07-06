
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;

public class Niubi8DangWuPinZaiShouShangMeiKeFaShengProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        if (world instanceof ServerLevel _level)
            _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_22.get()), x, y, z, 1, 1, 1, 1, 1);
    }
}
