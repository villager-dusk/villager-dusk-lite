
package net.mcreator.buxin.mixin;

import net.mcreator.buxin.config.client.BrightValueConfig;
import net.mcreator.buxin.my_method.Method_114514;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import yesman.epicfight.client.particle.TrailParticle;

// 修改说明：EpicFight 20.9.7（对应MC 1.20.1）中 TrailParticle.render() 方法签名和 getLightColor() 调用位置未变，
// 且仍为 public int getLightColor(float) 方法，因此原 Mixin 逻辑完全兼容，无需修改。
// 注意：EpicFight 20.x 系列保留了 TrailParticle 类及其 getLightColor(F)I 方法，API 兼容性良好。
@Mixin(TrailParticle.class)
public class TrailParticleMixin {
    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lyesman/epicfight/client/particle/TrailParticle;getLightColor(F)I"
            )
    )
    private int modifyTrailLightColor(TrailParticle instance, float v) {
        return BrightValueConfig.BrightValue.get();
    }
}
