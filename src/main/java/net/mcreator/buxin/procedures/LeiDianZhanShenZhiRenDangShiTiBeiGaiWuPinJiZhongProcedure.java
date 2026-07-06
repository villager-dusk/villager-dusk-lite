
package net.mcreator.buxin.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;

public class LeiDianZhanShenZhiRenDangShiTiBeiGaiWuPinJiZhongProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = new net.minecraft.world.entity.LightningBolt(net.minecraft.world.entity.EntityType.LIGHTNING_BOLT, _level);
			entityToSpawn.moveTo(x, y, z, 0, 0);
			entityToSpawn.setDeltaMovement(0, 0, 0);
			world.addFreshEntity(entityToSpawn);
		}
	}
}
