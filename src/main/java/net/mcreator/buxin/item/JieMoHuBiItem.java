
package net.mcreator.buxin.item;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.Minecraft;

import net.mcreator.buxin.init.BuxinModTabs;
import net.mcreator.buxin.client.model.Model铁傀儡护臂;

import java.util.function.Consumer;
import java.util.Map;
import java.util.Collections;

public abstract class JieMoHuBiItem extends ArmorItem {
	public JieMoHuBiItem(EquipmentSlot slot, Item.Properties properties) {
		super(new ArmorMaterial() {

			@Override
			public int getDurabilityForType(Type type) {
				return new int[]{13, 15, 16, 11}[slot.getIndex()] * 25;
			}

			@Override
			public int getDefenseForType(Type type) {
				return new int[]{2, 5, 6, 2}[slot.getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 9;
			}

			@Override
			public SoundEvent getEquipSound() {
				return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(""));
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.of();
			}

			@Override
			public String getName() {
				return "jie_mo_hu_bi";
			}

			@Override
			public float getToughness() {
				return 0f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0f;
			}
		}, getTypeFromSlot(slot), properties);
	}


	private static ArmorItem.Type getTypeFromSlot(EquipmentSlot slot) {
		return switch (slot) {
			case HEAD -> ArmorItem.Type.HELMET;
			case CHEST -> ArmorItem.Type.CHESTPLATE;
			case LEGS -> ArmorItem.Type.LEGGINGS;
			case FEET -> ArmorItem.Type.BOOTS;
			default -> throw new IllegalArgumentException("Invalid slot: " + slot);
		};
	}

	public static class Chestplate extends JieMoHuBiItem {
		public Chestplate() {
			super(EquipmentSlot.CHEST, new Item.Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "buxin:textures/entities/90942423291812559.png";
		}
	}
}
