
package net.mcreator.buxin.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.Level;

public class MoYingJianDangShiTiHuiDongWuPinShiProcedure {
    public static void execute(Entity entity) {
        if (entity == null)
            return;
        Level projectileLevel = entity.level();
        if (!projectileLevel.isClientSide()) {
            ThrownEnderpearl _entityToSpawn = new ThrownEnderpearl(EntityType.ENDER_PEARL, projectileLevel);
            _entityToSpawn.setPos(entity.getX(), entity.getEyeY() - 0.1, entity.getZ());
            _entityToSpawn.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 2, 0);
            projectileLevel.addFreshEntity(_entityToSpawn);
        }
    }
}
