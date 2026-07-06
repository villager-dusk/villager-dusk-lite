
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.init.BuxinModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DarkHerobrineDangShiTiShouShangShiProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (Math.random() > 0.6) {
			if (Math.random() == 0.5) {
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack = new ItemStack(BuxinModItems.HELEMT_WEAPON.get());
					_setstack.setCount(1);
					_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
			}
			if (Math.random() < 0.5) {
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack = new ItemStack(BuxinModItems.HELEMT_WEAPON.get());
					_setstack.setCount(1);
					_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
			}
			if (Math.random() > 0.5) {
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack = new ItemStack(BuxinModItems.HELEMT_WEAPON.get());
					_setstack.setCount(1);
					_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack = new ItemStack(BuxinModItems.OBS_5.get());
					_setstack.setCount(1);
					_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
			}
			BuxinMod.queueServerWork(30, () -> {
				if (Math.random() < 0.5) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack = new ItemStack(BuxinModItems.OBS_5.get());
						_setstack.setCount(1);
						_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
				}
				if (Math.random() > 0.5) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack = new ItemStack(Items.AIR);
						_setstack.setCount(1);
						_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
				}
			});
		}
	}
}
