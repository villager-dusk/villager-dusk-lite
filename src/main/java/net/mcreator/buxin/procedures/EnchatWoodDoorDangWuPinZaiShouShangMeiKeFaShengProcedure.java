
package net.mcreator.buxin.procedures;

import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

public class EnchatWoodDoorDangWuPinZaiShouShangMeiKeFaShengProcedure {
    public static void execute(Entity entity) {
        if (entity == null)
            return;
        ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)).enchant(Enchantments.KNOCKBACK, 5);
    }
}
