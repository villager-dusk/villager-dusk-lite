
package net.mcreator.buxin.procedures;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.mcreator.buxin.BuxinMod;
import org.lwjgl.glfw.GLFW;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Comparator;

public class Blood303WeaponDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null || world.isClientSide())
			return;

		if (true) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world instanceof Level level ? 
					level.getEntities((Entity) null, new AABB(_center, _center).inflate(15.0 / 2.0), e -> true)
						.stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
						.collect(Collectors.toList()) : 
					world.getEntities((Entity) null, new AABB(_center, _center).inflate(15.0 / 2.0), e -> true)
						.stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
						.collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if ((entity == entityiterator) == false) {
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 80, 3));
						BuxinMod.queueServerWork(80, () -> {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, new BlockPos((int) entityiterator.getX(), (int) entityiterator.getY(), (int) entityiterator.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:blood")), SoundSource.NEUTRAL,
											(float) 1.5, 1);
								} else {
									_level.playLocalSound((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:blood")), SoundSource.NEUTRAL, (float) 1.5, 1,
											false);
								}
							}
							Level particleLevel = entityiterator.level();
							if (particleLevel != null && !particleLevel.isClientSide()) {
								particleLevel.addParticle((SimpleParticleType) (BuxinModParticleTypes.BLOOD_2.get()), (entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), 0, 1, 0);
							}
							if ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) > 100) {
								entityiterator.hurt(new DamageSources(world.registryAccess()).generic(), (float) ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5));
							} else {
								entityiterator.hurt(new DamageSources(world.registryAccess()).generic(), 100);
							}
							if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, (int) ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5), 3));
						});
					}
				}
			}
		}
	}
}
