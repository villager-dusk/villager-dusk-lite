
package net.mcreator.buxin.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class PiglinSpecificSensor extends Sensor<LivingEntity> {
    
    public PiglinSpecificSensor() {
    }

    @Override
    public @NotNull Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_NEMESIS,
            MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD,
            MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM,
            MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN,
            MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN,
            MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS,
            MemoryModuleType.NEARBY_ADULT_PIGLINS,
            MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT,
            MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT,
            MemoryModuleType.NEAREST_REPELLENT
        );
    }

    @Override
    protected void doTick(@NotNull ServerLevel level, LivingEntity entity) {
        Brain<?> brain = entity.getBrain();
        
        // 寻找最近的驱赶物（如灵魂火）
        brain.setMemory(MemoryModuleType.NEAREST_REPELLENT, findNearestRepellent(level, entity));
        
        // 初始化各种可选的记忆值
        Optional<Mob> nearestNemesis = Optional.empty();
        Optional<Hoglin> nearestHuntableHoglin = Optional.empty();
        Optional<Hoglin> nearestBabyHoglin = Optional.empty();
        Optional<Piglin> nearestBabyPiglin = Optional.empty();
        Optional<LivingEntity> nearestZombified = Optional.empty();
        Optional<Player> nearestTargetablePlayerWithoutGold = Optional.empty();
        Optional<Player> nearestPlayerHoldingLovedItem = Optional.empty();
        
        int visibleAdultHoglinCount = 0;
        List<AbstractPiglin> visibleAdultPiglins = Lists.newArrayList();
        List<AbstractPiglin> nearbyAdultPiglins = Lists.newArrayList();
        
        // 获取可见的生物列表
        NearestVisibleLivingEntities visibleEntities = brain
            .getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
            .orElse(NearestVisibleLivingEntities.empty());
        
        // 遍历所有可见生物
        for (LivingEntity visibleEntity : visibleEntities.findAll(entity2 -> true)) {
            // 处理疣猪兽
            if (visibleEntity instanceof Hoglin hoglin) {
                if (hoglin.isBaby() && nearestBabyHoglin.isEmpty()) {
                    nearestBabyHoglin = Optional.of(hoglin);
                } else if (hoglin.isAdult()) {
                    visibleAdultHoglinCount++;
                    if (nearestHuntableHoglin.isEmpty() && hoglin.canBeHunted()) {
                        nearestHuntableHoglin = Optional.of(hoglin);
                    }
                }
            }
            // 处理猪灵蛮兵
            else if (visibleEntity instanceof PiglinBrute piglinBrute) {
                visibleAdultPiglins.add(piglinBrute);
            }
            // 处理普通猪灵
            else if (visibleEntity instanceof Piglin piglin) {
                if (piglin.isBaby() && nearestBabyPiglin.isEmpty()) {
                    nearestBabyPiglin = Optional.of(piglin);
                } else if (piglin.isAdult()) {
                    visibleAdultPiglins.add(piglin);
                }
            }
            // 处理玩家
            else if (visibleEntity instanceof Player player) {
                if (nearestTargetablePlayerWithoutGold.isEmpty() &&
                        !net.minecraft.world.entity.monster.piglin.PiglinAi.isWearingGold(player) &&
                        entity.canAttack(visibleEntity)) {
                    nearestTargetablePlayerWithoutGold = Optional.of(player);
                }

                if (nearestPlayerHoldingLovedItem.isEmpty() &&
                        !player.isSpectator() &&
                        net.minecraft.world.entity.monster.piglin.PiglinAi.isPlayerHoldingLovedItem(player)) {
                    nearestPlayerHoldingLovedItem = Optional.of(player);
                }
            }
            // 处理天敌（凋灵骷髅或凋灵）
            else if (nearestNemesis.isEmpty() &&
                    (visibleEntity instanceof WitherSkeleton || visibleEntity instanceof WitherBoss)) {
                nearestNemesis = Optional.of((Mob) visibleEntity);
            }
            // 处理僵尸化猪灵
            else if (nearestZombified.isEmpty() && net.minecraft.world.entity.monster.piglin.PiglinAi.isZombified(visibleEntity.getType())) {
                nearestZombified = Optional.of(visibleEntity);
            }
        }
        
        // 获取附近的所有生物（不仅限于可见的）
        List<LivingEntity> nearbyEntities = brain
            .getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES)
            .orElse(ImmutableList.of());
        
        // 收集附近的成年猪灵（包括不可见的）
        for (LivingEntity nearbyEntity : nearbyEntities) {
            if (nearbyEntity instanceof AbstractPiglin piglin && piglin.isAdult()) {
                nearbyAdultPiglins.add(piglin);
            }
        }
        
        // 将所有收集到的信息存储到记忆中
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS, nearestNemesis);
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, nearestHuntableHoglin);
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, nearestBabyHoglin);
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, nearestZombified);
        brain.setMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, nearestTargetablePlayerWithoutGold);
        brain.setMemory(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, nearestPlayerHoldingLovedItem);
        brain.setMemory(MemoryModuleType.NEARBY_ADULT_PIGLINS, nearbyAdultPiglins);
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, visibleAdultPiglins);
        brain.setMemory(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, visibleAdultPiglins.size());
        brain.setMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, visibleAdultHoglinCount);
    }

    /**
     * 寻找最近的驱赶物（猪灵会远离的东西）
     */
    private static Optional<BlockPos> findNearestRepellent(ServerLevel level, LivingEntity entity) {
        // Minecraft 1.20.1+ 中 BlockPos.findClosestMatch 的签名已变更：参数顺序和类型调整
        // 新签名：findClosestMatch(BlockPos center, int radiusXZ, int radiusY, Predicate<BlockPos> predicate)
        // 原代码参数正确（center, radiusXZ=8, radiusY=4, predicate），无需修改参数顺序
        // 但需注意：Mojang 在 1.20.1 中未改动此方法签名，仍为 (BlockPos, int, int, Predicate)，保持不变
        return BlockPos.findClosestMatch(
            entity.blockPosition(), 
            8,  // 搜索半径 XZ
            4,  // 搜索高度 Y
            pos -> isValidRepellent(level, pos)
        );
    }

    /**
     * 检查指定位置是否是有效的驱赶物
     */
    private static boolean isValidRepellent(ServerLevel level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        boolean isRepellent = blockState.is(BlockTags.PIGLIN_REPELLENTS);
        
        // 修改：CampfireBlock.isLitCampfire 已在 1.20.1 中移除（被 isLit 替代）
        // 且 SOUL_CAMPFIRE 的判断需使用 is() 而非 ==，并检查 isLit 状态
        // 注意：Blocks.SOUL_CAMPFIRE 仍存在，但 CampfireBlock.isLitCampfire 是静态方法，在 1.20.1 中已被删除
        // 正确方式：直接检查 blockState.is(Blocks.SOUL_CAMPFIRE) && blockState.getValue(CampfireBlock.LIT)
        if (isRepellent && blockState.is(Blocks.SOUL_CAMPFIRE)) {
            return blockState.getValue(CampfireBlock.LIT);
        }
        return isRepellent;
    }
}
