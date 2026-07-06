package net.mcreator.buxin.item;

import net.mcreator.buxin.client.model.ModelScoutH;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public abstract class VillagerScoutsItem extends ArmorItem {
    public VillagerScoutsItem(EquipmentSlot slot, Item.Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.Type type) {
                return new int[]{13, 15, 16, 11}[slot.getIndex()] * 100;
            }

            @Override
            public int getDefenseForType(ArmorItem.Type type) {
                return new int[]{2, 5, 6, 2}[slot.getIndex()];
            }

            @Override
            public int getEnchantmentValue() {
                return 9;
            }

            @Override
            public SoundEvent getEquipSound() {
                return SoundEvents.ARMOR_EQUIP_IRON;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.IRON_INGOT));
            }

            @Override
            public String getName() {
                return "villager_scouts";
            }

            @Override
            public float getToughness() {
                return 1f;
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

    public static class Helmet extends VillagerScoutsItem {
        public Helmet() {
            super(EquipmentSlot.HEAD, new Item.Properties());
        }

        @Override
        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                @Override
                public HumanoidModel<?> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> defaultModel) {
                    ModelScoutH scoutModel = new ModelScoutH(Minecraft.getInstance().getEntityModels().bakeLayer(ModelScoutH.LAYER_LOCATION));
                    HumanoidModel<?> armorModel = new HumanoidModel<>(new ModelPart(
                            Collections.emptyList(),
                            Map.of("head", scoutModel.Head,
                                    "hat", new ModelPart(Collections.emptyList(), Map.of()),
                                    "body", new ModelPart(Collections.emptyList(), Map.of()),
                                    "right_arm", new ModelPart(Collections.emptyList(), Map.of()),
                                    "left_arm", new ModelPart(Collections.emptyList(), Map.of()),
                                    "right_leg", new ModelPart(Collections.emptyList(), Map.of()),
                                    "left_leg", new ModelPart(Collections.emptyList(), Map.of()))));
                    armorModel.crouching = living.isShiftKeyDown();
                    armorModel.riding = defaultModel.riding;
                    armorModel.young = living.isBaby();
                    return armorModel;
                }
            });
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "buxin:textures/models/armor/scout___layer_1.png";
        }
    }
}