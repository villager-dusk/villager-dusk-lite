
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.entity.Obsidian2Entity;
import net.mcreator.buxin.init.BuxinModEntities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

import java.util.Random;

public class HerobrineDangShiTiGengXinKeShiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || world.isClientSide())
            return;
        Random random = new Random();
        if (entity instanceof Mob) {
            if (((Mob) entity).getTarget() != null) {
                if (Math.random() > 0.995) {
                    {
                        Entity _shootFrom = entity;
                        Level projectileLevel = _shootFrom.level();
                        if (!projectileLevel.isClientSide()) {
                            Projectile _entityToSpawn = new Object() {
                                public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                                    AbstractArrow entityToSpawn = new Obsidian2Entity(BuxinModEntities.OBSIDIAN_2.get(), level);
                                    entityToSpawn.setOwner(shooter);
                                    entityToSpawn.setBaseDamage(damage);
                                    entityToSpawn.setKnockback(knockback);
                                    entityToSpawn.setSilent(true);
                                    return entityToSpawn;
                                }
                            }.getArrow(projectileLevel, entity, 12, 15);
                            _entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
                            _entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1, 0);
                            projectileLevel.addFreshEntity(_entityToSpawn);
                        }
                    }
                }
            }
        }
    }
}
