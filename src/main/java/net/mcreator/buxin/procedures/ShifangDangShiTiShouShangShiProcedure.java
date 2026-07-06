
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class ShifangDangShiTiShouShangShiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        if (Math.random() < 0.6) {
            if (world instanceof ServerLevel _level)
                _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.HUOHUA_2.get()), x, y, z, (int) Mth.nextDouble(RandomSource.create(), 78, 170), 0.12, 0.6, 1, 0.25);
            if (world instanceof Level _level) {
                if (!_level.isClientSide()) {
                    _level.playSound(null, new BlockPos((int) x, (int) y, (int) z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:shield_under_attack")),
                            SoundSource.NEUTRAL, (float) (1 + Mth.nextDouble(RandomSource.create(), 0, 0.2)), 1);
                } else {
                    _level.playLocalSound(x, y, z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:shield_under_attack")),
                            SoundSource.NEUTRAL, (float) (1 + Mth.nextDouble(RandomSource.create(), 0, 0.2)), 1, false);
                }
            }
        }
        if (Math.random() > 0.6) {
            if (world instanceof Level _level) {
                if (!_level.isClientSide()) {
                    _level.playSound(null, new BlockPos((int) x, (int) y, (int) z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:clash15")),
                            SoundSource.NEUTRAL, (float) (1 + Mth.nextDouble(RandomSource.create(), 0, 0.2)), 1);
                } else {
                    _level.playLocalSound(x, y, z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:clash15")),
                            SoundSource.NEUTRAL, (float) (1 + Mth.nextDouble(RandomSource.create(), 0, 0.2)), 1, false);
                }
            }
        }
        if (Math.random() < 0.6) {
            if (Math.random() < 0.6) {
                if (world instanceof ServerLevel _level)
                    _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.HUOHUA.get()), x, y, z, 100, 0.1, 0.6, 1, 0.4);
                if (world instanceof ServerLevel _level)
                    _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.HUOHUA.get()), x, y, z, 100, 0.1, 0.6, 1, 0.4);
                if (world instanceof ServerLevel _level)
                    _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.HUOHUA.get()), x, y, z, 140, 0.1, 0.6, 1, 0.4);
                if (world instanceof Level _level) {
                    if (!_level.isClientSide()) {
                        _level.playSound(null, new BlockPos((int) x, (int) y, (int) z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:w")),
                                SoundSource.NEUTRAL, (float) (1 + Mth.nextDouble(RandomSource.create(), 0, 0.2)), 1);
                    } else {
                        _level.playLocalSound(x, y, z,
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:w")),
                                SoundSource.NEUTRAL, (float) (1 + Mth.nextDouble(RandomSource.create(), 0, 0.2)), 1, false);
                    }
                }
                if (Math.random() < 0.4) {
                    if (world instanceof ServerLevel _level)
                        _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.HUOHUA.get()), x, y, z, 1000, 0, 1, 0, 0.2);
                }
            }
        }
    }
}
