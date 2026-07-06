package net.mcreator.buxin.item;

import net.mcreator.buxin.client.model.Model村头盔;
import net.mcreator.buxin.init.BuxinModTabs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public abstract class GreenVillagerItem extends ArmorItem {
	public GreenVillagerItem(EquipmentSlot slot, Item.Properties properties) {
		super(new ArmorMaterial() {

			@Override
			public int getDurabilityForType(Type type) {
				return new int[]{13, 15, 16, 11}[slot.getIndex()] * 25;
			}

			@Override
			public int getDefenseForType(Type type) {
				return new int[]{1, 1, 1, 1}[slot.getIndex()];
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
				return "green_villager";
			}

			@Override
			public float getToughness() {
				return 1.3f;
			}

			@Override
			public float getKnockbackResistance() {
				return 4f;
			}
		}, getTypeFromSlot(slot), properties);
	}

	public static class Helmet extends GreenVillagerItem {
		public Helmet() {
			super(EquipmentSlot.HEAD, new Item.Properties());
		}
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

	public static class Chestplate extends GreenVillagerItem {
		public Chestplate() {
			super(EquipmentSlot.CHEST, new Item.Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "buxin:textures/models/armor/green__layer_1.png";
		}
	}

	public static class Leggings extends GreenVillagerItem {
		public Leggings() {
			super(EquipmentSlot.LEGS, new Item.Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "buxin:textures/models/armor/green__layer_2.png";
		}
	}

	public static class Boots extends GreenVillagerItem {
		public Boots() {
			super(EquipmentSlot.FEET, new Item.Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "buxin:textures/models/armor/green__layer_1.png";
		}
	}
}