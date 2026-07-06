
package net.mcreator.buxin.ai;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class AutoMineGoal extends Goal {
    private final PathfinderMob entity;
    private BlockPos targetPos;
    private int miningTime;
    private int cooldown;
    private static final Block[] MINEABLE_BLOCKS = {
            Blocks.STONE,
            Blocks.COAL_ORE,
            Blocks.DEEPSLATE_COAL_ORE,
            Blocks.IRON_ORE,
            Blocks.DEEPSLATE_IRON_ORE,
            Blocks.COPPER_ORE,
            Blocks.DEEPSLATE_COPPER_ORE,
            Blocks.GOLD_ORE,
            Blocks.DEEPSLATE_GOLD_ORE,
            Blocks.DIAMOND_ORE,
            Blocks.DEEPSLATE_DIAMOND_ORE,
            Blocks.EMERALD_ORE,
            Blocks.DEEPSLATE_EMERALD_ORE,
            Blocks.REDSTONE_ORE,
            Blocks.DEEPSLATE_REDSTONE_ORE,
            Blocks.LAPIS_ORE,
            Blocks.DEEPSLATE_LAPIS_ORE,
            Blocks.NETHER_QUARTZ_ORE,
            Blocks.NETHER_GOLD_ORE,
            Blocks.ANCIENT_DEBRIS
    };

    public AutoMineGoal(PathfinderMob entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (cooldown > 0) {
            cooldown--;
            return false;
        }

        if (entity.level().isDay()) {
            return false;
        }
        targetPos = findMineableBlock();
        return targetPos != null;
    }

    @Override
    public boolean canContinueToUse() {
        return targetPos != null && miningTime < 100 && !entity.level().isDay();
    }

    @Override
    public void start() {
        miningTime = 0;
        entity.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1.0);
    }

    @Override
    public void tick() {
        if (targetPos == null) return;

        double distance = entity.distanceToSqr(targetPos.getX(), targetPos.getY(), targetPos.getZ());

        if (distance < 4.0) {
            entity.getLookControl().setLookAt(targetPos.getX(), targetPos.getY(), targetPos.getZ());
            entity.getNavigation().stop();
            miningTime++;
            if (miningTime % 20 == 0) {
                tryMineBlock();
            }
            if (miningTime % 5 == 0) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    entity.getServer().getCommands().performPrefixedCommand(
                            new CommandSourceStack(
                                    CommandSource.NULL,
                                    entity.position(),
                                    entity.getRotationVector(),
                                    entity.level() instanceof ServerLevel ? (ServerLevel) entity.level() : null,
                                    4,
                                    entity.getDisplayName().getString(),
                                    entity.getDisplayName(),
                                    entity.level().getServer(),
                                    entity
                            ),
                            "/indestructible @s play \"buxin:biped/combat/dig\" 0.5 2"
                    );
                }
            }
        } else {
            entity.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1.0);
        }
    }

    @Override
    public void stop() {
        targetPos = null;
        miningTime = 0;
        cooldown = 10;
        entity.getNavigation().stop();
    }

    private BlockPos findMineableBlock() {
        BlockPos entityPos = entity.blockPosition();
        int radius = 16;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -4; y <= 4; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos checkPos = entityPos.offset(x, y, z);
                    if (isMineable(checkPos) && canReach(checkPos)) {
                        return checkPos;
                    }
                }
            }
        }
        return null;
    }

    private boolean isMineable(BlockPos pos) {
        BlockState state = entity.level().getBlockState(pos);
        for (Block block : MINEABLE_BLOCKS) {
            if (state.is(block)) {
                return true;
            }
        }
        return false;
    }

    private boolean canReach(BlockPos pos) {
        return entity.level().isEmptyBlock(pos.above());
    }

    private void tryMineBlock() {
        if (targetPos == null) return;
        if (!isMineable(targetPos)) {
            stop();
            return;
        }
        if (!entity.level().isClientSide() && entity.getServer() != null) {
            entity.getServer().getCommands().performPrefixedCommand(
                    new CommandSourceStack(
                            CommandSource.NULL,
                            entity.position(),
                            entity.getRotationVector(),
                            entity.level() instanceof ServerLevel ? (ServerLevel) entity.level() : null,
                            4,
                            entity.getDisplayName().getString(),
                            entity.getDisplayName(),
                            entity.level().getServer(),
                            entity
                    ),
                    "/indestructible @s play \"buxin:biped/combat/dig\" 0.5 2"
            );
        }
        if (miningTime >= 20) {
            mineBlock();
            stop();
        }
    }

    private void mineBlock() {
        BlockState state = entity.level().getBlockState(targetPos);
        Block block = state.getBlock();
        if (!entity.level().isClientSide() && entity.getServer() != null) {
            entity.getServer().getCommands().performPrefixedCommand(
                    new CommandSourceStack(
                            CommandSource.NULL,
                            entity.position(),
                            entity.getRotationVector(),
                            entity.level() instanceof ServerLevel ? (ServerLevel) entity.level() : null,
                            4,
                            entity.getDisplayName().getString(),
                            entity.getDisplayName(),
                            entity.level().getServer(),
                            entity
                    ),
                    "/indestructible @s play \"buxin:biped/combat/dig\" 0.5 2"
            );
        }
        entity.level().destroyBlock(targetPos, false);
        ItemStack drop = getDropForBlock(block);
        if (!drop.isEmpty()) {
            entity.spawnAtLocation(drop);
        }
        entity.playSound(state.getSoundType().getBreakSound(), 1.0F, 1.0F);
    }

    private ItemStack getDropForBlock(Block block) {
        if (block == Blocks.COAL_ORE || block == Blocks.DEEPSLATE_COAL_ORE) {
            return new ItemStack(Items.COAL, 1 + entity.level().random.nextInt(2));
        } else if (block == Blocks.IRON_ORE || block == Blocks.DEEPSLATE_IRON_ORE) {
            return new ItemStack(Items.RAW_IRON, 1 + entity.level().random.nextInt(2));
        } else if (block == Blocks.COPPER_ORE || block == Blocks.DEEPSLATE_COPPER_ORE) {
            return new ItemStack(Items.RAW_COPPER, 2 + entity.level().random.nextInt(3));
        } else if (block == Blocks.GOLD_ORE || block == Blocks.DEEPSLATE_GOLD_ORE) {
            return new ItemStack(Items.RAW_GOLD, 1 + entity.level().random.nextInt(2));
        } else if (block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE) {
            return new ItemStack(Items.DIAMOND, 1);
        } else if (block == Blocks.EMERALD_ORE || block == Blocks.DEEPSLATE_EMERALD_ORE) {
            return new ItemStack(Items.EMERALD, 1);
        } else if (block == Blocks.REDSTONE_ORE || block == Blocks.DEEPSLATE_REDSTONE_ORE) {
            return new ItemStack(Items.REDSTONE, 4 + entity.level().random.nextInt(3));
        } else if (block == Blocks.LAPIS_ORE || block == Blocks.DEEPSLATE_LAPIS_ORE) {
            return new ItemStack(Items.LAPIS_LAZULI, 4 + entity.level().random.nextInt(4));
        } else if (block == Blocks.NETHER_QUARTZ_ORE) {
            return new ItemStack(Items.QUARTZ, 1 + entity.level().random.nextInt(2));
        } else if (block == Blocks.ANCIENT_DEBRIS) {
            return new ItemStack(Items.ANCIENT_DEBRIS, 1);
        }
        return ItemStack.EMPTY;
    }
}
