
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.mcreator.buxin.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

public class LadderClimbGoal extends Goal {
    private final Mob entity;
    private Path path;

    public LadderClimbGoal(Mob entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    public boolean canUse() {
        if (this.entity.getTarget() == null) {
            return false;
        } else if (this.entity.getNavigation().isDone()) {
            return false;
        } else {
            this.path = this.entity.getNavigation().getPath();
            return this.path != null && this.entity.onClimbable();
        }
    }

    public void tick() {
        int i = this.path.getNextNodeIndex();
        if (i + 1 < this.path.getNodeCount()) {
            int y = this.path.getNode(i).y;
            Node pointNext = this.path.getNode(i + 1);
            BlockState down = this.entity.level().getBlockState(this.entity.blockPosition().below());
            double yMotion;
            if (pointNext.y >= y && (pointNext.y != y || isLadder(down, this.entity, this.entity.blockPosition().below()))) {
                yMotion = 0.15;
            } else {
                yMotion = -0.15;
            }

            this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0.0, yMotion, 0.0));
        }
    }
    public static boolean isLadder(BlockState state, LivingEntity entity, BlockPos pos) {
        return state.isLadder(entity.level(), pos, entity);
    }
}
