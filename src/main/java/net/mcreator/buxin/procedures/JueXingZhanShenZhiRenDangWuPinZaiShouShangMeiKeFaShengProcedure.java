
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;

public class JueXingZhanShenZhiRenDangWuPinZaiShouShangMeiKeFaShengProcedure {
	public static void execute(LevelAccessor world, Entity entity, double x, double y, double z) {
		if (world instanceof ServerLevel _level)
			_level.sendParticles((net.minecraft.core.particles.SimpleParticleType) (BuxinModParticleTypes.HUANG_SE_LI_ZI.get()), x, y, z, 1, 1, 1, 1, 1);
	}
}
