
package net.mcreator.buxin.ai;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.TntEntity;
import net.mcreator.buxin.init.BuxinModBlocks;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.my_method.VFXTool;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.gameasset.Animations;

import java.util.Comparator;
import java.util.List;

public class SpecialDarkObsidianZhuUseGoal extends Goal {
    public static void createExpandingExplosions(Entity sourceEntity, int iterations, double baseRadius) {
        if (sourceEntity.level().isClientSide()) return;

        Level level = sourceEntity.level();
        double centerX = sourceEntity.getX();
        double centerY = sourceEntity.getY();
        double centerZ = sourceEntity.getZ();

        for (int i = 1; i <= iterations; i++) {
            final int currentIteration = i;
            final double radius = baseRadius * currentIteration;
            BuxinMod.queueServerWork((int) i, () -> {
                int points = 12 + currentIteration * 2;
                for (int j = 0; j < points; j++) {
                    double angle = 2 * Math.PI * j / points;
                    double offsetX = Math.cos(angle) * radius * Math.sin(Math.PI / 2);
                    double offsetZ = Math.sin(angle) * radius * Math.sin(Math.PI / 2);

                    double x = centerX + offsetX;
                    double y = centerY;
                    double z = centerZ + offsetZ;
                    level.setBlock(new BlockPos((int) x, (int) y, (int) z), BuxinModBlocks.DARK_BLOCK_ZHU_2.get().defaultBlockState(), 3);
                }
            });
        }
    }

    private final Mob user;
    private LivingEntity targetLivingEntity;
    private int cooldown = reducedTickDelay(50);

    public SpecialDarkObsidianZhuUseGoal(Mob user) {
        this.user = user;
    }

    public boolean canUse() {
        LivingEntity target = this.user.getTarget();
        if (!(target instanceof LivingEntity))
            return false;

        if (--this.cooldown > 0)
            return false;

        return !this.user.getMainHandItem().isEmpty();
    }

    public void start() {
        this.targetLivingEntity = this.user.getTarget();
        if (targetLivingEntity == null) {
            return;
        }
        if (user.level() instanceof ServerLevel) {
            ItemStack stack0 = user.getMainHandItem();
            ItemStack stack1 = user.getOffhandItem();
            user.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.OBS_5.get()));
            user.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(BuxinModItems.OBS_5.get()));
            BuxinMod.queueServerWork(50, () -> {
                user.setItemSlot(EquipmentSlot.MAINHAND, stack0);
                user.setItemSlot(EquipmentSlot.OFFHAND, stack1);
            });
            AnimationPlayer.playAnimation(user, Animations.FIST_DASH);
            BuxinMod.queueServerWork(8, () -> {
                createExpandingExplosions(user, 3, 3);
                final Vec3 _center0 = new Vec3(user.getX(), user.getY(), user.getZ());
                List<Entity> _entfound0 = user.level().getEntitiesOfClass(Entity.class, new AABB(_center0, _center0).inflate(6 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center0))).toList();
                for (Entity entityiterator : _entfound0) {
                    if (entityiterator != user && entityiterator.isAlive()) {
                        entityiterator.hurt(entityiterator.damageSources().mobAttack(user), 8f);
                    }
                }
            });
        }
        this.cooldown = reducedTickDelay(160);
    }

    public void stop() {
        this.targetLivingEntity = null;
        this.user.getNavigation().stop();
    }
}
