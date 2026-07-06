
package net.mcreator.buxin.entity.snakeblade_test;

import net.mcreator.buxin.config.common.SnakeBladeConfig;
import net.mcreator.buxin.entity.snakeblade_test.entity.SnakeBladeEntity;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.my_method.SystemMethod;
import net.mcreator.buxin.my_method.VFXTool;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class SnakeBladeMethod {
    public static boolean process(LivingEntity playerIn) {
        Level worldIn = playerIn.level();
        Entity closestValid = null;
        for (Entity entity : worldIn.getEntitiesOfClass(LivingEntity.class, playerIn.getBoundingBox().inflate(SnakeBladeConfig.SnakeBladeMaxRange.get()), e -> true)) {
            if (!entity.equals(playerIn) && !playerIn.isAlliedTo(entity) && !entity.isAlliedTo(playerIn) && (entity instanceof Mob || entity instanceof Player) && playerIn.hasLineOfSight(entity)) {
                if (closestValid == null || playerIn.distanceTo(entity) < playerIn.distanceTo(closestValid)) {
                    closestValid = entity;
                }
            }
        }
        return launchSnakeBladeAt(playerIn, closestValid);
    }

    public static boolean launchSnakeBladeAt(LivingEntity playerIn, Entity closestValid) {
        Level worldIn = playerIn.level();
        if (!worldIn.isClientSide()) {
            if (closestValid != null) {
                SnakeBladeEntity segment = BuxinModEntities.SNAKE_BLADE.get().create(worldIn);
                if (segment != null) {
                    segment.copyPosition(playerIn);
                    worldIn.addFreshEntity(segment);
                    segment.setCreatorEntityUUID(playerIn.getUUID());
                    segment.setFromEntityID(playerIn.getId());
                    segment.setToEntityID(closestValid.getId());
                    segment.copyPosition(playerIn);
                    segment.setProgress(0.0F);
                    final Vec3 _center = new Vec3(playerIn.getX(), playerIn.getY(), playerIn.getZ());
                    List<Entity> _entfound = playerIn.level().getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8 / 2d), e -> true).stream()
                            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                            .toList();
                    for (Entity entityiterator : _entfound) {
                        if (entityiterator != playerIn && entityiterator instanceof LivingEntity) {
                            if (SystemMethod.isWindows()) {
                                VFXTool.addVFXParticle(new Vec3(entityiterator.getX(), entityiterator.getY() + 2.2, entityiterator.getZ()), "buxin", "danger", entityiterator.level());
                                Method_114514.play_sound(playerIn, "buxin:danger");
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
