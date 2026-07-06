
package net.mcreator.buxin.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class RainbowCatSwordDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || world.isClientSide())
            return;
        if (true) {
            if (world instanceof Level _level) {
                ResourceLocation soundLoc = new ResourceLocation("entity.cat.ambient");
                if (ForgeRegistries.SOUND_EVENTS.containsKey(soundLoc)) {
                    if (!_level.isClientSide()) {
                        _level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(soundLoc), SoundSource.NEUTRAL, 1, 1);
                    } else {
                        _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(soundLoc), SoundSource.NEUTRAL, 1, 1, false);
                    }
                } else {
                    ResourceLocation fallbackLoc = new ResourceLocation("entity.cat.meow");
                    if (ForgeRegistries.SOUND_EVENTS.containsKey(fallbackLoc)) {
                        if (!_level.isClientSide()) {
                            _level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(fallbackLoc), SoundSource.NEUTRAL, 1, 1);
                        } else {
                            _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(fallbackLoc), SoundSource.NEUTRAL, 1, 1, false);
                        }
                    }
                }
            }
        }
    }
}
