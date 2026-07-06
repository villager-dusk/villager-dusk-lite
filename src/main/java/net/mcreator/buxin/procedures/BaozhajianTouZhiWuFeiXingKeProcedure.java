
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class BaozhajianTouZhiWuFeiXingKeProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		BuxinMod.queueServerWork(135, () -> {
			if (world instanceof Level _level && !_level.isClientSide())
				_level.explode(null, x, y, z, 4, Level.ExplosionInteraction.TNT);
		});
	}
}
