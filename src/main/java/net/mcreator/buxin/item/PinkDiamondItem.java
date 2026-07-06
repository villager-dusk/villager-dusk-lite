
package net.mcreator.buxin.item;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.buxin.init.BuxinModTabs;

public abstract class PinkDiamondItem extends ArmorItem {
    public PinkDiamondItem(EquipmentSlot slot, Item.Properties properties) {
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
                return 15;
            }

            @Override
            public SoundEvent getEquipSound() {
                return null;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }

            @Override
            public String getName() {
                return "pink_diamond";
            }

            @Override
            public float getToughness() {
                return 5f;
            }

            @Override
            public float getKnockbackResistance() {
                return 0f;
            }
        }, getTypeFromSlot(slot), properties);
    }

    public static class Helmet extends PinkDiamondItem {
        public Helmet() {
            super(EquipmentSlot.HEAD, new Item.Properties());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "buxin:textures/models/armor/1pinkdiamond__layer_1.png";
        }
    }

    public static class Chestplate extends PinkDiamondItem {
        public Chestplate() {
            super(EquipmentSlot.CHEST, new Item.Properties());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "buxin:textures/models/armor/1pinkdiamond__layer_1.png";
        }
    }

    public static class Leggings extends PinkDiamondItem {
        public Leggings() {
            super(EquipmentSlot.LEGS, new Item.Properties());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "buxin:textures/models/armor/1pinkdiamond__layer_2.png";
        }
    }

    public static class Boots extends PinkDiamondItem {
        public Boots() {
            super(EquipmentSlot.FEET, new Item.Properties());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "buxin:textures/models/armor/1pinkdiamond__layer_1.png";
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
}
