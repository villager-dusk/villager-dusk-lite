
package net.mcreator.buxin.item.father;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CustomEnchantableItem extends Item {
    
    private static final Set<Enchantment> ALLOWED_ENCHANTMENTS = new HashSet<>();
    
    static {
        ALLOWED_ENCHANTMENTS.add(Enchantments.SHARPNESS);
        ALLOWED_ENCHANTMENTS.add(Enchantments.SMITE);
        ALLOWED_ENCHANTMENTS.add(Enchantments.BANE_OF_ARTHROPODS);
        ALLOWED_ENCHANTMENTS.add(Enchantments.KNOCKBACK);
        ALLOWED_ENCHANTMENTS.add(Enchantments.FIRE_ASPECT);
        //ALLOWED_ENCHANTMENTS.add(Enchantments.LOOTING);
        ALLOWED_ENCHANTMENTS.add(Enchantments.SWEEPING_EDGE);
        
        ALLOWED_ENCHANTMENTS.add(Enchantments.UNBREAKING);
        ALLOWED_ENCHANTMENTS.add(Enchantments.MENDING);
        ALLOWED_ENCHANTMENTS.add(Enchantments.VANISHING_CURSE);
        ALLOWED_ENCHANTMENTS.add(Enchantments.BINDING_CURSE);
    }
    
    public CustomEnchantableItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return ALLOWED_ENCHANTMENTS.contains(enchantment);
    }
    
    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return true;
    }
    
    @Override
    public int getEnchantmentValue() {
        return 20;
    }
    
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }
    
    public static Set<Enchantment> getAllowedEnchantments() {
        return Collections.unmodifiableSet(ALLOWED_ENCHANTMENTS);
    }
    
    public static void addAllowedEnchantment(Enchantment enchantment) {
        ALLOWED_ENCHANTMENTS.add(enchantment);
    }
}
