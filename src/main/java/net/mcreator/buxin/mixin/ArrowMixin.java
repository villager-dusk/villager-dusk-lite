
package net.mcreator.buxin.mixin;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Arrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Arrow.class)
public class ArrowMixin {
    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void remove(CallbackInfo ci){
        Arrow entity = (Arrow) (Object)this;
        if(entity.isAlive() && !entity.level().isClientSide) {
            if (entity.tickCount >= 300) {
                entity.discard();
            }
        }
    }
}
