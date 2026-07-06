
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.init.BuxinModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

public class ChunzuanProcedure {
    public static void execute(Entity entity) {
        if (entity == null)
            return;
        if (entity instanceof LivingEntity _living) {
            _living.setItemSlot(EquipmentSlot.FEET, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_BOOTS.get()));
            _living.setItemSlot(EquipmentSlot.LEGS, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_LEGGINGS.get()));
            _living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_CHESTPLATE.get()));
            _living.setItemSlot(EquipmentSlot.HEAD, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_HELMET.get()));
        }
        if (entity instanceof LivingEntity _living) {
            ItemStack feet = _living.getItemBySlot(EquipmentSlot.FEET);
            if (!feet.isEmpty()) {
                feet = feet.copy();
                feet.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
                feet.enchant(Enchantments.BINDING_CURSE, 5);
                _living.setItemSlot(EquipmentSlot.FEET, feet);
            }
            ItemStack legs = _living.getItemBySlot(EquipmentSlot.LEGS);
            if (!legs.isEmpty()) {
                legs = legs.copy();
                legs.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
                legs.enchant(Enchantments.BINDING_CURSE, 5);
                _living.setItemSlot(EquipmentSlot.LEGS, legs);
            }
            ItemStack chest = _living.getItemBySlot(EquipmentSlot.CHEST);
            if (!chest.isEmpty()) {
                chest = chest.copy();
                chest.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
                chest.enchant(Enchantments.BINDING_CURSE, 5);
                _living.setItemSlot(EquipmentSlot.CHEST, chest);
            }
            ItemStack head = _living.getItemBySlot(EquipmentSlot.HEAD);
            if (!head.isEmpty()) {
                head = head.copy();
                head.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
                head.enchant(Enchantments.BINDING_CURSE, 5);
                _living.setItemSlot(EquipmentSlot.HEAD, head);
            }
        }
    }
}
