
package net.mcreator.buxin.mixin;

import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownEnderpearl.class)
public class ThrownEnderperalMixin {

    @Inject(method = "onHit", at = @At("TAIL"))
    private void M_onHit(HitResult result, CallbackInfo ci) {
        ThrownEnderpearl pearl = (ThrownEnderpearl)(Object)this;
        if (!pearl.level().isClientSide()) { // ✅ 1.20.1: World level -> level() method (getter), no field access
            Method_114514.entity_use_command(pearl,"/execute at @s run particle buxin:ender ~ ~1 ~ 0 0 0 0.08 18");
            pearl.playSound(SoundEvents.ENDERMAN_TELEPORT,2,1);
            /*
            for(int i = 0; i < 32; ++i) {
                pearl.level.addParticle(ParticleTypes.PORTAL, pearl.getX(), pearl.getY() + pearl.random.nextDouble() * 2.0, pearl.getZ(), pearl.random.nextGaussian(), 0.0, pearl.random.nextGaussian());
            }
             */
        }
    }

    @Inject(method = "onHitEntity", at = @At("TAIL"))
    private void M_onHitEntity(EntityHitResult p_37502_, CallbackInfo ci) {
        ThrownEnderpearl pearl = (ThrownEnderpearl)(Object)this;
        if (!pearl.level().isClientSide()) { // ✅ 1.20.1: World level -> level() method (getter), no field access
            Method_114514.entity_use_command(pearl,"/execute at @s run particle buxin:ender ~ ~1 ~ 0 0 0 0.08 18");
            pearl.playSound(SoundEvents.ENDERMAN_TELEPORT,2,1);
            /*
            for(int i = 0; i < 32; ++i) {
                pearl.level.addParticle(ParticleTypes.PORTAL, pearl.getX(), pearl.getY() + pearl.random.nextDouble() * 2.0, pearl.getZ(), pearl.random.nextGaussian(), 0.0, pearl.random.nextGaussian());
            }
             */
        }
    }
}
