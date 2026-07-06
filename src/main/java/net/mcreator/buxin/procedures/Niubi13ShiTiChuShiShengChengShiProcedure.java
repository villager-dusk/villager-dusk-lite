
package net.mcreator.buxin.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.registries.ForgeRegistries;

public class Niubi13ShiTiChuShiShengChengShiProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.getPersistentData().putDouble("battle", 0);
		entity.getPersistentData().putBoolean("isbuxinentity", true);
		
		if (entity instanceof LivingEntity livingEntity) {
			ItemStack legs = livingEntity.getItemBySlot(EquipmentSlot.LEGS);
			if (!legs.isEmpty()) {
				legs = legs.copy();
				legs.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 2);
				livingEntity.setItemSlot(EquipmentSlot.LEGS, legs);
			}
			
			ItemStack head = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
			if (!head.isEmpty()) {
				head = head.copy();
				head.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 2);
				livingEntity.setItemSlot(EquipmentSlot.HEAD, head);
			}
			
			ItemStack feet = livingEntity.getItemBySlot(EquipmentSlot.FEET);
			if (!feet.isEmpty()) {
				feet = feet.copy();
				feet.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 2);
				livingEntity.setItemSlot(EquipmentSlot.FEET, feet);
			}
			
			ItemStack chest = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
			if (!chest.isEmpty()) {
				chest = chest.copy();
				chest.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 2);
				livingEntity.setItemSlot(EquipmentSlot.CHEST, chest);
			}
			
			ItemStack mainHand = livingEntity.getMainHandItem();
			if (!mainHand.isEmpty()) {
				mainHand = mainHand.copy();
				mainHand.enchant(Enchantments.SHARPNESS, 1);
				livingEntity.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, mainHand);
			}
			
			if ("buxin:mr".equals(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString())) {
				ItemStack chestplate = new ItemStack(Items.DIAMOND_CHESTPLATE);
				chestplate.setCount(1);
				livingEntity.setItemSlot(EquipmentSlot.CHEST, chestplate);
				if (livingEntity instanceof Player player) {
					player.getInventory().setChanged();
				}
			}
		}
	}
}
