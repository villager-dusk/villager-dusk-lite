
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.entity.VillagerScoutEntity;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;

import java.util.Random;

public class CunMinWeiBingShiTiChwuShiShengChengShiProcedure {
	public boolean CanPutToDeath = false;
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		
		Random random = new Random();
		int value = random.nextInt(3);
		Method_114514.entity_use_command(entity,"/team join villager");
		
		int val = random.nextInt(4);
		if(!(entity instanceof VillagerScoutEntity)) {
			if (val == 0) {
				ItemStack offhand = new ItemStack(Items.DIAMOND_SWORD);
				offhand.enchant(Enchantments.SHARPNESS, 1);
				entity.setItemSlot(EquipmentSlot.OFFHAND, offhand);
			} else if (val == 1) {
				entity.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
			} else if (val == 2) {
				entity.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.FISHING_ROD));
			}
		}

		if(Math.random() > 0.5) {
			if(entity instanceof VillagerScoutEntity v){
				v.setItemSlot(EquipmentSlot.CHEST, Items.DIAMOND_CHESTPLATE.getDefaultInstance());
				v.setItemSlot(EquipmentSlot.LEGS, Items.DIAMOND_LEGGINGS.getDefaultInstance());
				v.setItemSlot(EquipmentSlot.FEET, Items.DIAMOND_BOOTS.getDefaultInstance());
				v.setItemSlot(EquipmentSlot.MAINHAND, Items.DIAMOND_SWORD.getDefaultInstance());
			}

			LivingEntity living = (LivingEntity) entity;
			ItemStack chest = living.getItemBySlot(EquipmentSlot.CHEST);
			chest.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 1);
			living.setItemSlot(EquipmentSlot.CHEST, chest);

			ItemStack legs = living.getItemBySlot(EquipmentSlot.LEGS);
			legs.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 1);
			living.setItemSlot(EquipmentSlot.LEGS, legs);

			ItemStack feet = living.getItemBySlot(EquipmentSlot.FEET);
			feet.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 1);
			living.setItemSlot(EquipmentSlot.FEET, feet);

			ItemStack head = living.getItemBySlot(EquipmentSlot.HEAD);
			head.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 1);
			living.setItemSlot(EquipmentSlot.HEAD, head);

			ItemStack mainhand = living.getItemBySlot(EquipmentSlot.MAINHAND);
			mainhand.enchant(Enchantments.SHARPNESS, 1);
			living.setItemSlot(EquipmentSlot.MAINHAND, mainhand);

			ItemStack offhandFinal = living.getItemBySlot(EquipmentSlot.OFFHAND);
			offhandFinal.enchant(Enchantments.SHARPNESS, 1);
			living.setItemSlot(EquipmentSlot.OFFHAND, offhandFinal);
		}

		entity.getPersistentData().putBoolean("isfuck", false);
		entity.getPersistentData().putBoolean("iskicking", false);
		entity.getPersistentData().putDouble("battle", 0);
		entity.getPersistentData().putBoolean("isbuxinentity", true);

		if (Math.random() < 0.91) {
			if(value == 0) {
				if (world instanceof ServerLevel _level) {
					Sheep entityToSpawn = new Sheep(EntityType.SHEEP, _level);
					entityToSpawn.moveTo(x, y, z, 0, 0);
					entityToSpawn.setYBodyRot(0);
					entityToSpawn.setYHeadRot(0);
					entityToSpawn.setDeltaMovement(0, 0, 0);
					entityToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
					_level.addFreshEntity(entityToSpawn);
					entity.startRiding(entityToSpawn);
				}
			}
			if(value == 1 || value == 3) {
				if (world instanceof ServerLevel _level) {
					PolarBear entityToSpawn = new PolarBear(EntityType.POLAR_BEAR, _level);
					entityToSpawn.moveTo(x, y, z, 0, 0);
					entityToSpawn.setYBodyRot(0);
					entityToSpawn.setYHeadRot(0);
					entityToSpawn.setDeltaMovement(0, 0, 0);
					entityToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
					_level.addFreshEntity(entityToSpawn);
					entity.startRiding(entityToSpawn);
				}
			}
			if(value == 2) {
				if (world instanceof ServerLevel _level) {
					Cow entityToSpawn = new Cow(EntityType.COW, _level);
					entityToSpawn.moveTo(x, y, z, 0, 0);
					entityToSpawn.setYBodyRot(0);
					entityToSpawn.setYHeadRot(0);
					entityToSpawn.setDeltaMovement(0, 0, 0);
					entityToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
					_level.addFreshEntity(entityToSpawn);
					entity.startRiding(entityToSpawn);
				}
			}
		}
	}
}
