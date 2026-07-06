
package net.mcreator.buxin.item;

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

public abstract class RainbowItem extends ArmorItem {
    public RainbowItem(EquipmentSlot slot, Item.Properties properties) {
        super(new ArmorMaterial() {

            @Override
            public int getDurabilityForType(Type type) {
                return new int[]{13, 15, 16, 11}[slot.getIndex()] * 25;
            }

            @Override
            public int getDefenseForType(Type type) {
                return new int[]{9, 6, 10, 4}[slot.getIndex()];
            }

            @Override
            public int getEnchantmentValue() {
                return 9;
            }

            @Override
            public SoundEvent getEquipSound() {
                return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.armor.equip_diamond"));
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }

            @Override
            public String getName() {
                return "rainbow";
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

    public static class Helmet extends RainbowItem {
        public Helmet() {
            super(EquipmentSlot.HEAD, new Item.Properties());
        }


    }

    public static class Chestplate extends RainbowItem {
        public Chestplate() {
            super(EquipmentSlot.CHEST, new Item.Properties());
        }

    }

    public static class Leggings extends RainbowItem {
        public Leggings() {
            super(EquipmentSlot.LEGS, new Item.Properties());
        }

    }

    public static class Boots extends RainbowItem {
        public Boots() {
            super(EquipmentSlot.FEET, new Item.Properties());
        }
    }
}
