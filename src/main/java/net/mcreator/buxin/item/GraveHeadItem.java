
package net.mcreator.buxin.item;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

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
import net.mcreator.buxin.client.model.Modelgrave;

import java.util.function.Consumer;
import java.util.Map;
import java.util.Collections;

public abstract class GraveHeadItem extends ArmorItem {
	public GraveHeadItem(EquipmentSlot slot, Item.Properties properties) {
		super(new ArmorMaterial() {

			@Override
			public int getDurabilityForType(Type type) {
				return new int[]{13, 15, 16, 11}[slot.getIndex()] * 25;
			}

			@Override
			public int getDefenseForType(Type type) {
				return new int[]{2, 5, 6, 1}[slot.getIndex()];
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
				return "grave_head";
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

	public static class Helmet extends GraveHeadItem {
		public Helmet() {
			super(EquipmentSlot.HEAD, new Item.Properties());
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(),
							Map.of("head", new Modelgrave(Minecraft.getInstance().getEntityModels().bakeLayer(Modelgrave.LAYER_LOCATION)).Head, 
								  "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()), 
								  "body", new ModelPart(Collections.emptyList(), Collections.emptyMap()), 
								  "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()), 
								  "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()), 
								  "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()), 
								  "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
					armorModel.crouching = living.isShiftKeyDown();
					armorModel.riding = defaultModel.riding;
					armorModel.young = living.isBaby();
					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "buxin:textures/models/armor/grave_head__layer_1.png";
		}
	}
}
