
package net.mcreator.buxin.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleOptions;

import net.mcreator.buxin.init.BuxinModParticleTypes;

public class RainbowSnakeSwordDangWuPinZaiShouShangMeiKeFaShengProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof ServerLevel _level)
			_level.sendParticles(BuxinModParticleTypes.RAINBOWPAR.get(), x, y, z, 3, 1, 1, 1, 1);
	}
}
