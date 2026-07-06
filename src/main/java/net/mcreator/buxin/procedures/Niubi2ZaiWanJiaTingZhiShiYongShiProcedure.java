
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.init.BuxinModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

public class Niubi2ZaiWanJiaTingZhiShiYongShiProcedure {
	public static void execute() {
		(new ItemStack(BuxinModItems.LEGENDARY_SWORD.get())).enchant(Enchantments.SHARPNESS, 10);
	}
}
