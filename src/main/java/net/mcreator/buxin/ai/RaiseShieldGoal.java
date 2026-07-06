
package net.mcreator.buxin.ai;

import net.mcreator.buxin.entity.father.BattleVillagerEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ToolActions;

public class RaiseShieldGoal extends Goal {

    public final BattleVillagerEntity villager;

    public RaiseShieldGoal(BattleVillagerEntity villager) {
        this.villager = villager;
    }

    @Override
    public boolean canUse() {
        ItemStack offhand = villager.getOffhandItem();
        return !offhand.isEmpty()
                && offhand.canPerformAction(ToolActions.SHIELD_BLOCK)
                && raiseShield();
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse();
    }

    @Override
    public void start() {
        ItemStack offhand = villager.getOffhandItem();
        if (!offhand.isEmpty()
                && offhand.canPerformAction(ToolActions.SHIELD_BLOCK)) {
            villager.startUsingItem(InteractionHand.OFF_HAND);
        }
    }

    @Override
    public void stop() {
        // if (!villagerConfig.villagerAlwaysShield)
        //     villager.stopUsingItem();
    }

    protected boolean raiseShield() {
        LivingEntity target = villager.getTarget();
        if (target != null && villager.shieldCoolDown == 0) {
            return true;
        }
        return false;
    }
}
