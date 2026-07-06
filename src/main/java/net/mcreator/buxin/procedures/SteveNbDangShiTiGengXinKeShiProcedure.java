package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SteveNbDangShiTiGengXinKeShiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		double attack = 0;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) >= 6) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate((world.getLevelData().getGameRules().getInt(GameRules.RULE_MAX_COMMAND_CHAIN_LENGTH)) / 2d), e -> true).stream()
						.sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if ((entityiterator == entity) == false) {
						if ((entityiterator == (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null)) == true) {
							if (entity instanceof LivingEntity _entity)
								_entity.swing(InteractionHand.MAIN_HAND, true);
							entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getX()), ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getY()),
									((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getZ())));
							if (entity instanceof Mob _entity)
								_entity.getNavigation().moveTo(((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getX() + Mth.nextInt(RandomSource.create(), (int) (-2.5), (int) 1.5)),
										((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getY()),
										((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getZ() + Mth.nextInt(RandomSource.create(), (int) (-2.5), (int) 1.5)), 1.3);
							entityiterator.hurt(entity.level().damageSources().generic(), 8);
							if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)) != 0) {
								entityiterator.setDeltaMovement(new Vec3((entity.getLookAngle().x), (entity.getLookAngle().y), (entity.getLookAngle().z)));
							}
						}
					}
				}
			}
		} else {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(6 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
						.collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if ((entityiterator == entity) == false) {
						if ((entityiterator == (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null)) == true) {
							if (entity instanceof Mob _entity)
								_entity.getNavigation().moveTo((entityiterator.getLookAngle().x), (entityiterator.getLookAngle().y), (entityiterator.getLookAngle().z), 1.2);
						}
					}
				}
			}
			if (entity.getPersistentData().getBoolean("eatGoldenApple") == false) {
				entity.getPersistentData().putBoolean("eatGoldenApple", true);
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack = new ItemStack(Items.GOLDEN_APPLE);
					_setstack.setCount(1);
					_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				if (entity instanceof LivingEntity _entity)
					_entity.swing(InteractionHand.MAIN_HAND, true);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, new BlockPos((int) x, (int) (y + 1), (int) z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.eat")), SoundSource.NEUTRAL, (float) 1.2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.2));
					} else {
						_level.playLocalSound(x, (y + 1), z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.eat")), SoundSource.NEUTRAL, (float) 1.2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.2), false);
					}
				}
				BuxinMod.queueServerWork(5, () -> {
					if (entity instanceof LivingEntity _entity)
						_entity.swing(InteractionHand.MAIN_HAND, true);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, new BlockPos((int)x, (int)(y + 1), (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.eat")), SoundSource.NEUTRAL, (float) 1.2,
									(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.2));
						} else {
							_level.playLocalSound(x, (y + 1), z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.eat")), SoundSource.NEUTRAL, (float) 1.2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.2), false);
						}
					}
					BuxinMod.queueServerWork(5, () -> {
						if (entity instanceof LivingEntity _entity)
							_entity.swing(InteractionHand.MAIN_HAND, true);
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, new BlockPos((int)x, (int)(y + 1), (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.eat")), SoundSource.NEUTRAL, (float) 1.2,
										(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.2));
							} else {
								_level.playLocalSound(x, (y + 1), z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.eat")), SoundSource.NEUTRAL, (float) 1.2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.2), false);
							}
						}
						BuxinMod.queueServerWork(5, () -> {
							if (entity instanceof LivingEntity _entity)
								_entity.swing(InteractionHand.MAIN_HAND, true);
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, new BlockPos((int)x, (int)(y + 1), (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.eat")), SoundSource.NEUTRAL, (float) 1.2,
											(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.2));
								} else {
									_level.playLocalSound(x, (y + 1), z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.eat")), SoundSource.NEUTRAL, (float) 1.2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.2),
											false);
								}
							}
							BuxinMod.queueServerWork(5, () -> {
								if (entity instanceof LivingEntity _entity)
									_entity.swing(InteractionHand.MAIN_HAND, true);
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, new BlockPos((int)x, (int)(y + 1), (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.eat")), SoundSource.NEUTRAL, (float) 1.2,
												(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.2));
									} else {
										_level.playLocalSound(x, (y + 1), z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.eat")), SoundSource.NEUTRAL, (float) 1.2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.2),
												false);
									}
								}
								BuxinMod.queueServerWork(5, () -> {
									if (entity instanceof LivingEntity _entity)
										_entity.swing(InteractionHand.MAIN_HAND, true);
									if (world instanceof Level _level) {
										if (!_level.isClientSide()) {
											_level.playSound(null, new BlockPos((int)x, (int)(y + 1), (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.eat")), SoundSource.NEUTRAL, (float) 1.2,
													(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.2));
										} else {
											_level.playLocalSound(x, (y + 1), z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.eat")), SoundSource.NEUTRAL, (float) 1.2,
													(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.2), false);
										}
									}
									BuxinMod.queueServerWork(5, () -> {
										if (entity instanceof LivingEntity _entity)
											_entity.swing(InteractionHand.MAIN_HAND, true);
										if (world instanceof Level _level) {
											if (!_level.isClientSide()) {
												_level.playSound(null, new BlockPos((int)x, (int)(y + 1), (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.eat")), SoundSource.NEUTRAL, (float) 1.2,
														(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.2));
											} else {
												_level.playLocalSound(x, (y + 1), z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.eat")), SoundSource.NEUTRAL, (float) 1.2,
														(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.2), false);
											}
										}
										if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
											_entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 1, false, true));
										if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
											_entity.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 100, 2, false, true));
										if (entity instanceof LivingEntity _entity)
											_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + 1.5));
										BuxinMod.queueServerWork(3, () -> {
											if (entity instanceof LivingEntity _entity) {
												ItemStack _setstack = new ItemStack(Items.DIAMOND_SWORD);
												_setstack.setCount(1);
												_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
												if (_entity instanceof Player _player)
													_player.getInventory().setChanged();
											}
											((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)).enchant(Enchantments.SHARPNESS, 5);
											((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)).enchant(Enchantments.KNOCKBACK, 2);
											if (world instanceof Level _level) {
												if (!_level.isClientSide()) {
													_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_diamond")), SoundSource.NEUTRAL, 1, 1);
												} else {
													_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_diamond")), SoundSource.NEUTRAL, 1, 1, false);
												}
											}
											BuxinMod.queueServerWork(15, () -> {
												entity.getPersistentData().putBoolean("eatGoldenApple", false);
											});
										});
									});
								});
							});
						});
					});
				});
			}
		}
		if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_ANNOUNCE_ADVANCEMENTS) == true) {
			entity.getPersistentData().putDouble("timer", (entity.getPersistentData().getDouble("timer") + 1));
			if (entity.getPersistentData().getDouble("timer") == 80) {
				entity.getPersistentData().putDouble("timer", 0);
				attack = Mth.nextInt(RandomSource.create(), 1, 4);
			}
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate((world.getLevelData().getGameRules().getInt(GameRules.RULE_MAX_COMMAND_CHAIN_LENGTH)) / 2d), e -> true).stream()
						.sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator) {
						if (attack == 2) {
							if (entity instanceof LivingEntity _entity) {
								ItemStack _setstack = new ItemStack(Items.LAVA_BUCKET);
								_setstack.setCount(1);
								_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
								if (_entity instanceof Player _player)
									_player.getInventory().setChanged();
							}
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.fill_lava")), SoundSource.NEUTRAL, 1, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.fill_lava")), SoundSource.NEUTRAL, 1, 1, false);
								}
							}
							BuxinMod.queueServerWork(10, () -> {
								if (entity instanceof Mob _entity)
									_entity.getNavigation().moveTo((entityiterator.getLookAngle().x), (entityiterator.getLookAngle().y), (entityiterator.getLookAngle().z), 1.2);
								if (entity instanceof LivingEntity _entity)
									_entity.swing(InteractionHand.MAIN_HAND, true);
								if (entity instanceof LivingEntity _entity) {
									ItemStack _setstack = new ItemStack(Items.BUCKET);
									_setstack.setCount(1);
									_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
									if (_entity instanceof Player _player)
										_player.getInventory().setChanged();
								}
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.empty_lava")), SoundSource.NEUTRAL, 1, 1);
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.empty_lava")), SoundSource.NEUTRAL, 1, 1, false);
									}
								}
								entity.getPersistentData().putDouble("entityX", ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getX()));
								entity.getPersistentData().putDouble("entityY", ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getY()));
								entity.getPersistentData().putDouble("entityZ", ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getZ()));
								world.setBlock(new BlockPos((int)(entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getX(), (int)(entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getY(),
										(int)(entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).getZ()), Blocks.LAVA.defaultBlockState(), 3);
								BuxinMod.queueServerWork(20, () -> {
									if (entity instanceof LivingEntity _entity)
										_entity.swing(InteractionHand.MAIN_HAND, true);
									if (entity instanceof LivingEntity _entity) {
										ItemStack _setstack = new ItemStack(Items.LAVA_BUCKET);
										_setstack.setCount(1);
										_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
										if (_entity instanceof Player _player)
											_player.getInventory().setChanged();
									}
									if (world instanceof Level _level) {
										if (!_level.isClientSide()) {
											_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.fill_lava")), SoundSource.NEUTRAL, 1, 1);
										} else {
											_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.fill_lava")), SoundSource.NEUTRAL, 1, 1, false);
										}
									}
									if (((world.getBlockState(new BlockPos((int)entity.getPersistentData().getDouble("entityX"), (int)entity.getPersistentData().getDouble("entityY"), (int)entity.getPersistentData().getDouble("entityZ"))))
											.getBlock() == Blocks.AIR) == false) {
										world.setBlock(new BlockPos((int)entity.getPersistentData().getDouble("entityX"), (int)entity.getPersistentData().getDouble("entityY"), (int)entity.getPersistentData().getDouble("entityZ")), Blocks.AIR.defaultBlockState(),
												3);
									}
									BuxinMod.queueServerWork(10, () -> {
										if (entity instanceof LivingEntity _entity) {
											ItemStack _setstack = new ItemStack(Items.DIAMOND_SWORD);
											_setstack.setCount(1);
											_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
											if (_entity instanceof Player _player)
												_player.getInventory().setChanged();
										}
										((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)).enchant(Enchantments.SHARPNESS, 5);
										((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)).enchant(Enchantments.KNOCKBACK, 2);
										if (world instanceof Level _level) {
											if (!_level.isClientSide()) {
												_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_diamond")), SoundSource.NEUTRAL, 1, 1);
											} else {
												_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_diamond")), SoundSource.NEUTRAL, 1, 1, false);
											}
										}
									});
								});
							});
						}
					}
				}
			}
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(12 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
						.collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator) {
						if (attack == 1) {
							if (entity instanceof LivingEntity _entity) {
								ItemStack _setstack = new ItemStack(Items.FIRE_CHARGE);
								_setstack.setCount(1);
								_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
								if (_entity instanceof Player _player)
									_player.getInventory().setChanged();
							}
							if (entity instanceof LivingEntity _entity)
								_entity.swing(InteractionHand.OFF_HAND, true);
							entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entityiterator.getLookAngle().x), (entityiterator.getLookAngle().y), (entityiterator.getLookAngle().z)));
							entity.getPersistentData().putDouble("Fireball", (Mth.nextInt(RandomSource.create(), 0, 1)));
							if (entity.getPersistentData().getDouble("entityY") == 1) {
								BuxinMod.queueServerWork(10, () -> {
									entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ())));
									{
										Entity _shootFrom = entity;
										Level projectileLevel = _shootFrom.level();
										if (!projectileLevel.isClientSide()) {
											Projectile _entityToSpawn = new Object() {
												public Projectile getFireball(Level level, Entity shooter, double ax, double ay, double az) {
													AbstractHurtingProjectile entityToSpawn = new LargeFireball(EntityType.FIREBALL, level);
													entityToSpawn.setOwner(shooter);
													entityToSpawn.xPower = ax;
													entityToSpawn.yPower = ay;
													entityToSpawn.zPower = az;
													return entityToSpawn;
												}
											}.getFireball(projectileLevel, entity, 2, 2, 2);
											_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
											_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1, 0);
											projectileLevel.addFreshEntity(_entityToSpawn);
										}
									}
									BuxinMod.queueServerWork(20, () -> {
										if (entity instanceof LivingEntity _entity) {
											ItemStack _setstack = new ItemStack(Blocks.AIR);
											_setstack.setCount(1);
											_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
											if (_entity instanceof Player _player)
												_player.getInventory().setChanged();
										}
									});
								});
							} else {
								BuxinMod.queueServerWork(10, () -> {
									entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(x, (y - 2), z));
									{
										Entity _shootFrom = entity;
										Level projectileLevel = _shootFrom.level();
										if (!projectileLevel.isClientSide()) {
											Projectile _entityToSpawn = new Object() {
												public Projectile getFireball(Level level, Entity shooter, double ax, double ay, double az) {
													AbstractHurtingProjectile entityToSpawn = new LargeFireball(EntityType.FIREBALL, level);
													entityToSpawn.setOwner(shooter);
													entityToSpawn.xPower = ax;
													entityToSpawn.yPower = ay;
													entityToSpawn.zPower = az;
													return entityToSpawn;
												}
											}.getFireball(projectileLevel, entity, 2, 2, 2);
											_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
											_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1, 0);
											projectileLevel.addFreshEntity(_entityToSpawn);
										}
									}
									BuxinMod.queueServerWork(20, () -> {
										if (entity instanceof LivingEntity _entity) {
											ItemStack _setstack = new ItemStack(Blocks.AIR);
											_setstack.setCount(1);
											_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
											if (_entity instanceof Player _player)
												_player.getInventory().setChanged();
										}
									});
								});
							}
						}
					}
				}
			}
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(12 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
						.collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator) {
						if (attack == 3) {
							if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) >= 10) {
								if (entity instanceof LivingEntity _entity) {
									ItemStack _setstack = new ItemStack(Items.ENDER_PEARL);
									_setstack.setCount(1);
									_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
									if (_entity instanceof Player _player)
										_player.getInventory().setChanged();
								}
								entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entityiterator.getX()), (entityiterator.getY() + 1), (entityiterator.getZ())));
								BuxinMod.queueServerWork(3, () -> {
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
											_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1, 0);
											projectileLevel.addFreshEntity(_entityToSpawn);
										}
									}
									if (entity instanceof LivingEntity _entity) {
										ItemStack _setstack = new ItemStack(Blocks.AIR);
										_setstack.setCount(1);
										_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
										if (_entity instanceof Player _player)
											_player.getInventory().setChanged();
									}
								});
							} else {
								if (entity instanceof LivingEntity _entity) {
									ItemStack _setstack = new ItemStack(Items.ENDER_PEARL);
									_setstack.setCount(1);
									_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
									if (_entity instanceof Player _player)
										_player.getInventory().setChanged();
								}
								entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entityiterator.getLookAngle().x), (entityiterator.getLookAngle().y), (entityiterator.getLookAngle().z)));
								BuxinMod.queueServerWork(3, () -> {
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
											_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1, 0);
											projectileLevel.addFreshEntity(_entityToSpawn);
										}
									}
									if (entity instanceof LivingEntity _entity) {
										ItemStack _setstack = new ItemStack(Blocks.AIR);
										_setstack.setCount(1);
										_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
										if (_entity instanceof Player _player)
											_player.getInventory().setChanged();
									}
								});
							}
						}
					}
				}
			}
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(32 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
						.collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator) {
						if (attack == 4) {
							if (entity instanceof Mob _entity)
								_entity.getNavigation().moveTo((entityiterator.getLookAngle().x), (entityiterator.getLookAngle().y), (entityiterator.getLookAngle().z), 1.2);
							if (entity instanceof LivingEntity _entity) {
								ItemStack _setstack = new ItemStack(Items.BOW);
								_setstack.setCount(1);
								_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
								if (_entity instanceof Player _player)
									_player.getInventory().setChanged();
							}
							BuxinMod.queueServerWork(20, () -> {
								entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entityiterator.getX()), (entityiterator.getY() + 1), (entityiterator.getZ())));
								BuxinMod.queueServerWork(20, () -> {
									entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entityiterator.getX()), (entityiterator.getY() + 1), (entityiterator.getZ())));
									if (world instanceof Level _level) {
										if (!_level.isClientSide()) {
											_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.shoot")), SoundSource.NEUTRAL, 1, 1);
										} else {
											_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.shoot")), SoundSource.NEUTRAL, 1, 1, false);
										}
									}
									{
										Entity _shootFrom = entity;
										Level projectileLevel = _shootFrom.level();
										if (!projectileLevel.isClientSide()) {
											Projectile _entityToSpawn = new Object() {
												public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
													AbstractArrow entityToSpawn = new Arrow(EntityType.ARROW, level);
													entityToSpawn.setOwner(shooter);
													entityToSpawn.setBaseDamage(damage);
													entityToSpawn.setKnockback(knockback);
													entityToSpawn.setCritArrow(true);
													return entityToSpawn;
												}
											}.getArrow(projectileLevel, entity, 4, 1);
											_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
											_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 3, 0);
											projectileLevel.addFreshEntity(_entityToSpawn);
										}
									}
									if (entity instanceof LivingEntity _entity)
										_entity.swing(InteractionHand.OFF_HAND, true);
									BuxinMod.queueServerWork(20, () -> {
										entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entityiterator.getX()), (entityiterator.getY() + 1), (entityiterator.getZ())));
										if (world instanceof Level _level) {
											if (!_level.isClientSide()) {
												_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.shoot")), SoundSource.NEUTRAL, 1, 1);
											} else {
												_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.shoot")), SoundSource.NEUTRAL, 1, 1, false);
											}
										}
										{
											Entity _shootFrom = entity;
											Level projectileLevel = _shootFrom.level();
											if (!projectileLevel.isClientSide()) {
												Projectile _entityToSpawn = new Object() {
													public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
														AbstractArrow entityToSpawn = new Arrow(EntityType.ARROW, level);
														entityToSpawn.setOwner(shooter);
														entityToSpawn.setBaseDamage(damage);
														entityToSpawn.setKnockback(knockback);
														entityToSpawn.setCritArrow(true);
														return entityToSpawn;
													}
												}.getArrow(projectileLevel, entity, 4, 1);
												_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
												_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 3, 0);
												projectileLevel.addFreshEntity(_entityToSpawn);
											}
										}
										if (entity instanceof LivingEntity _entity)
											_entity.swing(InteractionHand.OFF_HAND, true);
										BuxinMod.queueServerWork(20, () -> {
											entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((entityiterator.getX()), (entityiterator.getY() + 1), (entityiterator.getZ())));
											if (world instanceof Level _level) {
												if (!_level.isClientSide()) {
													_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.shoot")), SoundSource.NEUTRAL, 1, 1);
												} else {
													_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.shoot")), SoundSource.NEUTRAL, 1, 1, false);
												}
											}
											{
												Entity _shootFrom = entity;
												Level projectileLevel = _shootFrom.level();
												if (!projectileLevel.isClientSide()) {
													Projectile _entityToSpawn = new Object() {
														public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
															AbstractArrow entityToSpawn = new Arrow(EntityType.ARROW, level);
															entityToSpawn.setOwner(shooter);
															entityToSpawn.setBaseDamage(damage);
															entityToSpawn.setKnockback(knockback);
															entityToSpawn.setCritArrow(true);
															return entityToSpawn;
														}
													}.getArrow(projectileLevel, entity, 4, 1);
													_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
													_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 3, 0);
													projectileLevel.addFreshEntity(_entityToSpawn);
												}
											}
											if (entity instanceof LivingEntity _entity)
												_entity.swing(InteractionHand.OFF_HAND, true);
											BuxinMod.queueServerWork(10, () -> {
												if (entity instanceof LivingEntity _entity) {
													ItemStack _setstack = new ItemStack(Blocks.AIR);
													_setstack.setCount(1);
													_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
													if (_entity instanceof Player _player)
														_player.getInventory().setChanged();
												}
											});
										});
									});
								});
							});
						}
					}
				}
			}
		}
	}
}