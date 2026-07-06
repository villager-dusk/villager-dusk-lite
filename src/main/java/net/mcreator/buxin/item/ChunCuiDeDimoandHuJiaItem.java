package net.mcreator.buxin.item;

import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.init.BuxinModTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class ChunCuiDeDimoandHuJiaItem extends ArmorItem {
	public ChunCuiDeDimoandHuJiaItem(ArmorItem.Type type, Item.Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForType(ArmorItem.Type type) {
				return switch (type) {
					case HELMET -> 13 * 150;
					case CHESTPLATE -> 15 * 150;
					case LEGGINGS -> 16 * 150;
					case BOOTS -> 11 * 150;
				};
			}

			@Override
			public int getDefenseForType(ArmorItem.Type type) {
				return switch (type) {
					case HELMET -> 8;
					case CHESTPLATE -> 7;
					case LEGGINGS -> 8;
					case BOOTS -> 7;
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
				return Ingredient.of(new ItemStack(BuxinModItems.DIAMOND_2.get()));
			}

			@Override
			public String getName() {
				return "chun_cui_de_dimoand_hu_jia";
			}

			@Override
			public float getToughness() {
				return 5f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0f;
			}
		}, type, properties);
	}

	public static class Helmet extends ChunCuiDeDimoandHuJiaItem {
		public Helmet() {
			super(ArmorItem.Type.HELMET, new Item.Properties());
		}


	}

	public static class Chestplate extends ChunCuiDeDimoandHuJiaItem {
		public Chestplate() {
			super(ArmorItem.Type.CHESTPLATE, new Item.Properties());
		}


	}

	public static class Leggings extends ChunCuiDeDimoandHuJiaItem {
		public Leggings() {
			super(ArmorItem.Type.LEGGINGS, new Item.Properties());
		}


	}

	public static class Boots extends ChunCuiDeDimoandHuJiaItem {
		public Boots() {
			super(ArmorItem.Type.BOOTS, new Item.Properties());
		}


	}
}