
package net.mcreator.buxin.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.RandomSource;

import net.mcreator.buxin.item.Fuzi2Item;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.BuxinMod;

public class Fuzi2DangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure {
    public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
        if (entity == null || world.isClientSide())
            return;
        if (true) {
            if (itemstack.getItem() instanceof Fuzi2Item)
                itemstack.getOrCreateTag().putString("geckoAnim", "animation.ruby_axe.short");
            BuxinMod.queueServerWork(10, () -> {
                if (entity instanceof LivingEntity _entity) {
                    ItemStack _setstack = new ItemStack(BuxinModItems.FUZI_3.get());
                    _setstack.setCount(1);
                    _entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
                    if (_entity instanceof Player _player)
                        _player.getInventory().setChanged();
                }
            });
            ItemStack mainHand = entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
            int damageToTake = mainHand.getDamageValue();
            if (itemstack.hurt(damageToTake, RandomSource.create(), null)) {
                itemstack.shrink(1);
                itemstack.setDamageValue(0);
            }
        }
    }
}
