
package net.mcreator.buxin.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(NearestAttackableTargetGoal.class)
public class NearestAttackableTargetGoalMixin {
    @Shadow @Nullable protected LivingEntity target;

    @Inject(
            method = "findTarget",
            at = @At("TAIL"),
            cancellable = true
    )
    private void restrictTargetToPlayerOnly(CallbackInfo ci) {
        if (this.target != null && !(this.target instanceof Player)) {
            this.target = null;
        }
    }
}
