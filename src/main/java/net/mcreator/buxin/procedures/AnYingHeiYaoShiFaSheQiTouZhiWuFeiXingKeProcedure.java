
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class AnYingHeiYaoShiFaSheQiTouZhiWuFeiXingKeProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        world.destroyBlock(new BlockPos((int) x, (int) (y + 2), (int) z), false);
        BuxinMod.queueServerWork(3, () -> {
            world.setBlock(new BlockPos((int) x, (int) (y + 2), (int) z), Blocks.OBSIDIAN.defaultBlockState(), 3);
        });
        BuxinMod.queueServerWork(60, () -> {
            if (world instanceof Level _level) {
                if (!_level.isClientSide()) {
                    _level.playSound(null, new BlockPos((int) x, (int) y, (int) z), BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("buxin:drop")), SoundSource.NEUTRAL, (float) 0.05, 1);
                } else {
                    _level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation("buxin:drop")), SoundSource.NEUTRAL, (float) 0.05, 1, false);
                }
            }
            world.destroyBlock(new BlockPos((int) x, (int) (y + 2), (int) z), false);
        });
    }
}
