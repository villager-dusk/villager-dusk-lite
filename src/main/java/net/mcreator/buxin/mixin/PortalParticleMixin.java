
package net.mcreator.buxin.mixin;

import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.PortalParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PortalParticle.class)
public class PortalParticleMixin {
    @Inject(
            method = "getLightColor",
            at = @At("RETURN"),
            cancellable = true
    )
    public void getLightColor(float p_107564_, CallbackInfoReturnable<Integer> cir){
        cir.setReturnValue(15728880);
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public ParticleRenderType getRenderType() {
        // MC 1.20.1: ParticleRenderType.PARTICLE_SHEET_LIT was renamed to PARTICLE_SHEET_LIT (still exists) but signature unchanged
        // No change needed — ParticleRenderType.PARTICLE_SHEET_LIT is still valid and present in 1.20.1
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }
}
