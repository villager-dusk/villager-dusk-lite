
package net.mcreator.buxin.ai;

import net.mcreator.buxin.entity.TntEntity;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class TNTBlockUseGoal extends Goal {
    private final Mob user;
    private LivingEntity targetLivingEntity;
    private int cooldown = reducedTickDelay(50);

    public TNTBlockUseGoal(Mob user){
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
        if(targetLivingEntity == null){
            return;
        }
        if(user.level() instanceof ServerLevel) {
            if(Math.random() > 0.85917813) {
                Method_114514.play_sound(user, "entity.tnt.primed");
                TntEntity tnt = new TntEntity(BuxinModEntities.TNT.get(), user.level());
                tnt.setPos(targetLivingEntity.blockPosition().getCenter());
                tnt.setOwner(user);
                ((ServerLevel) user.level()).addFreshEntity(tnt);
            } else {
                Level world = user.level();
                Method_114514.play_sound(user,"buxin:bomb");
                if (user.level() instanceof ServerLevel projectileLevel) {
                    Projectile _entityToSpawn = new Object() {
                        public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                            AbstractArrow entityToSpawn = new TntEntity(BuxinModEntities.TNT.get(), level);
                            entityToSpawn.setOwner(shooter);
                            entityToSpawn.setSilent(true);
                            return entityToSpawn;
                        }
                    }.getArrow(projectileLevel, user, 10, 1);
                    _entityToSpawn.setPos(user.getX(), user.getY(), user.getZ());
                    _entityToSpawn.shoot((targetLivingEntity.getLookAngle().x), (targetLivingEntity.getLookAngle().y), (targetLivingEntity.getLookAngle().z), (float) 1.4f, (float) 1.7f);
                    projectileLevel.addFreshEntity(_entityToSpawn);
                }
            }
        }
        this.cooldown = reducedTickDelay(90);
    }

    public void stop() {
        this.targetLivingEntity = null;
        this.user.getNavigation().stop();
    }
}
