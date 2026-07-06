
package net.mcreator.buxin.procedures;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class ThrowEnderPeralSongKaiAnJianShiProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		if (entity instanceof Player _player && _player.getInventory().hasAnyOf(java.util.Set.of(Items.ENDER_PEARL))) {
			entity.playSound(SoundEvents.ENDER_PEARL_THROW, 1.0f, 1.0f);
			boolean consumed = false;
			for (int i = 0; i < _player.getInventory().getContainerSize(); i++) {
				ItemStack stack = _player.getInventory().getItem(i);
				if (!stack.isEmpty() && stack.is(Items.ENDER_PEARL)) {
					stack.shrink(1);
					consumed = true;
					break;
				}
			}
			if (!consumed) return;
			
			_player.getCooldowns().addCooldown(Items.ENDER_PEARL, 20);
			{
                Level projectileLevel = entity.level();
				if (!projectileLevel.isClientSide()) {
					Projectile _entityToSpawn = new Object() {
						public Projectile getProjectile(Level level, Entity shooter) {
							return new ThrownEnderpearl(level, (net.minecraft.world.entity.LivingEntity) shooter);
						}
					}.getProjectile(projectileLevel, entity);
					_entityToSpawn.setPos(entity.getX(), entity.getEyeY() - 0.1, entity.getZ());
					_entityToSpawn.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, (float) 1.2f, 0f);
					projectileLevel.addFreshEntity(_entityToSpawn);
				}
			}

		}
	}
}
