
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.LevelAccessor;

public class ZanZhu7DangShiTiGengXinKeShiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		BuxinMod.queueServerWork(400, () -> {
			if (world instanceof ServerLevel projectileLevel) {
				Projectile _entityToSpawn = new LargeFireball(EntityType.FIREBALL, projectileLevel);
				_entityToSpawn.setPos(x, y, z);
				_entityToSpawn.shoot(1, 1, 1, 5, 0);
				projectileLevel.addFreshEntity(_entityToSpawn);
			}
		});
	}
}
