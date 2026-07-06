package net.mcreator.buxin.my_method;

import net.mcreator.buxin.entity.EnchanterEntity;
import net.mcreator.buxin.entity.Niubi14Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.client.model.Mesh;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.guard.ParryingSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

public class AnimationPlayer {
    public static void playAnimation(Entity entity, StaticAnimation animation){
        if (!entity.level.isClientSide()) {
            try {
                entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(EntityPatch -> {
                    if (EntityPatch instanceof LivingEntityPatch<?> LivingEntityPatch) {
                        LivingEntityPatch.playAnimationSynchronized(animation, 0F);
                    }
                });
            } catch (Exception e){
                System.err.println("entity " + entity.getDisplayName().getString() +" was failed to play animation: " + animation.getLocation() + " err in : " + e);
            }
        }
    }

    private static DynamicAnimation animation;
    public static DynamicAnimation entity_getAnimation(Entity entity){
        entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(entitypatch -> {
            if (entitypatch instanceof LivingEntityPatch<?> livingEntityPatch) {
                animation = livingEntityPatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
            }
        });
        return animation;
    }
    public static DynamicAnimation getAnimation(Entity entity){
        entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(entitypatch -> {
            if (entitypatch instanceof LivingEntityPatch<?> livingEntityPatch) {
                animation = livingEntityPatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
            }
        });
        return animation;
    }

    public static DynamicAnimation getAnimation(LivingEntityPatch<?> livingEntityPatch){
        return livingEntityPatch.getAnimator().getPlayerFor(null).getAnimation();
    }
    public static int getAnimationId(StaticAnimation animation){
        return animation.getId();
    }
    public static float getAnimationConvertTime(StaticAnimation animation){ return animation.getConvertTime(); }
    public static float getAnimationTotalTime(StaticAnimation animation){return animation.getTotalTime();}
    public static ResourceLocation getAnimationLocation(StaticAnimation animation){return animation.getLocation();}
    public static ResourceLocation getAnimationRegistryName(StaticAnimation animation){return animation.getRegistryName();}

    public static LivingEntityPatch<?> getlivingEntityPatch(Entity entity){
        return (LivingEntityPatch<?>) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class) != null ? (LivingEntityPatch<?>) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class) : null;
    }
    public static PlayerPatch<?> getplayerPatch(Player player){
        return (PlayerPatch<?>) EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class) != null ? (PlayerPatch<?>) EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class) : null;
    }
}
