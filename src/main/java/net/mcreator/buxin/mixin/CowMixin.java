
package net.mcreator.buxin.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Cow.class)
public class CowMixin {
    @Inject(
            method = "mobInteract",
            at = @At("HEAD")
    )
    public void interactionResult(Player sourceentity, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir){
        Cow entity = (Cow)(Object)this;
        sourceentity.getItemInHand(hand);
        sourceentity.startRiding(entity);
        if (entity != null && sourceentity != null) {
            sourceentity.startRiding(entity);
            if (sourceentity.isShiftKeyDown()) {
                entity.stopRiding();
            }
        }
    }
}
