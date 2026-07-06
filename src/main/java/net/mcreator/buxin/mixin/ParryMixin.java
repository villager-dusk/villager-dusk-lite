
package net.mcreator.buxin.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.skill.guard.ParryingSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

@Mixin(
        value = {ParryingSkill.class},
        remap = false
)
public abstract class ParryMixin extends GuardSkill {

    public ParryMixin(GuardSkill.Builder builder) {
        super(builder);
    }

    @Inject(
            method = {"createActiveGuardBuilder"},
            at = {@At("RETURN")}
    )
    private static void InjectAdvancedGuardMotion(CallbackInfoReturnable<GuardSkill.Builder> info) {
        GuardSkill.Builder builder = (GuardSkill.Builder)info.getReturnValue();
        builder.addAdvancedGuardMotion(WeaponCategories.GREATSWORD, (item, playerpatch) -> {
            return new StaticAnimation[]{Animations.GREATSWORD_GUARD_HIT};
        });
        builder.addGuardMotion(WeaponCategories.GREATSWORD, (item, playerpatch) -> {
            return Animations.GREATSWORD_GUARD_HIT;
        });
    }
}
