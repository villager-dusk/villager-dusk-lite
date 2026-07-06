
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class ObsStick2ShiTiChuShiShengChengShiProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		entity.getPersistentData().putBoolean("YH", true);
		BuxinMod.queueServerWork(120, () -> {
			if (!entity.level().isClientSide())
				entity.discard();
		});
	}
}
