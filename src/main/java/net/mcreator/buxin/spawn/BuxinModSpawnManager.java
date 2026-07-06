
package net.mcreator.buxin.spawn;

import net.mcreator.buxin.init.BuxinModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BuxinModSpawnManager {
    @SubscribeEvent
    public static void onEntitySpawnRestriction(SpawnPlacementRegisterEvent event) {
        event.register(
                BuxinModEntities.ZOMBIE_VILLAGER_SCOUT.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BuxinModSpawnManager::checkEnchanterSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
        /*
        event.register(
                BuxinModEntities.NIUBI_13.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BuxinModSpawnManager::checkEnchanterSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
        event.register(
                BuxinModEntities.SHIFANG.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BuxinModSpawnManager::checkEnchanterSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
        event.register(
                BuxinModEntities.ENCHANTER.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BuxinModSpawnManager::checkEnchanterSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
        event.register(
                BuxinModEntities.HEROBRINE_3.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BuxinModSpawnManager::checkEnchanterSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
        event.register(
                BuxinModEntities.HEROBRINE.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BuxinModSpawnManager::checkEnchanterSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
        event.register(
                BuxinModEntities.CHANGDAOHIMNB.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BuxinModSpawnManager::checkEnchanterSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
        event.register(
                BuxinModEntities.NULL.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BuxinModSpawnManager::checkEnchanterSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
        event.register(
                BuxinModEntities.NIUBI_6.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BuxinModSpawnManager::checkEnchanterSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
        event.register(
                BuxinModEntities.PURPLE_OBSIDIAN_HEROBRINE.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BuxinModSpawnManager::checkEnchanterSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );

         */
    }
    private static boolean checkEnchanterSpawnRules(EntityType<? extends Mob> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (!PathfinderMob.checkMobSpawnRules(entityType, level, spawnType, pos, random)) {
            return false;
        }

        if (!isDaytime(level)) {
            return false;
        }

        if (pos.getY() < 60) {
            return false;
        }

        if(isDarkEnoughToSpawn(level,pos,random)){
            return false;
        }

        if (level.getBrightness(LightLayer.BLOCK, pos) < 8) {
            return false;
        }
        
        if (!level.getBlockState(pos).isAir()) {
            return false;
        }

        return true;
    }

    private static boolean isDaytime(LevelAccessor level) {
        return true;
    }

    public static boolean isDarkEnoughToSpawn(ServerLevelAccessor p_219010_, BlockPos p_219011_, RandomSource p_219012_) {
        if (p_219010_.getBrightness(LightLayer.SKY, p_219011_) > p_219012_.nextInt(32)) {
            return false;
        } else {
            DimensionType dimensiontype = p_219010_.dimensionType();
            int i = dimensiontype.monsterSpawnBlockLightLimit();
            if (i < 15 && p_219010_.getBrightness(LightLayer.BLOCK, p_219011_) > i) {
                return false;
            } else {
                int j = p_219010_.getLevel().isThundering() ? p_219010_.getMaxLocalRawBrightness(p_219011_, 10) : p_219010_.getMaxLocalRawBrightness(p_219011_);
                return j <= dimensiontype.monsterSpawnLightTest().sample(p_219012_);
            }
        }
    }
}
