
package net.mcreator.buxin.mixin;

import net.mcreator.buxin.config.common.GuardShakeValueConfig;
import net.mcreator.buxin.init.BuxinModGameRules;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.guard.ParryingSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.HurtEvent;

import java.util.Comparator;
import java.util.List;

@Mixin(ParryingSkill.class)
public class ParryingMixin {
    @Inject(
            method = "guard",
            at = @At(value = "TAIL"),
            remap = false
    )
    public void guardskillbook(SkillContainer container, CapabilityItem itemCapability, HurtEvent.Pre event, float knockback, float impact, boolean advanced, CallbackInfo ci){
        //System.out.println("114514");
        ServerPlayer entity = (ServerPlayer)((ServerPlayerPatch)event.getPlayerPatch()).getOriginal();
        Method_114514.entity_use_command(entity, "/particle buxin:huohua ^ ^1.5 ^ 0 0 0 0.11" + " " + entity.level().getLevelData().getGameRules().getInt(BuxinModGameRules.SPARK_NUMBER));
        final Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
        List<LivingEntity> _entfound = entity.level().getEntitiesOfClass(LivingEntity.class, new AABB(_center, _center).inflate(6.5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
        for (LivingEntity entityiterator : _entfound) {
            if (entityiterator instanceof Player) {
                Method_114514.entity_use_command(entityiterator, "/impactful @s shake " + GuardShakeValueConfig.GuardShakeTimeValue.get() + " " + GuardShakeValueConfig.GuardShakeAmplitudeValue.get() + " " + GuardShakeValueConfig.GuardShakeFrequencyValue.get());
            }
        }
        if(event.getDamageSource().getEntity() instanceof LivingEntity mob){
            // System.err.println(sourceentity.getDisplayName().getString() + " ," + sourceentity.getPersistentData().getInt("guard_pf"));
            if(!mob.level().isClientSide){
                if(mob.getPersistentData().getInt("guard_pf") < 11) { // != 10
                    mob.getPersistentData().putInt("guard_pf", mob.getPersistentData().getInt("guard_pf") + 1);
                } else {
                    Method_114514.play_sound(mob,"epicfight:sfx.neutralize_mobs");
                    mob.getPersistentData().putInt("guard_pf",0);
                    AnimationPlayer.playAnimation(mob, Animations.BIPED_COMMON_NEUTRALIZED);
                }
            }
        }
    }
}
