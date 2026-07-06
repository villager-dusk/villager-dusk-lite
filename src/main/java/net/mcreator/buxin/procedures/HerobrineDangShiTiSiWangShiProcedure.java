
package net.mcreator.buxin.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class HerobrineDangShiTiSiWangShiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {

		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:that_enough")), SoundSource.NEUTRAL, 1, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:that_enough")), SoundSource.NEUTRAL, 1, 1, false);
			}
		}
	}
}
