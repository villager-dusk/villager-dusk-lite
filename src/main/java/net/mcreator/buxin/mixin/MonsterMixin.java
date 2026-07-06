
package net.mcreator.buxin.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Monster.class)
public class MonsterMixin {
    @Inject(
            method = "isDarkEnoughToSpawn",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void byd(ServerLevelAccessor p_219010_, BlockPos p_219011_, RandomSource p_219012_, @NotNull CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(true);
    }

    @Inject(
            method = "checkMonsterSpawnRules",
            at = @At("HEAD"),
            cancellable = true
    )

    private static void byd2(EntityType<? extends Monster> p_219014_, ServerLevelAccessor p_219015_, MobSpawnType p_219016_, BlockPos p_219017_, RandomSource p_219018_, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(true);
    }
}
