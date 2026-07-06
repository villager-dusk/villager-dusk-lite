
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.LevelAccessor;

public class LanemobentiDangShiTiSiWangShiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		world.addParticle((ParticleOptions) (BuxinModParticleTypes.NIUBI_24.get()), x, y, z, 0, 1, 0);
	}
}
