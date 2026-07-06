
package net.mcreator.buxin.mixin;

import net.mcreator.buxin.common_attackgoals.AddCommonAttackGoal;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.world.entity.animal.PolarBear;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PolarBear.class)
public class PolarBearMixin {
    @Inject(
            method = "registerGoals",
            at = @At("HEAD")
    )
    public void addgoal(CallbackInfo ci){
        PolarBear polarBear = (PolarBear)(Object)this;
        AddCommonAttackGoal.polarBear(polarBear);
        Method_114514.entity_use_command(polarBear,"/team join villager");
    }
}
