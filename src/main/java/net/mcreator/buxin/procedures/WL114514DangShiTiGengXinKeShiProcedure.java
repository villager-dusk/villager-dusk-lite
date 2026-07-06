
package net.mcreator.buxin.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;

// ✅ 无需修改：ParticleTypes.SMOKE 在 1.20.1 中仍存在且用法一致（同 1.19.2）
// ✅ 无需修改：ServerLevel 和 LevelAccessor 的继承关系与 instanceof 检查在 1.20.1+Forge 47.4.10 中保持兼容
// ✅ 无需修改：sendParticles 方法签名未变（参数类型和顺序一致：ParticleType, double, double, double, int, double, double, double, double）
// ⚠️ 注意：1.20.1 中 ParticleTypes.SMOKE 仍为 public static final，无需迁移
public class WL114514DangShiTiGengXinKeShiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.SMOKE, x, y, z, 3, 1, 1, 1, 1);
	}
}
