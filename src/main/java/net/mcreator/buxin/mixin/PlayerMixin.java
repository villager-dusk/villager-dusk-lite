
package net.mcreator.buxin.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Unique
    private static final UUID LEVEL_DAMAGE_UUID = UUID.fromString("ABCD1234-5678-9012-3456-7890ABCDEF12");
    
    @Unique
    private int cachedLevel = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        Player self = (Player) (Object) this;
        if(self.tickCount % 10 == 0) {
            int currentLevel = self.experienceLevel;
            if (currentLevel != cachedLevel) {
                cachedLevel = currentLevel;
                updateLevelDamage(self, currentLevel);
            }
        }
    }

    @Unique
    private void updateLevelDamage(Player player, int level) {
        AttributeInstance damageAttr = player.getAttribute(Attributes.ATTACK_DAMAGE);
        if (damageAttr == null) return;
        damageAttr.removeModifier(LEVEL_DAMAGE_UUID);
        double bonus = level * 0.1;
        if (bonus > 0) {
            AttributeModifier modifier = new AttributeModifier(
                LEVEL_DAMAGE_UUID,
                "Level Damage",
                bonus,
                AttributeModifier.Operation.ADDITION
            );
            damageAttr.addTransientModifier(modifier);
        }
    }

    /**
     * @author 114514
     * @reason niubi
     */
    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void playTime(CallbackInfo ci){
        Player player = (Player) (Object)this;
        if(player.tickCount % 1200 == 0){
            player.getPersistentData().putInt("played_minutes",player.getPersistentData().getInt("played_minutes") + 1);
        }
    }
}
