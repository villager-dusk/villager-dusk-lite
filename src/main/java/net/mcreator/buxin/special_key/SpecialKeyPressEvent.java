
package net.mcreator.buxin.special_key;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.gameasset.PutEntityToDieAnimation;
import net.mcreator.buxin.gameasset.SpecialAnimation;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.HitAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.Random;

public class SpecialKeyPressEvent {
    public static void event(Entity entity) {
        if (entity == null) return;
        Random random = new Random();
        int value = random.nextInt(3);
        entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(EntityPatch -> {
            if (EntityPatch instanceof PlayerPatch<?> playerPatch) {
                DynamicAnimation animation = AnimationPlayer.getAnimation(playerPatch.getOriginal());
                
                if (!(animation instanceof LongHitAnimation) && !(animation instanceof HitAnimation) && !(animation instanceof PutEntityToDieAnimation) && !(animation instanceof SpecialAnimation)) {
                    WeaponCategory Maincategory = playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory();
                    WeaponCategory Offcategory = playerPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory();
                    if (entity.getPersistentData().getDouble("isattacking") == 0) {
                        if (Maincategory == CapabilityItem.WeaponCategories.GREATSWORD) {
                            entity.getPersistentData().putDouble("isattacking", 1);
                            if (value == 1)
                                playAnimation(entity, Animations.SWORD_AIR_SLASH);
                            else if (value == 2)
                                playAnimation(entity, Animations.SWORD_DUAL_AUTO1);
                            else
                                playAnimation(entity, BuxinAnimations.Attack3);
                        } else if (Maincategory == CapabilityItem.WeaponCategories.SWORD) {
                            entity.getPersistentData().putDouble("isattacking", 1);
                            if (Offcategory == CapabilityItem.WeaponCategories.SWORD) {
                                if (value == 1)
                                    playAnimation(entity, Animations.DANCING_EDGE);
                                else if (value == 2) {
                                    if(Math.random() > 0.5)
                                        playAnimation(entity, BuxinAnimations.D4);
                                    else {
                                        playAnimation(entity, BuxinAnimations.SWORD_DUAL_AUTO_1_BETTER);
                                    }
                                }
                                else
                                    playAnimation(entity, Animations.DAGGER_DUAL_DASH);
                            } else {
                                if (value == 1)
                                    playAnimation(entity, Animations.GREATSWORD_DASH);
                                else if (value == 2)
                                    playAnimation(entity, BuxinAnimations.SIMPLE_SWORD_AUTO_3);
                                else
                                    playAnimation(entity, BuxinAnimations.SIMPLE_SWORD_AUTO_3);
                            }
                        } else if (Maincategory == CapabilityItem.WeaponCategories.SPEAR) {
                            entity.getPersistentData().putDouble("isattacking", 1);
                            if (value == 1)
                                playAnimation(entity, Animations.SWORD_DUAL_AUTO1);
                            else if (value == 2)
                                playAnimation(entity, Animations.SWORD_DUAL_AUTO2);
                            else
                                playAnimation(entity, Animations.SWORD_DUAL_AUTO3);
                        } else if (Maincategory != CapabilityItem.WeaponCategories.FIST && entity instanceof LivingEntity && ((LivingEntity) entity).getMainHandItem().getItem() != Items.AIR) {
                            entity.getPersistentData().putDouble("isattacking", 1);
                            if (value == 1)
                                playAnimation(entity, Animations.SWORD_DUAL_AUTO1);
                            else if (value == 2)
                                playAnimation(entity, BuxinAnimations.SIMPLE_SWORD_AUTO_3);
                            else
                                playAnimation(entity, BuxinAnimations.SIMPLE_SWORD_AUTO_3);
                        } else {
                            entity.getPersistentData().putDouble("isattacking", 1);
                            if (value == 1)
                                playAnimation(entity, Animations.SWORD_DUAL_AUTO1);
                            else if (value == 2)
                                playAnimation(entity, BuxinAnimations.SIMPLE_SWORD_AUTO_3);
                            else
                                playAnimation(entity, BuxinAnimations.SIMPLE_SWORD_AUTO_3);
                        }
                        BuxinMod.queueServerWork(18, () -> {
                            entity.getPersistentData().putDouble("isattacking", 0);
                        });
                    }
                }
            }
        });
    }

    private static void playAnimation(Entity entity, StaticAnimation animation){
        LocalPlayerPatch playerpatch = EpicFightCapabilities.getEntityPatch(entity, LocalPlayerPatch.class);
        if(entity != null && animation != null && playerpatch != null) {
            playerpatch.playAnimationSynchronized(animation, 0f);
        }
    }
}
