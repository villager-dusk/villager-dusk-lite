
package net.mcreator.buxin.ai;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.father.BattleVillagerEntity;
import net.mcreator.buxin.init.BuxinModMobEffects;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.my_method.SystemMethod;
import net.mcreator.buxin.my_method.VFXTool;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.Comparator;
import java.util.List;

public class ShieldBashUseGoal extends Goal {
    private final Mob user;
    private LivingEntity targetLivingEntity;
    private int cooldown = reducedTickDelay(50);

    public ShieldBashUseGoal(Mob user) {
        this.user = user;
    }

    public boolean canUse() {
        LivingEntity target = this.user.getTarget();
        if (!(target instanceof LivingEntity))
            return false;

        if (--this.cooldown > 0)
            return false;
        var patch = AnimationPlayer.getlivingEntityPatch(user);
        WeaponCategory offcategory = null;
        if (patch != null) {
            offcategory = patch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory();
        }
        if (offcategory != CapabilityItem.WeaponCategories.SHIELD || !(user.getOffhandItem().getItem() instanceof ShieldItem)) {
            return false;
        }

        if (user.getMainHandItem().getItem() == Items.BOW) {
            return false;
        }

        if (user.distanceTo(target) > 3) {
            return false;
        }
        return !this.user.getMainHandItem().isEmpty();
    }

    public void start() {
        this.targetLivingEntity = this.user.getTarget();
        if (targetLivingEntity == null) {
            return;
        }

        // 1.20.1中 ServerLevel 是 final 类，instanceof 检查仍然有效
        if (user.level() instanceof ServerLevel serverLevel) {
            {
                final Vec3 _center = user.position();
                List<LivingEntity> _entfound = serverLevel.getEntitiesOfClass(LivingEntity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream()
                        .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                        .toList();
                for (Entity entityiterator : _entfound) {
                    if (!(user == entityiterator) && !(entityiterator instanceof Projectile) && !(entityiterator instanceof BattleVillagerEntity) && !(entityiterator.getPersistentData().getBoolean("isfuck")) && entityiterator instanceof LivingEntity) {
                        if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
                            _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 5, 1, false, false));
                        user.lookAt(EntityAnchorArgument.Anchor.EYES, entityiterator.getEyePosition());
                        Method_114514.play_sound(entityiterator, "epicfight:sfx.neutralize_bosses");
                        Method_114514.entity_use_command(entityiterator, "/particle epicfight:hit_blunt ^ ^1.5 ^ 0.1 0.1 0.1 1 1");
                        if (entityiterator instanceof Player player) {
                            Method_114514.entity_use_command(entityiterator, "/impactful @s shake 40 6 6");
                            if (AnimationPlayer.getlivingEntityPatch(entityiterator) instanceof PlayerPatch<?> playerPatch) {
                                playerPatch.setStamina(playerPatch.getStamina() - playerPatch.getMaxStamina() * 0.2f);
                            }
                        }
// indestructible 20.9.7 命令格式保持一致
                        Method_114514.entity_use_command(user, "/indestructible @s play \"wom:biped/skill/gezets_auto_1\" 0 1");
                        BuxinMod.queueServerWork(3, () -> {
                            if (user.level() instanceof ServerLevel) {
                                entityiterator.hurt(entityiterator.damageSources().mobAttack(user), 9f);
                            }
                            BuxinMod.queueServerWork(3, () -> {
                                Method_114514.entity_use_command(entityiterator, "/indestructible @s play \"epicfight:biped/skill/guard_break1\" 0 1");
                            });
                        });
                    }
                }
            }
        }
        this.cooldown = reducedTickDelay(60);
    }

    public void stop() {
        this.targetLivingEntity = null;
        this.user.getNavigation().stop();
    }
}
