
package net.mcreator.buxin.ai;

import net.mcreator.buxin.DelayedTask;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

import java.util.Random;

public class VillagerScoutHurt {
    public VillagerScoutHurt() {
    }
    public static void execute(final LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity, Entity entity1, DamageSource damageSource) {
        // Updated for 1.20.1: DamageSource comparison uses is() with DamageTypes
        if (!damageSource.is(DamageTypes.IN_WALL) && !damageSource.is(DamageTypes.CRAMMING) && entity instanceof LivingEntity livingentity && entity1 instanceof LivingEntity livingentity1) {
            if(entity.isPassenger()){
                entity.stopRiding();
                Entity vehicle = entity.getVehicle();
                if (vehicle != null) {
                    vehicle.ejectPassengers();
                }
            }
            if (Math.random() < 0.1) {
                Method_114514.play_sound(levelaccessor,d0,d1,d2,"minecraft:entity.ender_pearl.throw");
                ThrownEnderpearl projectile;
                Level level = entity.level();
                if (!level.isClientSide()) {
                    projectile = new ThrownEnderpearl(EntityType.ENDER_PEARL, level);
                    projectile.setOwner(entity);
                    projectile.setPos(entity.getX(), entity.getEyeY() - 0.1, entity.getZ());
                    projectile.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 1.0F, 0.0F);
                    level.addFreshEntity(projectile);
                }
                if (level instanceof ServerLevel) {
                    ((ServerLevel) level).sendParticles(ParticleTypes.REVERSE_PORTAL, entity.xOld, entity.yOld + 1.0, entity.zOld, 60, 0.65, 0.65, 0.65, 0.25);
                    level.playSound((Player) null, entity.xOld, entity.yOld + 1.0, entity.zOld, SoundEvents.ENDERMAN_TELEPORT, entity.getSoundSource(), 2.0F, 1.0F - ((new Random()).nextFloat() - 0.5F) * 0.2F);
                }
                if (Math.random() <= 0.7) {
                     new DelayedTask(40) {
                        public void run() {
                            Entity entity2 = entity;
                            Level level1 = entity2.level();
                            if (!level1.isClientSide()) {
                                Projectile projectile1 = new ThrownEnderpearl(EntityType.ENDER_PEARL, level1);
                                projectile1.setOwner(entity2);
                                projectile1.setPos(entity2.getX(), entity2.getEyeY() - 0.1, entity2.getZ());
                                projectile1.shoot(entity2.getLookAngle().x, entity2.getLookAngle().y, entity2.getLookAngle().z, 1.5F, 0.0F);
                                level1.addFreshEntity(projectile1);
                            }

                        }
                    };
                }
            }
        }
    }
}
