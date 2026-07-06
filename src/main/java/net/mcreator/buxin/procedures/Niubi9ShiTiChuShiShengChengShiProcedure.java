
package net.mcreator.buxin.procedures;

import net.minecraft.world.entity.Entity;

public class Niubi9ShiTiChuShiShengChengShiProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.getPersistentData().putDouble("battle", 0);
		entity.getPersistentData().putBoolean("isbuxinentity", true);
	}
}
