
package net.mcreator.buxin.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class SuperStaminaEffectMobEffect extends MobEffect {
    public SuperStaminaEffectMobEffect() {
        super(MobEffectCategory.NEUTRAL, -39373);
    }

    @Override
    public String getDescriptionId() {
        return "effect.buxin.super_stamina_effect";
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
