
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.my_method.VFXTool;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

public class ZhanshenzhirenemoDangShiTiGengXinKeShiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		Random random1 = new Random();
		if(Math.random() < 0.4)
			if(world instanceof Level world2)
				VFXTool.addVFXParticle(new Vec3(x,y + random1.nextDouble(1.5),z), BuxinMod.MODID,"electronic_2_small",world2);
		/*if (Math.random() < 0.025) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:dian_hu")), SoundSource.NEUTRAL, (float) 0.5, (float) 0.85);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:dian_hu")), SoundSource.NEUTRAL, (float) 0.75, (float) 0.85, false);
				}
			}
		}

		 */
	}
}
