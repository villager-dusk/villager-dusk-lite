
package net.mcreator.buxin.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class Herobrine3ShiTiChuShiShengChengShiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		entity.getPersistentData().putBoolean("OP", true);
		//if(entity instanceof LivingEntity)
		entity.getPersistentData().putDouble("battle", 0.0);
		entity.getPersistentData().putBoolean("isbuxinentity", true);
	}
}
