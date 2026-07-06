
package net.mcreator.buxin.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.server.level.ServerLevel;

import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.BuxinMod;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Comparator;

public class ZszrJueXingDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null || world.isClientSide())
			return;
		if (true) {
			BuxinMod.queueServerWork(15, () -> {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(16 / 2d), e -> true)
							.stream()
							.sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
							.collect(Collectors.toList());
					for (Entity entityiterator : _entfound) {
						/*if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = new ZrDiciEntity(BuxinModEntities.ZR_DICI.get(), _level);
							entityToSpawn.moveTo(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ(), world.getRandom().nextFloat() * 360F, 0);
							if (entityToSpawn instanceof Mob _mobToSpawn) {
								_mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
							}
							_level.addFreshEntity(entityToSpawn);
						}*/
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 30, 100, false, false));
						BuxinMod.queueServerWork(10, () -> {
							entityiterator.hurt(new DamageSources(world.registryAccess()).generic(), 40);
							BuxinMod.queueServerWork(30, () -> {
								if (entity instanceof Player _player) {
									_player.getCooldowns().addCooldown(itemstack.getItem(), 90);
								}
							});
						});
					}
				}
			});
		}
	}
}
