
		package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.config.common.VFXParticleConfig;

import net.mcreator.buxin.entity.LanemobentiEntity;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.my_method.SystemMethod;
import net.mcreator.buxin.my_method.VFXTool;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class L蓝恶魔Procedure {
	private static void createExpandingExplosions2(Entity sourceEntity, int iterations, double baseRadius) {
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
						entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int)explosionX, (int)explosionY, (int)explosionZ)));
						entityToSpawn.setCustomName(Component.literal("b"));
						entityToSpawn.setVisualOnly(true);
						_level.addFreshEntity(entityToSpawn);
					}
				}
			});
		}
	}
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		Random random1 = new Random();
		if (Math.random() < 0.5 && !(entity instanceof LanemobentiEntity)) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_22.get()), x, y, z, 1, 1, 1, 1, 1);
		}
		if (Math.random() < 0.3) {
			if(entity instanceof LanemobentiEntity) {
				VFXTool.addVFXParticle(new Vec3(entity.getX(),entity.getY() + random1.nextDouble(1.5),entity.getZ()),BuxinMod.MODID,"electronic_2_small",entity.level());
			}
		}
		if(entity instanceof Mob mob && mob.getTarget() != null && Math.random() > 0.917813 && mob.distanceTo(mob.getTarget()) > 6.5 && entity instanceof LanemobentiEntity){
			entity.lookAt(EntityAnchorArgument.Anchor.EYES, mob.getTarget().getLookAngle());
			if(mob.getTarget() instanceof Player player && !player.isCreative()) {
				BuxinMod.queueServerWork((int) (Math.random() * 2), () -> {
					Method_114514.entity_use_command(entity, "/indestructible @s play \"epicfight:biped/combat/mob_throw\" 0 1");
					ThrownTrident $$2 = new ThrownTrident(entity.level(), (LivingEntity) entity, new ItemStack(Items.TRIDENT));
					double $$3 = Method_114514.getTarget(entity).getX() - entity.getX();
					double $$4 = Method_114514.getTarget(entity).getY(0.3333333333333333) - $$2.getY();
					double $$5 = Method_114514.getTarget(entity).getZ() - entity.getZ();
					double $$6 = Math.sqrt($$3 * $$3 + $$5 * $$5);
					$$2.shoot($$3, $$4 + $$6 * 0.20000000298023224, $$5, 1.6F, (float) (14 - entity.level().getDifficulty().getId() * 4));
					entity.playSound(SoundEvents.DROWNED_SHOOT, 1.0F, 1.0F / (mob.getRandom().nextFloat() * 0.4F + 0.8F));
					entity.level().addFreshEntity($$2);
					BuxinMod.queueServerWork(90,() -> {
						Method_114514.entity_use_command(entity,"/kill @e[type=minecraft:trident]");
					});
				});
			}
		}
		try {
			if (Math.random() > 0.971813 && ((Mob) entity).getTarget() != null && entity.distanceTo(((Mob) entity).getTarget()) > 7.5 && entity instanceof LanemobentiEntity) {
				Entity target = ((Mob) entity).getTarget();
				if (((Mob) entity).getTarget() != null && entity != null) {
					if(VFXParticleConfig.VFXParticleConfig.get()) {
						target.hurt(target.damageSources().generic(), 5);
						if (Math.random() > 0.957) {
							if(entity instanceof LanemobentiEntity)
								Method_114514.entity_use_command(target, "/vfx buxin lightning");
						}
					}
					if (Math.random() > 0.8)
						Method_114514.entity_use_command(target, "/summon minecraft:lightning_bolt");
				}
			}
		} catch (Exception e){
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < 40) {
			if (entity.getPersistentData().getBoolean("ABAB") == false) {
				if(entity instanceof LanemobentiEntity) {

				} else {

				}

				if(entity instanceof LanemobentiEntity lanemobenti) {
					if (!entity.level().isClientSide()) {
						if(lanemobenti.getTarget() != null) {
							entity.hurt(lanemobenti.getTarget().damageSources().mobAttack(lanemobenti.getTarget()), Float.MAX_VALUE);
							VFXTool.addVFXParticle(new Vec3(lanemobenti.getTarget().getX(),lanemobenti.getTarget().getY() + 2.2,lanemobenti.getTarget().getZ()),"buxin","danger",entity.level());
							Method_114514.play_sound(lanemobenti.getTarget(),"buxin:danger");
						} else {
							entity.discard();
						}
					}
				} else {
					if(entity instanceof LivingEntity livingEntity) {
						livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 12, 255, false, false));
						livingEntity.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 5, 255, false, false));
						AnimationPlayer.playAnimation(entity, BuxinAnimations.TRIDENT_HOLIDAY);
					}
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:blue_demon_trident_holiday")), SoundSource.NEUTRAL, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:blue_demon_trident_holiday")), SoundSource.NEUTRAL, 2, 1, false);
					}
				}
				Entity _ent = entity;
				if (!_ent.level().isClientSide() && _ent.getServer() != null) {
					_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 6,
							_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "/impactful @s shake 400 5 5");
					BuxinMod.queueServerWork(10,() -> {
						_ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 6,
								_ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "/impactful @s shake 200 5 5");
					});
				}
				if (world instanceof ServerLevel _level)
					if(entity instanceof LanemobentiEntity)
						_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), x, y, z, 50, 5, 0, 5, 1);
					else
						_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_22.get()), x, y, z, 50, 5, 0, 5, 1);
				BuxinMod.queueServerWork(7, () -> {
					if (world instanceof ServerLevel _level)
						if(entity instanceof LanemobentiEntity)
							_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), x, y, z, 50, 5, 0, 5, 1);
						else
							_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_22.get()), x, y, z, 50, 5, 0, 5, 1);
					BuxinMod.queueServerWork(7, () -> {
						if (world instanceof ServerLevel _level)
							if(entity instanceof LanemobentiEntity)
								_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), x, y, z, 50, 5, 0, 5, 1);
							else
								_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_22.get()), x, y, z, 50, 5, 0, 5, 1);
						BuxinMod.queueServerWork(7, () -> {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:zjdl")), SoundSource.NEUTRAL, 2, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:zjdl")), SoundSource.NEUTRAL, 2, 1, false);
								}
							}
						});
					});
				});
				BuxinMod.queueServerWork(35, () -> {
					if (world instanceof ServerLevel _level)
						if(entity instanceof LanemobentiEntity)
							_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), x, y, z, 50, 5, 0.1, 5, 0.1);
						else
							_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_22.get()), x, y, z, 50, 5, 0.1, 5, 0.1);
					BuxinMod.queueServerWork(2, () -> {
						if (world instanceof ServerLevel _level)
							if(entity instanceof LanemobentiEntity)
								_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), x, y, z, 50, 5, 0.1, 5, 0.1);
							else
								_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_22.get()), x, y, z, 50, 5, 0.1, 5, 0.1);
						BuxinMod.queueServerWork(2, () -> {
							if (world instanceof ServerLevel _level)
								if(entity instanceof LanemobentiEntity)
									_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), x, y, z, 50, 5, 0.1, 5, 0.1);
								else
									_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_22.get()), x, y, z, 50, 5, 0.1, 5, 0.1);
							if (world instanceof ServerLevel _level)
								_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
										"impactful @p shake 25 3 5");
						});
					});
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								"impactful @p shake 25 3 5");
					BuxinMod.queueServerWork(45, () -> {
						BuxinMod.queueServerWork(15, () -> {
							for(int i=0;i<2;i++) {
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo((x + 8), (y + 2), z, world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, (x + 8), y, z, 4, Level.ExplosionInteraction.BLOCK);
								createExpandingExplosions2(entity,5,5);
								BuxinMod.queueServerWork(27,() -> {
									if(entity instanceof LanemobentiEntity)
										VFXTool.addVFXParticle(entity.position(),BuxinMod.MODID,"lightning",entity.level());
									else
										VFXTool.addVFXParticle(entity.position(),BuxinMod.MODID,"hb_lightning",entity.level());
									BuxinMod.queueServerWork(17,() -> {
										if(entity instanceof LanemobentiEntity)
											VFXTool.addVFXParticle(entity.position(),BuxinMod.MODID,"lightning",entity.level());
										else
											VFXTool.addVFXParticle(entity.position(),BuxinMod.MODID,"hb_lightning",entity.level());
									});
								});
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo(x, (y + 2), (z + 8), world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, x, y, (z + 8), 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo((x - 8), (y + 2), z, world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, (x - 8), y, z, 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo(x, (y + 2), (z - 8), world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, x, y, (z - 8), 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo((x + 11), (y + 2), z, world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, (x + 11), y, z, 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo(x, (y + 2), (z + 11), world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, x, y, (z + 11), 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo((x - 11), (y + 2), z, world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, (x - 11), y, z, 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo(x, (y + 2), (z - 11), world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, x, y, (z - 11), 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo((x + 8), (y + 2), (z + 8), world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, (x + 8), y, (z + 8), 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo((x + 8), (y + 2), (z + 8), world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, (x + 8), y, (z + 8), 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo((x - 8), (y + 2), (z - 8), world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, (x - 8), y, (z - 8), 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo((x - 8), (y + 2), (z - 8), world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, (x - 8), y, (z - 8), 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo((x + 11), (y + 2), (z + 11), world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, (x + 11), y, (z + 11), 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo((x + 11), (y + 2), (z + 11), world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, (x + 11), y, (z + 11), 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo((x - 11), (y + 2), (z - 11), world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, (x - 11), y, (z - 11), 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo((x - 11), (y + 2), (z - 11), world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, (x - 11), y, (z - 11), 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo((x - 8), (y + 2), (z + 8), world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, (x - 8), y, (z + 8), 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo((x + 8), (y + 2), (z - 8), world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, (x + 8), y, (z - 8), 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo((x + 8), (y + 2), (z - 8), world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, (x + 8), y, (z - 8), 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo((x + 8), (y + 2), (z - 8), world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, (x + 8), y, (z - 8), 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo((x + 11), (y + 2), (z - 11), world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, (x + 11), y, (z - 11), 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo((x - 11), (y + 2), (z + 11), world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, (x - 11), y, (z + 11), 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo((x + 11), (y + 2), (z - 11), world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
								if (world instanceof Level _level && !_level.isClientSide())
									_level.explode(null, (x + 11), y, (z - 11), 4, Level.ExplosionInteraction.BLOCK);
								if (world instanceof ServerLevel _level) {
									ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
									trident.moveTo((x - 11), (y + 2), (z + 11), world.getRandom().nextFloat() * 360F, 0);
									_level.addFreshEntity(trident);
								}
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x - 11), y, (z + 11), 4, Level.ExplosionInteraction.BLOCK);
							BuxinMod.queueServerWork(20, () -> {
								BuxinMod.queueServerWork(20, () -> {
									{
										final Vec3 _center = new Vec3(x, y, z);
										List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
												.collect(Collectors.toList());
										for (Entity entityiterator : _entfound) {
											if(entity instanceof LanemobentiEntity)
												Method_114514.entity_use_command(entityiterator,"/vfx buxin dragon_beam_hit");
											if (world instanceof ServerLevel _level) {
												if(entity instanceof LanemobentiEntity) {
													LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
													entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)entityiterator.getZ())));
													entityToSpawn.setVisualOnly(false);
													entityToSpawn.setCustomName(Component.literal("b"));
													_level.addFreshEntity(entityToSpawn);
												} else {
													LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
													entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)entityiterator.getZ())));
													entityToSpawn.setVisualOnly(false);
													entityToSpawn.setCustomName(Component.literal("p"));
													_level.addFreshEntity(entityToSpawn);
												}
											}
											createExpandingExplosions2(entity,5,5);
											if (world instanceof ServerLevel _level) {
												if(entity instanceof LanemobentiEntity) {
													LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
													entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)entityiterator.getZ())));
													entityToSpawn.setVisualOnly(false);
													entityToSpawn.setCustomName(Component.literal("b"));
													_level.addFreshEntity(entityToSpawn);
												} else {
													LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
													entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)entityiterator.getZ())));
													entityToSpawn.setVisualOnly(false);
													entityToSpawn.setCustomName(Component.literal("p"));
													_level.addFreshEntity(entityToSpawn);
												}
											}
											if (world instanceof ServerLevel _level)
												if(entity instanceof LanemobentiEntity)
													_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), (entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), 5, (entityiterator.getX() + 1),
															(entityiterator.getY() + 2), (entityiterator.getZ() + 1), 1);
												else
													_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_22.get()), (entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), 5, (entityiterator.getX() + 1),
															(entityiterator.getY() + 2), (entityiterator.getZ() + 1), 1);
											BuxinMod.queueServerWork(5, () -> {
												if (world instanceof ServerLevel _level) {
													LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
													if (!(entity instanceof LanemobentiEntity)) {
														entityToSpawn.setCustomName(Component.literal("p"));
													}
													entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)entityiterator.getZ())));
													entityToSpawn.setVisualOnly(false);
													_level.addFreshEntity(entityToSpawn);
												}
											});
										}
									}
								});
								{
									final Vec3 _center = new Vec3(x, y, z);
									List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(20 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
											.collect(Collectors.toList());
									for (Entity entityiterator : _entfound) {
										if (world instanceof ServerLevel _level) {
											LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
											if (!(entity instanceof LanemobentiEntity)) {
												entityToSpawn.setCustomName(Component.literal("p"));
											} else {
												entityToSpawn.setCustomName(Component.literal("b"));
											}
											entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)entityiterator.getZ())));
											entityToSpawn.setVisualOnly(false);
											_level.addFreshEntity(entityToSpawn);
										}
										if (world instanceof ServerLevel _level)
											if(entity instanceof LanemobentiEntity)
												_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), (entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), 5, (entityiterator.getX() + 1),
														(entityiterator.getY() + 2), (entityiterator.getZ() + 1), 1);
											else
												_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_22.get()), (entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), 5, (entityiterator.getX() + 1),
														(entityiterator.getY() + 2), (entityiterator.getZ() + 1), 1);
										BuxinMod.queueServerWork(100, () -> {
											if (entityiterator instanceof LivingEntity _livEnt) {
												entityiterator.hurt(_livEnt.damageSources().generic(), _livEnt.getMaxHealth());
												BuxinMod.queueServerWork(2, () -> {
													entityiterator.hurt(_livEnt.damageSources().generic(), _livEnt.getMaxHealth());
													BuxinMod.queueServerWork(2, () -> {
														entityiterator.hurt(_livEnt.damageSources().generic(), _livEnt.getMaxHealth());
													});
												});
											}
										});

									}
								}
							});
						});
						BuxinMod.queueServerWork(30, () -> {
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo((x + 14), (y + 2), z, world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x + 14), y, z, 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo(x, (y + 2), (z + 14), world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, x, y, (z + 14), 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo((x - 14), (y + 2), z, world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x - 14), y, z, 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo(x, (y + 2), (z - 14), world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, x, y, (z - 14), 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo((x + 17), (y + 2), z, world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x + 17), y, z, 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo(x, (y + 2), (z + 17), world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, x, y, (z + 17), 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo((x - 17), (y + 2), z, world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x - 17), y, z, 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo(x, (y + 2), (z - 17), world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, x, y, (z - 17), 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo((x + 14), (y + 2), (z + 14), world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x + 14), y, (z + 14), 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo((x + 14), (y + 2), (z + 14), world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x + 14), y, (z + 14), 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo((x - 14), (y + 2), (z - 14), world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x - 14), y, (z - 14), 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo((x - 14), (y + 2), (z - 14), world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x - 14), y, (z - 14), 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo((x + 17), (y + 2), (z + 17), world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x + 17), y, (z + 17), 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo((x + 17), (y + 2), (z + 17), world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x + 17), y, (z + 17), 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo((x - 17), (y + 2), (z - 17), world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x - 17), y, (z - 17), 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo((x - 17), (y + 2), (z - 17), world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x - 17), y, (z - 17), 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo((x - 14), (y + 2), (z + 14), world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x - 14), y, (z + 14), 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo((x + 14), (y + 2), (z - 14), world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x + 14), y, (z - 14), 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo((x + 14), (y + 2), (z - 14), world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x + 14), y, (z - 14), 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo((x + 14), (y + 2), (z - 14), world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x + 14), y, (z - 14), 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo((x + 17), (y + 2), (z - 17), world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x + 17), y, (z - 17), 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo((x - 11), (y + 2), (z + 11), world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x - 17), y, (z + 17), 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo((x + 17), (y + 2), (z - 17), world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x + 17), y, (z - 17), 4, Level.ExplosionInteraction.BLOCK);
							if (world instanceof ServerLevel _level) {
								ThrownTrident trident = new ThrownTrident(_level, (LivingEntity)entity, new ItemStack(Items.TRIDENT));
								trident.moveTo((x - 17), (y + 2), (z + 17), world.getRandom().nextFloat() * 360F, 0);
								_level.addFreshEntity(trident);
							}
							if (world instanceof Level _level && !_level.isClientSide())
								_level.explode(null, (x - 17), y, (z + 17), 4, Level.ExplosionInteraction.BLOCK);
						});
						if (world instanceof Level _level && !_level.isClientSide())
							_level.explode(null, x, y, z, 16, Level.ExplosionInteraction.BLOCK);
						if (world instanceof Level _level && !_level.isClientSide())
							_level.explode(null, (x + 2), y, z, 8, Level.ExplosionInteraction.BLOCK);
						if (world instanceof Level _level && !_level.isClientSide())
							_level.explode(null, (x - 2), y, z, 8, Level.ExplosionInteraction.BLOCK);
						if (world instanceof Level _level && !_level.isClientSide())
							_level.explode(null, x, y, (z + 2), 8, Level.ExplosionInteraction.BLOCK);
						if (world instanceof Level _level && !_level.isClientSide())
							_level.explode(null, x, y, (z - 2), 8, Level.ExplosionInteraction.BLOCK);
						if (world instanceof Level _level && !_level.isClientSide())
							_level.explode(null, (x + 2), y, (z + 2), 8, Level.ExplosionInteraction.BLOCK);
						if (world instanceof Level _level && !_level.isClientSide())
							_level.explode(null, (x - 2), y, (z - 2), 8, Level.ExplosionInteraction.BLOCK);
						if (world instanceof Level _level && !_level.isClientSide())
							_level.explode(null, (x + 2), y, (z - 2), 8, Level.ExplosionInteraction.BLOCK);
						if (world instanceof Level _level && !_level.isClientSide())
							_level.explode(null, (x - 2), y, (z + 2), 8, Level.ExplosionInteraction.BLOCK);
						if (world instanceof Level _level && !_level.isClientSide())
							_level.explode(null, (x + 6), y, z, 8, Level.ExplosionInteraction.BLOCK);
						if (world instanceof Level _level && !_level.isClientSide())
							_level.explode(null, (x - 6), y, z, 8, Level.ExplosionInteraction.BLOCK);
						if (world instanceof Level _level && !_level.isClientSide())
							_level.explode(null, x, y, (z + 6), 8, Level.ExplosionInteraction.BLOCK);
						if (world instanceof Level _level && !_level.isClientSide())
							_level.explode(null, x, y, (z - 6), 8, Level.ExplosionInteraction.BLOCK);
						if (world instanceof Level _level && !_level.isClientSide())
							_level.explode(null, (x + 6), y, (z + 6), 8, Level.ExplosionInteraction.BLOCK);
						if (world instanceof Level _level && !_level.isClientSide())
							_level.explode(null, (x - 6), y, (z - 6), 8, Level.ExplosionInteraction.BLOCK);
						if (world instanceof Level _level && !_level.isClientSide())
							_level.explode(null, (x + 6), y, (z - 6), 8, Level.ExplosionInteraction.BLOCK);
						if (world instanceof Level _level && !_level.isClientSide())
							_level.explode(null, (x - 6), y, (z + 6), 8, Level.ExplosionInteraction.BLOCK);
					});
				});
				entity.getPersistentData().putBoolean("ABAB", true);
				if (world instanceof ServerLevel _level)
					_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
							"kill @e[type = item]");
			}
		}
	}
}
