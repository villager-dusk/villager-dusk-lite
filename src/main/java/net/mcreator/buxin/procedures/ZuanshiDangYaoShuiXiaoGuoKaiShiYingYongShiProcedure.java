
package net.mcreator.buxin.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;

import net.mcreator.buxin.BuxinMod;

public class ZuanshiDangYaoShuiXiaoGuoKaiShiYingYongShiProcedure {
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
		if (entity == null || world.isClientSide())
			return;
		BuxinMod.queueServerWork(1, () -> {
			{
				Entity _entity = entity;
				if (_entity instanceof Player _player) {
					_player.getInventory().armor.set(3, new ItemStack(Items.DIAMOND_HELMET));
					_player.getInventory().setChanged();
				} else if (_entity instanceof LivingEntity _living) {
					_living.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
				}
			}
			itemstack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
		});
		BuxinMod.queueServerWork(1, () -> {
			{
				Entity _entity = entity;
				if (_entity instanceof Player _player) {
					_player.getInventory().armor.set(2, new ItemStack(Items.DIAMOND_CHESTPLATE));
					_player.getInventory().setChanged();
				} else if (_entity instanceof LivingEntity _living) {
					_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
				}
			}
			itemstack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
		});
		BuxinMod.queueServerWork(1, () -> {
			{
				Entity _entity = entity;
				if (_entity instanceof Player _player) {
					_player.getInventory().armor.set(1, new ItemStack(Items.DIAMOND_LEGGINGS));
					_player.getInventory().setChanged();
				} else if (_entity instanceof LivingEntity _living) {
					_living.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
				}
			}
			itemstack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
		});
		BuxinMod.queueServerWork(1, () -> {
			{
				Entity _entity = entity;
				if (_entity instanceof Player _player) {
					_player.getInventory().armor.set(0, new ItemStack(Items.DIAMOND_BOOTS));
					_player.getInventory().setChanged();
				} else if (_entity instanceof LivingEntity _living) {
					_living.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
				}
			}
			itemstack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
		});
	}
}
