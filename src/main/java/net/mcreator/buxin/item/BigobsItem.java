package net.mcreator.buxin.item;

import net.mcreator.buxin.client.model.Modelbig_obs;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public abstract class BigobsItem extends ArmorItem {
    public BigobsItem(ArmorItem.Type type, Item.Properties properties) {
        super(new ArmorMaterial() {

            @Override
            public int getDurabilityForType(Type type) {
                return 0;
            }

            @Override
            public int getDefenseForType(Type type) {
                return 0;
            }

            @Override
            public int getEnchantmentValue() {
                return 9;
            }

            @Override
            public SoundEvent getEquipSound() {
                return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.item.pickup"));
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }

            @Override
            public String getName() {
                return "bigobs";
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

    public static class Helmet extends BigobsItem {
        public Helmet() {
            super(ArmorItem.Type.HELMET, new Item.Properties());
        }

        @Override
        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                @Override
                public HumanoidModel<?> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> defaultModel) {
                    HumanoidModel<?> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
                            Map.of("head", new Modelbig_obs(Minecraft.getInstance().getEntityModels().bakeLayer(Modelbig_obs.LAYER_LOCATION)).Head,
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
            return "buxin:textures/models/armor/hb__layer_1.png";
        }
    }

    public static class Chestplate extends BigobsItem {
        public Chestplate() {
            super(ArmorItem.Type.CHESTPLATE, new Item.Properties());
        }

        @Override
        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                @Override
                @OnlyIn(Dist.CLIENT)
                public HumanoidModel<?> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> defaultModel) {
                    HumanoidModel<?> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
                            Map.of("body", new Modelbig_obs(Minecraft.getInstance().getEntityModels().bakeLayer(Modelbig_obs.LAYER_LOCATION)).Body,
                                    "left_arm", new Modelbig_obs(Minecraft.getInstance().getEntityModels().bakeLayer(Modelbig_obs.LAYER_LOCATION)).LeftArm,
                                    "right_arm", new Modelbig_obs(Minecraft.getInstance().getEntityModels().bakeLayer(Modelbig_obs.LAYER_LOCATION)).RightArm,
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
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "buxin:textures/models/armor/hb__layer_1.png";
        }
    }
}