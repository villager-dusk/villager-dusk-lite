
package net.mcreator.buxin.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

public class CunMinWeiBingShiTiChuShiShengChengShiProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity livingEntity) {
			livingEntity.getItemBySlot(EquipmentSlot.HEAD).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
			livingEntity.getItemBySlot(EquipmentSlot.CHEST).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
			livingEntity.getItemBySlot(EquipmentSlot.LEGS).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
			livingEntity.getItemBySlot(EquipmentSlot.FEET).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
		}
	}
}
