package net.mcreator.buxin.mixin;

import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Zombie.class)
public class ZombieMixin {
    @Redirect(
            method = "aiStep",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Zombie;isSunSensitive()Z")
    )
    private boolean test(Zombie instance){
        return false;
    }
}