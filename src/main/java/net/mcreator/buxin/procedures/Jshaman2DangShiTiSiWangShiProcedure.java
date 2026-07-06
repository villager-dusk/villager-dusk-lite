
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.entity.ShifangEntity;
import net.mcreator.buxin.init.BuxinModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class Jshaman2DangShiTiSiWangShiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        if (world instanceof ServerLevel _level) {
            Entity entityToSpawn = new ShifangEntity(BuxinModEntities.SHIFANG.get(), _level);
            entityToSpawn.moveTo(x, y, z, 0, 0);
            entityToSpawn.setYBodyRot(0);
            entityToSpawn.setYHeadRot(0);
            entityToSpawn.setDeltaMovement(0, 0, 0);
            if (entityToSpawn instanceof Mob _mobToSpawn)
                _mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
            world.addFreshEntity(entityToSpawn);
        }
        if (world instanceof Level _level) {
            if (!_level.isClientSide()) {
                _level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:shi_fang")), SoundSource.NEUTRAL, 1, 1);
            } else {
                _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:shi_fang")), SoundSource.NEUTRAL, 1, 1, false);
            }
        }
    }
}
