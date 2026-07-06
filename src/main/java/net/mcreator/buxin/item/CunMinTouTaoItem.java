package net.mcreator.buxin.item;

import net.mcreator.buxin.client.model.Model村民头套;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.procedures.JueXingZhanShenZhiRenDangWuPinZaiShouShangMeiKeFaShengProcedure;
import net.mcreator.buxin.procedures.Niubi2DangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public abstract class CunMinTouTaoItem extends ArmorItem {
    private final String teamName = "villager";

    public CunMinTouTaoItem(ArmorItem.Type type, Item.Properties properties) {
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
                    case HELMET -> 2;
                    case CHESTPLATE -> 5;
                    case LEGGINGS -> 6;
                    case BOOTS -> 2;
                };
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
                return "cun_min_tou_tao";
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

    public static class Helmet extends CunMinTouTaoItem {
        public Helmet() {
            super(ArmorItem.Type.HELMET, new Item.Properties());
        }

        @Override
        public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
            super.inventoryTick(itemstack, world, entity, slot, selected);
        }

        @Override
        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                @Override
                public HumanoidModel<?> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> defaultModel) {
                    ModelPart headModel = new Model村民头套(Minecraft.getInstance().getEntityModels().bakeLayer(Model村民头套.LAYER_LOCATION)).Head;
                    ModelPart emptyPart = new ModelPart(Collections.emptyList(), Collections.emptyMap());

                    HumanoidModel<?> armorModel = new HumanoidModel<>(new ModelPart(
                            Collections.emptyList(),
                            Map.of(
                                    "head", headModel,
                                    "hat", emptyPart,
                                    "body", emptyPart,
                                    "right_arm", emptyPart,
                                    "left_arm", emptyPart,
                                    "right_leg", emptyPart,
                                    "left_leg", emptyPart
                            )
                    ));

                    armorModel.crouching = living.isShiftKeyDown();
                    armorModel.young = living.isBaby();
                    return armorModel;
                }
            });
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "buxin:textures/models/armor/texte__layer_1.png";
        }
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
    }
}