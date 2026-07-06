
package net.mcreator.buxin.procedures;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.RandomSource;

import net.mcreator.buxin.init.BuxinModItems;

public class Niubi5DangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure {
	public static void execute(Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (true) {
			entity.setItemSlot(EquipmentSlot.MAINHAND, BuxinModItems.SUPER_SNAKE_SWORD.get().getDefaultInstance());
		}
	}
}
