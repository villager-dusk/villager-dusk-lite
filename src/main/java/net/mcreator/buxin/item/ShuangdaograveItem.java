
package net.mcreator.buxin.item;

import net.mcreator.buxin.client.model.Model格雷;
import net.mcreator.buxin.init.BuxinModTabs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class ShuangdaograveItem extends ArmorItem {
    public ShuangdaograveItem(EquipmentSlot slot, Item.Properties properties) {
        super(new ArmorMaterial() {


            @Override
            public int getDurabilityForType(Type type) {
                return new int[]{13, 15, 16, 11}[slot.getIndex()] * 85;
            }

            @Override
            public int getDefenseForType(Type type) {
                return new int[]{2, 5, 19, 2}[slot.getIndex()];
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
                return Ingredient.of(new ItemStack(Items.EMERALD));
            }

            @Override
            public String getName() {
                return "shuangdaograve";
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

    public static class Chestplate extends ShuangdaograveItem {
        public Chestplate() {
            super(EquipmentSlot.CHEST, new Item.Properties());
        }

        @Override
        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                @Override
                @OnlyIn(Dist.CLIENT)
                public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
                    HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(),
                            Map.of("body", new Model格雷(Minecraft.getInstance().getEntityModels().bakeLayer(Model格雷.LAYER_LOCATION)).Body, 
                                  "left_arm", new Model格雷(Minecraft.getInstance().getEntityModels().bakeLayer(Model格雷.LAYER_LOCATION)).LeftArm,
                                  "right_arm", new Model格雷(Minecraft.getInstance().getEntityModels().bakeLayer(Model格雷.LAYER_LOCATION)).RightArm, 
                                  "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()), 
                                  "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()), 
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
        public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
            super.appendHoverText(itemstack, world, list, flag);
            list.add(Component.literal("Grave"));
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "buxin:textures/models/armor/graveshuang_dao____layer_1.png";
        }
    }
}
