
package net.mcreator.buxin.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class YingchuihimShiTiChuShiShengChengShiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || world.isClientSide())
            return;
        if (world instanceof Level _level) {
            if (!_level.isClientSide()) {
                SoundEvent soundEvent = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:yc"));
                if (soundEvent != null) {
                    _level.playSound(null, new BlockPos((int)x, (int)y, (int)z), soundEvent, SoundSource.NEUTRAL, 1, 1);
                }
            } else {
                SoundEvent soundEvent = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:yc"));
                if (soundEvent != null) {
                    _level.playLocalSound(x, y, z, soundEvent, SoundSource.NEUTRAL, 1, 1, false);
                }
            }
        }

        entity.getPersistentData().putDouble("battle", 0);
        entity.getPersistentData().putBoolean("isbuxinentity", true);
    }
}
