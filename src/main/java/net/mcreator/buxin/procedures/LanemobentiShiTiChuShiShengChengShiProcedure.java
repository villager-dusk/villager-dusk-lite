
package net.mcreator.buxin.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

public class LanemobentiShiTiChuShiShengChengShiProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity livingEntity) {
			ItemStack feetStack = livingEntity.getItemBySlot(EquipmentSlot.FEET);
			if (!feetStack.isEmpty()) feetStack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
			ItemStack legsStack = livingEntity.getItemBySlot(EquipmentSlot.LEGS);
			if (!legsStack.isEmpty()) legsStack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
			ItemStack chestStack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
			if (!chestStack.isEmpty()) chestStack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
			ItemStack headStack = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
			if (!headStack.isEmpty()) headStack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
		}
	}
}
