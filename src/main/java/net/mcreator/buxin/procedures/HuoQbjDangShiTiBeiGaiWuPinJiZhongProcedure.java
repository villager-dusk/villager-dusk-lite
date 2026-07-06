
package net.mcreator.buxin.procedures;

import net.minecraft.world.entity.Entity;

public class HuoQbjDangShiTiBeiGaiWuPinJiZhongProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.setRemainingFireTicks(60);
	}
}
