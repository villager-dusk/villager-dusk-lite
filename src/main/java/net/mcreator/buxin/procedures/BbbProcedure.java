
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.item.FuziItem;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

public class BbbProcedure {
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
		if (entity == null || world.isClientSide())
			return;
		if (true) {
			if (itemstack.getItem() instanceof FuziItem)
				itemstack.getOrCreateTag().putString("geckoAnim", "animation.ruby_axe.short");
			BuxinMod.queueServerWork(15, () -> {
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack = new ItemStack(BuxinModItems.FUZI_2.get());
					_setstack.setCount(1);
					_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
			});
			(itemstack).setDamageValue((itemstack).getDamageValue());
			{
				ItemStack _ist = itemstack;
				if (_ist.hurt(((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)).getDamageValue(), RandomSource.create(0L), null)) {
					_ist.shrink(1);
					_ist.setDamageValue(0);
				}
			}
		}
	}
}
