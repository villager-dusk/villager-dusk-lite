
package net.mcreator.buxin.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class AggressiveMiningGoal extends Goal {
    private final Monster monster;
    private final int searchRadius;
    private BlockPos targetBlock;
    private int breakProgress;
    private int cooldown;
    private final List<BlockPos> recentMined = new ArrayList<>();

    public AggressiveMiningGoal(Monster monster, int searchRadius) {
        this.monster = monster;
        this.searchRadius = searchRadius;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (cooldown > 0) {
            cooldown--;
            return false;
        }

        LivingEntity target = monster.getTarget();
        if (target == null || !target.isAlive()) return false;

        this.targetBlock = findAggressiveMiningSpot(target);
        return this.targetBlock != null;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = monster.getTarget();
        return target != null && target.isAlive() &&
                targetBlock != null && !monster.level().isEmptyBlock(targetBlock) &&
                target.distanceToSqr(monster) < (searchRadius * searchRadius * 4);
    }

    @Override
    public void start() {
        this.breakProgress = 0;
        monster.getNavigation().moveTo(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ(), 1.2);
    }

    @Override
    public void stop() {
        this.targetBlock = null;
        this.breakProgress = 0;
        this.cooldown = 10;
        monster.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (targetBlock == null) return;

        LivingEntity target = monster.getTarget();
        if (target == null) {
            stop();
            return;
        }

        double distanceToBlock = monster.distanceToSqr(
                targetBlock.getX() + 0.5, targetBlock.getY(), targetBlock.getZ() + 0.5);

        if (distanceToBlock < 6.0) {
            monster.getNavigation().stop();

            breakProgress++;

            if (breakProgress % 3 == 0) {
                playMiningEffects(targetBlock);
            }

            if (breakProgress >= getFastBreakTime(targetBlock)) {
                breakBlockInstantly(targetBlock);
                findNextTargetImmediately(target);
            }
        } else {
            monster.getNavigation().moveTo(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ(), 1.2);
        }
    }

    private BlockPos findAggressiveMiningSpot(LivingEntity target) {
        Level level = monster.level();
        BlockPos targetPos = target.blockPosition();
        BlockPos monsterPos = monster.blockPosition();

        List<BlockPos> candidates = new ArrayList<>();

        for (Direction dir : Direction.Plane.HORIZONTAL) {
            for (int i = 0; i <= 2; i++) {
                BlockPos wallPos = targetPos.relative(dir, i);
                if (isValidTargetBlock(wallPos) && !recentMined.contains(wallPos)) {
                    candidates.add(wallPos);
                }
            }
        }

        List<BlockPos> lineOfSight = getDirectPath(monsterPos, targetPos);
        for (BlockPos pos : lineOfSight) {
            if (isValidTargetBlock(pos) && !recentMined.contains(pos) && pos.closerToCenterThan(Vec3.atCenterOf(targetPos), searchRadius)) {
                candidates.add(pos);
            }
        }

        if (candidates.isEmpty()) return null;

        Optional<BlockPos> best = candidates.stream()
                .filter(pos -> willCauseFallDamage(pos, target))
                .findFirst();

        if (best.isPresent()) return best.get();

        return candidates.stream()
                .min(Comparator.comparingDouble(pos -> monster.distanceToSqr(
                        pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5)))
                .orElse(null);
    }

    private void findNextTargetImmediately(LivingEntity target) {
        BlockPos nextTarget = findAggressiveMiningSpot(target);
        if (nextTarget != null) {
            this.targetBlock = nextTarget;
            this.breakProgress = 0;
            recentMined.add(targetBlock);
            if (recentMined.size() > 10) {
                recentMined.remove(0);
            }
        } else {
            stop();
        }
    }

    private boolean willCauseFallDamage(BlockPos pos, LivingEntity target) {
        if (pos.getY() == target.blockPosition().getY() - 1) {
            BlockPos below = pos.below();
            return monster.level().isEmptyBlock(below) ||
                    monster.level().getBlockState(below).isAir();
        }
        return false;
    }

    private int getFastBreakTime(BlockPos pos) {
        Level level = monster.level();
        BlockState state = level.getBlockState(pos);
        float hardness = state.getDestroySpeed(level, pos);

        int baseTime = 15;
        int time = (int) (baseTime * Math.max(0.3f, hardness));

        return Math.max(5, Math.min(30, time));
    }

    private void breakBlockInstantly(BlockPos pos) {
        Level level = monster.level();
        if (level.isEmptyBlock(pos)) return;

        for (int i = 0; i < 3; i++) {
            level.levelEvent(2001, pos, Block.getId(level.getBlockState(pos)));
        }

        level.destroyBlock(pos, true, monster);

        checkForCascadeEffect(pos);
    }

    private void checkForCascadeEffect(BlockPos center) {
        Level level = monster.level();

        BlockPos above = center.above();
        BlockState aboveState = level.getBlockState(above);
        if (aboveState.getBlock() == Blocks.SAND || aboveState.getBlock() == Blocks.GRAVEL) {
            level.destroyBlock(above, true, monster);
        }

        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos sidePos = center.relative(dir);
            if (isUnsupportedBlock(sidePos)) {
                level.destroyBlock(sidePos, true, monster);
            }
        }
    }

    private boolean isUnsupportedBlock(BlockPos pos) {
        Level level = monster.level();
        BlockState state = level.getBlockState(pos);
        BlockPos below = pos.below();

        return (state.getBlock() == Blocks.SAND || state.getBlock() == Blocks.GRAVEL) &&
                level.isEmptyBlock(below);
    }

    private boolean isValidTargetBlock(BlockPos pos) {
        Level level = monster.level();
        if (level.isEmptyBlock(pos)) return false;

        BlockState state = level.getBlockState(pos);

        if (state.getBlock() == Blocks.BEDROCK) return false;
        if (state.getDestroySpeed(level, pos) < 0) return false;
        if (state.getBlock() == Blocks.OBSIDIAN) return false;

        return true;
    }

    private List<BlockPos> getDirectPath(BlockPos from, BlockPos to) {
        List<BlockPos> path = new ArrayList<>();
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();
        int dz = to.getZ() - from.getZ();

        int steps = Math.max(Math.abs(dx), Math.max(Math.abs(dy), Math.abs(dz)));
        for (int i = 0; i <= steps; i++) {
            double t = (double) i / steps;
            int x = from.getX() + (int) (dx * t);
            int y = from.getY() + (int) (dy * t);
            int z = from.getZ() + (int) (dz * t);
            path.add(new BlockPos((int)x, (int)y, (int)z));
        }
        return path;
    }

    private void playMiningEffects(BlockPos pos) {
        Level level = monster.level();
        level.levelEvent(2001, pos, Block.getId(level.getBlockState(pos)));

        if (level instanceof ServerLevel serverLevel) {
            AABB box = new AABB(pos).inflate(1.0);
        }
    }
}
