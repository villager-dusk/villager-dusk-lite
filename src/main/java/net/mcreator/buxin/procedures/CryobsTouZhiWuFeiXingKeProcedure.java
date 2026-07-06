
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class CryobsTouZhiWuFeiXingKeProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        world.destroyBlock(new BlockPos((int) x, (int) (y + 2), (int) z), false);
        BuxinMod.queueServerWork(3, () -> {
            world.setBlock(new BlockPos((int) x, (int) (y + 2), (int) z), Blocks.CRYING_OBSIDIAN.defaultBlockState(), 3);
        });
        BuxinMod.queueServerWork(60, () -> {
            if (world instanceof Level _level) {
                if (!_level.isClientSide()) {
                    var soundEvent = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:block_place"));
                    if (soundEvent != null) {
                        _level.playSound(null, new BlockPos((int)x, (int)y, (int)z), soundEvent, SoundSource.NEUTRAL, (float) 0.05, 1);
                    }
                } else {
                    var soundEvent = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:block_place"));
                    if (soundEvent != null) {
                        _level.playLocalSound(x, y, z, soundEvent, SoundSource.NEUTRAL, (float) 0.05, 1, false);
                    }
                }
            }
            world.destroyBlock(new BlockPos((int)x, (int)(y + 2), (int)z), false);
        });
    }
}
