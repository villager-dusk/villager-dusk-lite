package net.mcreator.buxin.procedures;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class ChangdaohimnbShiTiChuShiShengChengShiProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null || world.isClientSide())
			return;

		entity.getPersistentData().putDouble("battle", 0);
	}
}