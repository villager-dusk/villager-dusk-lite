
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class DarkBlockZhuFangZhiFangKuaiShiProcedure {
	public static void execute(Level world, double x, double y, double z) {
		Method_114514.play_sound(world,x,y,z,"buxin:metal1");
		BuxinMod.queueServerWork((int) (45 + Mth.nextDouble(RandomSource.create(), 1, 10)), () -> {
			Method_114514.play_sound(world,x,y,z,"entity.iron_golem.repair");
			world.destroyBlock(new BlockPos((int)x, (int)y, (int)z), false);
			Method_114514.level_use_command(world,new Vec3(x,y,z),"/particle minecraft:large_smoke ~ ~0.1 ~ 0 0 0 0.12 20");
		});
	}
}
