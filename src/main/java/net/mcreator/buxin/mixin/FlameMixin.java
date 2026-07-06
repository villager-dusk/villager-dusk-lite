
package net.mcreator.buxin.mixin;

import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.ParticleRenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlameParticle.class)
public class FlameMixin {
    @Inject(
            method = "getRenderType",
            at = @At("RETURN"),
            cancellable = true
    )
    public void rendertype(CallbackInfoReturnable<ParticleRenderType> cir){
        cir.setReturnValue(ParticleRenderType.PARTICLE_SHEET_LIT);
    }
    @Inject(
            method = "getLightColor",
            at = @At("RETURN"),
            cancellable = true
    )
    public void getLightColor(float p_106821_, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(15728880);
    }
}
