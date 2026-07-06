
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.PoisonEggEntity;
import net.mcreator.buxin.init.BuxinModEntities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class BBQShiTiChuShiShengChengShiProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		BuxinMod.queueServerWork(250, () -> {
			{
				Entity _shootFrom = entity;
				Level projectileLevel = _shootFrom.level();
				if (!projectileLevel.isClientSide()) {
					AbstractArrow _entityToSpawn = new PoisonEggEntity(BuxinModEntities.POISON_EGG.get(), projectileLevel);
					_entityToSpawn.setBaseDamage(30);
					_entityToSpawn.setKnockback(1);
					_entityToSpawn.setSilent(true);
					_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
					_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 4, 0);
					projectileLevel.addFreshEntity(_entityToSpawn);
				}
			}
			{
				Entity _shootFrom = entity;
				Level projectileLevel = _shootFrom.level();
				if (!projectileLevel.isClientSide()) {
					AbstractArrow _entityToSpawn = new PoisonEggEntity(BuxinModEntities.POISON_EGG.get(), projectileLevel);
					_entityToSpawn.setBaseDamage(30);
					_entityToSpawn.setKnockback(1);
					_entityToSpawn.setSilent(true);
					_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
					_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 3, 0);
					projectileLevel.addFreshEntity(_entityToSpawn);
				}
			}
			{
				Entity _shootFrom = entity;
				Level projectileLevel = _shootFrom.level();
				if (!projectileLevel.isClientSide()) {
					AbstractArrow _entityToSpawn = new PoisonEggEntity(BuxinModEntities.POISON_EGG.get(), projectileLevel);
					_entityToSpawn.setBaseDamage(30);
					_entityToSpawn.setKnockback(1);
					_entityToSpawn.setSilent(true);
					_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
					_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 3, 0);
					projectileLevel.addFreshEntity(_entityToSpawn);
				}
			}
		});
		BuxinMod.queueServerWork(450, () -> {
			{
				Entity _shootFrom = entity;
				Level projectileLevel = _shootFrom.level();
				if (!projectileLevel.isClientSide()) {
					AbstractArrow _entityToSpawn = new PoisonEggEntity(BuxinModEntities.POISON_EGG.get(), projectileLevel);
					_entityToSpawn.setBaseDamage(30);
					_entityToSpawn.setKnockback(1);
					_entityToSpawn.setSilent(true);
					_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
					_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 4, 0);
					projectileLevel.addFreshEntity(_entityToSpawn);
				}
			}
			{
				Entity _shootFrom = entity;
				Level projectileLevel = _shootFrom.level();
				if (!projectileLevel.isClientSide()) {
					AbstractArrow _entityToSpawn = new PoisonEggEntity(BuxinModEntities.POISON_EGG.get(), projectileLevel);
					_entityToSpawn.setBaseDamage(30);
					_entityToSpawn.setKnockback(1);
					_entityToSpawn.setSilent(true);
					_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
					_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 3, 0);
					projectileLevel.addFreshEntity(_entityToSpawn);
				}
			}
			{
				Entity _shootFrom = entity;
				Level projectileLevel = _shootFrom.level();
				if (!projectileLevel.isClientSide()) {
					AbstractArrow _entityToSpawn = new PoisonEggEntity(BuxinModEntities.POISON_EGG.get(), projectileLevel);
					_entityToSpawn.setBaseDamage(30);
					_entityToSpawn.setKnockback(1);
					_entityToSpawn.setSilent(true);
					_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
					_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 3, 0);
					projectileLevel.addFreshEntity(_entityToSpawn);
				}
			}
		});
	}
}
