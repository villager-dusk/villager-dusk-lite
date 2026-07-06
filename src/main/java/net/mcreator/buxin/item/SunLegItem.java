
package net.mcreator.buxin.item;

import net.mcreator.buxin.init.BuxinModTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public abstract class SunLegItem extends ArmorItem {
    public SunLegItem(EquipmentSlot slot, Item.Properties properties) {
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
                return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_generic"));
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }

            @Override
            public String getName() {
                return "sun_leg";
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
    public static class Leggings extends SunLegItem {
        public Leggings() {
            super(EquipmentSlot.LEGS, new Item.Properties());
        }

        @Override
        public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
            super.appendHoverText(itemstack, world, list, flag);
            //list.add(Component.literal("\u8FD9\u4E00\u79CD\u795E\u79D8\u7684\u62A4\u817F\u8BA9\u4F60\u5728\u767D\u5929\u5177\u6709\u66F4\u5F3A\u7684\u6218\u6597\u80FD\u529B"));
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "buxin:textures/models/armor/sun__layer_2.png";
        }
    }
}
