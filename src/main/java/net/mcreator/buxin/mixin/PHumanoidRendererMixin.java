
package net.mcreator.buxin.mixin;

import net.mcreator.buxin.client.eyes.renderer.biped.normal.GlowingEyesLayer;
import net.mcreator.buxin.client.eyes.renderer.biped.patched_layer.PatchedGlowingEyesLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.client.model.MeshProvider;
import yesman.epicfight.client.mesh.HumanoidMesh;
import yesman.epicfight.client.renderer.patched.entity.PHumanoidRenderer;

@Mixin(PHumanoidRenderer.class)
public class PHumanoidRendererMixin {
    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void layer(MeshProvider<?> mesh, EntityRendererProvider.Context context, EntityType entityType, CallbackInfo ci){
        PHumanoidRenderer<?,?,?,?,?> pHumanoidRenderer = (PHumanoidRenderer<?,?,?,?,?>) (Object)this;
        pHumanoidRenderer.addPatchedLayer(GlowingEyesLayer.class, new PatchedGlowingEyesLayer(mesh));
    }
}
