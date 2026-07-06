
package net.mcreator.buxin.procedures;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.LevelAccessor;

public class Entity303ShiTiChuShiShengChengShiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof ServerLevel _level) {
			LightningBolt entityToSpawn = new LightningBolt(EntityType.LIGHTNING_BOLT, _level);
			entityToSpawn.setPos(x, y, z);
			entityToSpawn.setVisualOnly(false);
			_level.addFreshEntity(entityToSpawn);
		}
	}
}
