
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class SherenDangShiTiHuiDongWuPinShiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof ServerLevel _level) {
			_level.sendParticles((SimpleParticleType) BuxinModParticleTypes.NIUBI_22.get(), x, y, z, 4, 1, 1, 1, 1);
			_level.sendParticles((SimpleParticleType) BuxinModParticleTypes.PINK_LIGHT_PARTICLES.get(), x, y, z, 4, 1, 1, 1, 1);
			_level.sendParticles((SimpleParticleType) BuxinModParticleTypes.NIUBI_22.get(), x, y, z, 1, 1, 1, 1, 1);
		}

		if (Math.random() < 0.05) {
			if (Math.random() > 0.5) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, new BlockPos((int) x, (int) y, (int) z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:shan_dian_2")), SoundSource.NEUTRAL, (float) 0.7, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:shan_dian_2")), SoundSource.NEUTRAL, (float) 0.7, 1, false);
					}
				}
			}
			if (Math.random() < 0.5) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, new BlockPos((int) x, (int) y, (int) z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:shan_dian_1")), SoundSource.NEUTRAL, (float) 0.7, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:shan_dian_1")), SoundSource.NEUTRAL, (float) 0.7, 1, false);
					}
				}
			}
		}
	}
}
