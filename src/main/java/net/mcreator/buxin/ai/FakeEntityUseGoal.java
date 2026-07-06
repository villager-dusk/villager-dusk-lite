
package net.mcreator.buxin.ai;

import net.mcreator.buxin.entity.TntEntity;
import net.mcreator.buxin.entity.fake_entity.FakeEntity;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FakeEntityUseGoal extends Goal {
    private final Mob user;
    private LivingEntity targetLivingEntity;
    private int cooldown = reducedTickDelay(50);

    public FakeEntityUseGoal(Mob user) {
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
            Mob entity = user;
            Level level = entity.level();
            if (level instanceof ServerLevel serverLevel) {
                Vec3 startPos = entity.position();
                int particleCount = 5;
                float radius = 3F;
                Vec3 particleOrigin = startPos.subtract(0, 1, 0);
                for (int i = 0; i < particleCount; i++) {
                    float angle = (float) i / particleCount * (float) Math.PI * 2;
                    float xOffset = radius * (float) Math.cos(angle);
                    float zOffset = radius * (float) Math.sin(angle);
                    Vec3 particlePos = particleOrigin.add(xOffset, 0, zOffset);
                    serverLevel.sendParticles(ParticleTypes.POOF, particlePos.x, particlePos.y + 2, particlePos.z, 20, 0, 0, 0, 0.1);
                    Method_114514.play_sound(entity, "entity.item.pickup");
                    FakeEntity fakeEntity = new FakeEntity(entity);
                    fakeEntity.setOwnerUUID(entity.getUUID());
                    fakeEntity.setPos(particleOrigin.add(xOffset, 1, zOffset));
                    fakeEntity.setCustomName(entity.getName());
                    serverLevel.addFreshEntity(fakeEntity);
                }
            }
        }
        this.cooldown = reducedTickDelay(240);
    }

    public void stop() {
        this.targetLivingEntity = null;
        this.user.getNavigation().stop();
    }
}
