
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.config.common.VFXParticleConfig;
import net.mcreator.buxin.my_method.VFXTool;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

import static net.mcreator.buxin.BuxinMod.isWindows;

public class TntDangTouSheWuJiZhongWanJiaShiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof Level _level && !_level.isClientSide()) {
			_level.explode(null, x, (y + 2), z, 3, Level.ExplosionInteraction.BLOCK);
		}
		
		if ((isWindows()) && VFXParticleConfig.VFXParticleConfig.get() && world instanceof Level level) {
			VFXTool.addVFXParticle(new Vec3(x, y, z), BuxinMod.MODID, "fire_bomb", level);
		}
	}
}
