package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.EnchanterEntity;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModGameRules;
import net.mcreator.buxin.init.BuxinModMobEffects;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class PlayermobsAiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		AtomicReference<Double> dx = new AtomicReference<>(entity.getX());
		AtomicReference<Double> dy = new AtomicReference<>(entity.getY());
		AtomicReference<Double> dz = new AtomicReference<>(entity.getZ());
		RandomSource random = RandomSource.create();
		double fuck = random.nextInt(3);
		double attack = 0;
		if(entity instanceof LivingEntity e){
			if(e.getMainHandItem().getItem() == Items.GOLDEN_APPLE){
				AnimationPlayer.playAnimation(entity, Animations.BIPED_EAT);
			}
		}
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) >= 70) {
		} else {
			if (Math.random() > 0.88) {
				if (!entity.getPersistentData().getBoolean("eatGoldenApple") && entity instanceof LivingEntity) {
					ItemStack oldItem = ((LivingEntity) entity).getMainHandItem();
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack = new ItemStack(Items.GOLDEN_APPLE);
						_setstack.setCount(1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					entity.getPersistentData().putBoolean("eatGoldenApple", true);
					AnimationPlayer.playAnimation(entity, Animations.BIPED_EAT);
					if (world instanceof Level _level) {
						Method_114514.play_sound(entity.level(), entity.getX(), entity.getY(), entity.getZ(), "entity.generic.eat");
					}
					if (!entity.level().isClientSide() && entity.level().getServer() != null) {
						Method_114514.entity_use_command(entity, "/execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 10");
					}
					for (int i = 0; i < 6; i++) {
						BuxinMod.queueServerWork(4 * i, () -> {
							Method_114514.play_sound(entity.level(), entity.getX(), entity.getY(), entity.getZ(), "entity.generic.eat");
							if (!entity.level().isClientSide() && entity.level().getServer() != null) {
								Method_114514.entity_use_command(entity, "/execute at @s run particle minecraft:item golden_apple ^ ^1.5 ^0.5 0 0 0 0.01 10");
							}
						});
					}

					BuxinMod.queueServerWork(28, () -> {
						if (!entity.level().isClientSide()) {
							entity.level().playSound((Player) null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.player.burp"))), SoundSource.NEUTRAL, 1.5F, 1.0F);
						} else {
							entity.level().playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.player.burp"))), SoundSource.NEUTRAL, 1.5F, 1.0F, false);
						}
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 80, 2, false, true));
					});

					BuxinMod.queueServerWork(31, () -> {
						if (!entity.isRemoved()) {
							entity.setItemSlot(EquipmentSlot.MAINHAND, oldItem);
						}
						BuxinMod.queueServerWork(20, () -> {
							entity.getPersistentData().putBoolean("eatGoldenApple", false);
						});
					});
				}
			}
		}
		Level level = world instanceof Level ? (Level) world : null;
		if (level != null && level.getLevelData().getGameRules().getBoolean(GameRules.RULE_ANNOUNCE_ADVANCEMENTS) == true) {
			entity.getPersistentData().putDouble("timer", (entity.getPersistentData().getDouble("timer") + 1));
			if (entity.getPersistentData().getDouble("timer") == 80) {
				entity.getPersistentData().putDouble("timer", 0);
				attack = Mth.nextInt(random, 1, 4);
				if (level.getLevelData().getGameRules().getBoolean(BuxinModGameRules.DOMOBSUSEENDERPEARL) == true) {
					if(Math.random() > 0.25) {
						{
							final Vec3 _center = new Vec3(x, y, z);
							List<Entity> _entfound = level.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(12 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
									.collect(Collectors.toList());
							for (Entity entityiterator : _entfound) {
								if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator) {
									if (attack == 3) {
										if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) >= 80) {
											entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entityiterator.getX()), (entityiterator.getY() + 1), (entityiterator.getZ())));
											BuxinMod.queueServerWork(1, () -> {
												if (entity instanceof LivingEntity _entity)
													_entity.swing(InteractionHand.OFF_HAND, true);
												{
													entity.playSound(SoundEvents.ENDER_PEARL_THROW);
													Entity _shootFrom = entity;
													Level projectileLevel = _shootFrom.level();
													if (!projectileLevel.isClientSide()) {
														Projectile _entityToSpawn = new Object() {
															public Projectile getProjectile(Level level, Entity shooter) {
																Projectile entityToSpawn = new ThrownEnderpearl(EntityType.ENDER_PEARL, level);
																entityToSpawn.setOwner(shooter);
																return entityToSpawn;
															}
														}.getProjectile(projectileLevel, entity);
														_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
														_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, (float) 1.1, 0);
														projectileLevel.addFreshEntity(_entityToSpawn);
													}
												}
												((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY)).enchant(Enchantments.SHARPNESS, 2);
											});
										} else {
											entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entityiterator.getLookAngle().x), (entityiterator.getLookAngle().y), (entityiterator.getLookAngle().z)));
											BuxinMod.queueServerWork(40, () -> {
												if (entity instanceof LivingEntity _entity)
													_entity.swing(InteractionHand.OFF_HAND, true);
												{
													Entity _shootFrom = entity;
													Level projectileLevel = _shootFrom.level();
													if (!projectileLevel.isClientSide()) {
														Projectile _entityToSpawn = new Object() {
															public Projectile getProjectile(Level level, Entity shooter) {
																Projectile entityToSpawn = new ThrownEnderpearl(EntityType.ENDER_PEARL, level);
																entityToSpawn.setOwner(shooter);
																return entityToSpawn;
															}
														}.getProjectile(projectileLevel, entity);
														_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
														_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, (float) 1.2, 0);
														projectileLevel.addFreshEntity(_entityToSpawn);
													}
												}
												((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY)).enchant(Enchantments.SHARPNESS, 2);
											});
										}
									}
								}
							}
						}
					} else if(Math.random() > 0.6){
						{
							Grave2ShiTiChuShiShengChengShiProcedure event = new Grave2ShiTiChuShiShengChengShiProcedure();
							entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(EntityPatch -> {
								if (EntityPatch instanceof LivingEntityPatch<?>) {
									final Vec3 _center = new Vec3(x, y, z);
									List<Entity> _entfound = level.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(6 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
											.toList();
									for (Entity entityiterator : _entfound) {
										if (((!(entity.getPersistentData().getBoolean("isfuck"))) && !(entityiterator instanceof ItemEntity) && (!(entityiterator instanceof Projectile)) && ((!(entity == entityiterator))))) {
											event.CanPutToDeath = true;
										}
										if (entityiterator instanceof LivingEntity && entityiterator != entity && event.CanPutToDeath && (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator && entity.isAlive() && ((LivingEntity) entityiterator).getHealth() != ((LivingEntity) entityiterator).getMaxHealth() * 0.7 || entityiterator instanceof LivingEntity && entityiterator != entity && event.CanPutToDeath && (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator && entity.isAlive()) {
											if(SystemMethod.isWindows()) {
												VFXTool.addVFXParticle(new Vec3(entityiterator.getX(),entityiterator.getY() + 2.2,entityiterator.getZ()),"buxin","danger",entity.level());
												Method_114514.play_sound(entityiterator,"buxin:danger");
											}
											BuxinMod.queueServerWork(10,() -> {
												entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ())));
												entityiterator.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(entity.getX(), entity.getY() + 1.0, entity.getZ()));
												Vec3 viewVec = ((LivingEntity) entityiterator).getViewVector(1.0F);
												entity.teleportTo(((LivingEntity) entityiterator).getX() + viewVec.x() * 1.51, ((LivingEntity) entityiterator).getY(), ((LivingEntity) entityiterator).getZ() + viewVec.z() * 1.51);
												entity.getPersistentData().putBoolean("isfuck", true);
												event.CanPutToDeath = false;
												BuxinMod.queueServerWork(135, () -> {
													entity.getPersistentData().putBoolean("isfuck", false);
												});
												BuxinMod.queueServerWork(32, () -> {
													if (level instanceof ServerLevel _level)
														_level.sendParticles((SimpleParticleType) (EpicFightParticles.BLADE_RUSH_SKILL.get()), entityiterator.getX(), entityiterator.getY() + 1.3, entityiterator.getZ(), 1, 0.25, 0.3, 0.25, 0.2);
													entityiterator.hurt(entity.damageSources().mobAttack((LivingEntity) entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.2) + 4);
													Method_114514.play_sound(entity.level(), entity.getX(), entity.getY(), entity.getZ(), "epicfight:entity.hit.eviscerate");
													if (level instanceof ServerLevel _level)
														_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), entityiterator.getX(), (entityiterator.getY() + 1.3), entityiterator.getZ(), (55), 0.07, 0.05, 0.07, 0.25);
												});
												BuxinMod.queueServerWork(14, () -> {
													entityiterator.hurt(entity.damageSources().mobAttack((LivingEntity) entity), (float) (((LivingEntity) entityiterator).getMaxHealth() * 0.05));
													if (!entity.level().isClientSide() && entity.level().getServer() != null) {
														Method_114514.play_sound(entity.level(), entity.getX(), entity.getY(), entity.getZ(), "epicfight:entity.hit.blade");
														if (level instanceof ServerLevel _level)
															_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), dx.get(), (dy.get() + 1.3), dz.get(), (10), 0.03, 0.02, 0.03, 0.15);
													}
												});
												if (entity.level().getServer() != null && entityiterator.level().getServer() != null) {
													if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
														_entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 225, 1, false, false));
													((LivingEntity) entityiterator).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 45, 2, false, false));
													((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
													((LivingEntity) entityiterator).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 45, 50, false, false));
													((LivingEntity) entity).addEffect(new MobEffectInstance((MobEffect) MobEffects.MOVEMENT_SLOWDOWN, 130, 0, false, false));
													Method_114514.play_sound(entityiterator, "buxin:bitch");
													Method_114514.entity_use_command(entityiterator, "/indestructible @s play \"buxin:biped/run/other_been_attack\" 0 1");
												}
												var LivingEntityPatch = AnimationPlayer.getlivingEntityPatch(entity);
												if (LivingEntityPatch != null) {
													WeaponCategory Maincategory = LivingEntityPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory();
													WeaponCategory Offcategory = LivingEntityPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory();
													BuxinMod.queueServerWork(2, () -> {
														if (Maincategory == Offcategory) {
															AnimationPlayer.playAnimation(entity, BuxinAnimations.DUAL_SWORD_DIE);
														} else {
															AnimationPlayer.playAnimation(entity, BuxinAnimations.SWORD_DIE);
														}
													});
												}
											});
										}
									}
								}
							});
						}
					}
				}
			}
		}
	}
}