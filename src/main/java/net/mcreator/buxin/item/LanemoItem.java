
package net.mcreator.buxin.item;

import net.mcreator.buxin.init.BuxinModTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class LanemoItem extends ArmorItem {
    public LanemoItem(EquipmentSlot slot, Item.Properties properties) {
        super(new ArmorMaterial() {

            @Override
            public int getDurabilityForType(Type type) {
                return new int[]{13, 15, 16, 11}[slot.getIndex()] * 145;
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
                return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft:item.armor.equip_generic"));
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.PRISMARINE_SHARD));
            }

            @Override
            public String getName() {
                return "lanemo";
            }

            @Override
            public float getToughness() {
                return 2f;
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

    public static class Chestplate extends LanemoItem {
        public Chestplate() {
            super(EquipmentSlot.CHEST, new Item.Properties());
        }

    }
}
