package net.mcreator.buxin.item;

import net.mcreator.buxin.procedures.DarkArmorTouKuiShiJianMeiYouXiKeProcedure;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class DarkArmorItem extends ArmorItem {
	public DarkArmorItem(ArmorItem.Type type, Item.Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForType(ArmorItem.Type type) {
				return switch (type) {
					case HELMET -> 13 * 25;
					case CHESTPLATE -> 15 * 25;
					case LEGGINGS -> 16 * 25;
					case BOOTS -> 11 * 25;
				};
			}

			@Override
			public int getDefenseForType(ArmorItem.Type type) {
				return switch (type) {
					case HELMET -> 5;
					case CHESTPLATE -> 7;
					case LEGGINGS -> 7;
					case BOOTS -> 5;
				};
			}

			@Override
			public int getEnchantmentValue() {
				return 15;
			}

			@Override
			public SoundEvent getEquipSound() {
				return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_generic"));
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.of();
			}

			@Override
			public String getName() {
				return "dark";
			}

			@Override
			public float getToughness() {
				return 0f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0f;
			}
		}, type, properties);
	}

	public static class Helmet extends DarkArmorItem {
		public Helmet() {
			super(ArmorItem.Type.HELMET, new Item.Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "buxin:textures/models/armor/1dark_layer_1.png";
		}

		@Override
		public void onArmorTick(ItemStack itemstack, Level world, Player entity) {
			DarkArmorTouKuiShiJianMeiYouXiKeProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ());
		}
	}

	public static class Chestplate extends DarkArmorItem {
		public Chestplate() {
			super(ArmorItem.Type.CHESTPLATE, new Item.Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "buxin:textures/models/armor/1dark_layer_1.png";
		}

		@Override
		public void onArmorTick(ItemStack itemstack, Level world, Player entity) {
			DarkArmorTouKuiShiJianMeiYouXiKeProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ());
		}
	}

	public static class Boots extends DarkArmorItem {
		public Boots() {
			super(ArmorItem.Type.BOOTS, new Item.Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "buxin:textures/models/armor/1dark_layer_1.png";
		}

		@Override
		public void onArmorTick(ItemStack itemstack, Level world, Player entity) {
			DarkArmorTouKuiShiJianMeiYouXiKeProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ());
		}
	}
}