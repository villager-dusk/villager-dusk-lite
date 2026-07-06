
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.entity.LanemobentiEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

public class LanemobentiShiTiChuShiSwhengChengShiProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if(entity instanceof LanemobentiEntity) {
			if (entity instanceof LivingEntity livingEntity) {
				livingEntity.getItemBySlot(EquipmentSlot.FEET).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
				livingEntity.getItemBySlot(EquipmentSlot.LEGS).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
				livingEntity.getItemBySlot(EquipmentSlot.CHEST).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
				livingEntity.getItemBySlot(EquipmentSlot.HEAD).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
				livingEntity.getMainHandItem().enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
				livingEntity.getOffhandItem().enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
			}
		}
		entity.getPersistentData().putDouble("battle", 0);
		entity.getPersistentData().putBoolean("isbuxinentity", true);
	}
}
