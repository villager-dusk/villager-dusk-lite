
package net.mcreator.buxin.ai;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.my_method.SystemMethod;
import net.mcreator.buxin.my_method.VFXTool;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SmallTridentHolidayUseGoal extends Goal {
	private final Mob pearler;
	private LivingEntity targetLivingEntity;
	private int cooldown = reducedTickDelay(50);

	public SmallTridentHolidayUseGoal(Mob pearler){
		this.pearler = pearler;
	}

	public boolean canUse() {
		LivingEntity target = this.pearler.getTarget();
		if (!(target instanceof LivingEntity))
			return false;

		if (this.pearler.distanceToSqr(target) > 25d)
			return false;

		if (--this.cooldown > 0)
			return false;

		return !this.pearler.getMainHandItem().isEmpty();
	}private static void createExpandingExplosions(Entity sourceEntity, int iterations, double baseRadius) {
		if (sourceEntity.level().isClientSide()) return;
		double centerX = sourceEntity.getX();
		double centerY = sourceEntity.getY();
		double centerZ = sourceEntity.getZ();

		for (int i = 1; i <= iterations; i++) {
			final int currentIteration = i;
			final double radius = baseRadius * currentIteration;
			BuxinMod.queueServerWork((int) (iterations + Math.random() * 1.2),() -> {
				int points = 8 + currentIteration * 2;
				for (int j = 0; j < points; j++) {
					double angle = 2 * Math.PI * j / points;
					double offsetX = Math.cos(angle) * radius;
					double offsetZ = Math.sin(angle) * radius;

					double explosionX = centerX + offsetX;
					double explosionY = centerY;
					double explosionZ = centerZ + offsetZ;
					if (sourceEntity.level() instanceof ServerLevel _level) {
						LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
						entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int) explosionX, (int) explosionY, (int) explosionZ)));
						entityToSpawn.setCustomName(Component.literal("b"));
						entityToSpawn.setVisualOnly(true);
						_level.addFreshEntity(entityToSpawn);
					}
				}
			});
		}
	}
	public void start() {
		this.targetLivingEntity = this.pearler.getTarget();
		Mob entity = pearler;
		if(SystemMethod.isWindows()) {
			VFXTool.addVFXParticle(new Vec3(targetLivingEntity.getX(),targetLivingEntity.getY() + 2.2,targetLivingEntity.getZ()),"buxin","danger",entity.level());
			Method_114514.play_sound(targetLivingEntity,"buxin:danger");
		}
		BuxinMod.queueServerWork(10,() -> {
			double x = entity.getX(), y = entity.getY(), z = entity.getZ();
			LevelAccessor world = entity.level();
			if (!world.isClientSide() && world.getServer() != null)

				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, new BlockPos((int) x, (int) y, (int) z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:blue_demon_trident_holiday")), SoundSource.NEUTRAL, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:blue_demon_trident_holiday")), SoundSource.NEUTRAL, 2, 1, false);
					}
				}
			try {
				entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(EntityPatch -> {
					if (EntityPatch instanceof LivingEntityPatch<?> livingEntityPatch) {
						livingEntityPatch.playAnimationSynchronized(BuxinAnimations.TRIDENT_HOLIDAY, 0F);
					}
				});
			} catch (Exception e) {
			}
			BuxinMod.queueServerWork(50, () -> {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, new BlockPos((int) x, (int) y, (int) z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:dian_hu")), SoundSource.NEUTRAL, (float) 0.5, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:dian_hu")), SoundSource.NEUTRAL, (float) 0.5, 1, false);
					}
				}
				BuxinMod.queueServerWork(20, () -> {
					createExpandingExplosions(entity, 5, 5);
					Method_114514.entity_use_command(entity, "/vfx buxin lightning");
					for (int i = 0; i < 2; i++) {
						if (world instanceof Level _level && !_level.isClientSide())
							_level.explode(null, x, y, z, 4 + new Random().nextInt(3), Level.ExplosionInteraction.BLOCK);
					}
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(14 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
							.collect(Collectors.toList());
					for (Entity entityiterator : _entfound) {
						if (entityiterator instanceof LivingEntity) {
							entityiterator.hurt(entity.damageSources().mobAttack(entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.2 + 8f));
							if (world instanceof ServerLevel _level) {
								LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
								entityToSpawn.setCustomName(Component.literal("b"));
								entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int) entityiterator.getX(), (int) entityiterator.getY(), (int) entityiterator.getZ())));
								entityToSpawn.setVisualOnly(true);
								_level.addFreshEntity(entityToSpawn);
							}
						}
					}
				});
			});
		});
		this.cooldown = reducedTickDelay(200);
	}

	public void stop() {
		this.targetLivingEntity = null;
		this.pearler.getNavigation().stop();
	}
}
