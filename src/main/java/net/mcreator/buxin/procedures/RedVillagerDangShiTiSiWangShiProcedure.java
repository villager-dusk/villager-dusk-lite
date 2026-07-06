
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.*;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

import java.util.Objects;
import java.util.Random;

public class RedVillagerDangShiTiSiWangShiProcedure {
	private static void dropLoot(Level world, double x, double y, double z) {
		if (world.isClientSide()) return;

		Item[] drops = new Item[]{Items.DIAMOND, Items.EMERALD, Items.ENDER_PEARL,Items.ENDER_EYE, Items.ENDER_PEARL,Items.BOOK,Items.ARROW,Items.ARROW,Items.ARROW};

		for (Item item : drops) {
			ItemEntity entity = new ItemEntity(world, x, y, z, new ItemStack(item));
			entity.setPickUpDelay(10);
			world.addFreshEntity(entity);
		}
	}
	private static void dropLoot2(Level world, double x, double y, double z) {
		if (world.isClientSide()) return;

		Item[] drops = new Item[]{
				Items.ENDER_PEARL, Items.ENCHANTED_GOLDEN_APPLE, Items.IRON_INGOT,Items.DIRT,Items.DIRT,Items.DIRT,Items.REDSTONE,Items.REDSTONE,Items.FIREWORK_ROCKET

		};

		for (Item item : drops) {
			ItemEntity entity = new ItemEntity(world, x, y, z, new ItemStack(item));
			entity.setPickUpDelay(10);
			world.addFreshEntity(entity);
		}
	}
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, DamageSource damageSource) {
		Random random = new Random();
		double value = random.nextInt(4);
		String worldName = "";
		//try {
		if(world.getServer() != null)
			worldName = Objects.requireNonNull(world.getServer()).getWorldData().getLevelName();
		//} catch (Exception e){

		//}
		if(!("Arena".equals(worldName)) && !(entity.getPersistentData().getBoolean("su"))) {
            if (Math.random() > 0.2) {
				dropLoot(entity.level(),entity.getX(),entity.getY(),entity.getZ());
                Method_114514.entity_use_command(entity, "/summon firework_rocket ~ ~10 ~ {LifeTime:10,FireworksItem:{id:\"firework_rocket\",Count:1,tag:{Fireworks:{Explosions:[{Type:3,Colors:[0],Flicker:1}]}},display:{Name:{\"text\":\"Black Creeper Firework\"}}}}");

                BuxinMod.queueServerWork(150, () -> {
                    if (value == 0) {
                        if (world instanceof ServerLevel _level) {
                            Entity entityToSpawn = new GreenVillagerCavalryEntity(BuxinModEntities.GREEN_VILLAGER_CAVALRY.get(), _level);
                           

                            if (entityToSpawn instanceof Mob _mobToSpawn)
                                _mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                            world.addFreshEntity(entityToSpawn);
                        }
                    }
                    if (value == 1) {
                        if (world instanceof ServerLevel _level) {
                            Entity entityToSpawn = new PurpleVillagerCavalryEntity(BuxinModEntities.PURPLE_VILLAGER_CAVALRY.get(), _level);
                           entityToSpawn.moveTo(entity.getX(), entity.getY(), entity.getZ(), _level.getRandom().nextFloat() * 360F, 0);
                            entityToSpawn.getPersistentData().putBoolean("su",true);

                            if (entityToSpawn instanceof Mob _mobToSpawn)
                                _mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                            world.addFreshEntity(entityToSpawn);
                        }
                    }
                    if (value == 2) {
                        if (world instanceof ServerLevel _level) {
                            Entity entityToSpawn = new RedVillagerEntity(BuxinModEntities.RED_VILLAGER.get(), _level);
                           entityToSpawn.moveTo(entity.getX(), entity.getY(), entity.getZ(), _level.getRandom().nextFloat() * 360F, 0);
                            entityToSpawn.getPersistentData().putBoolean("su",true);

                            if (entityToSpawn instanceof Mob _mobToSpawn)
                                _mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                            world.addFreshEntity(entityToSpawn);
                        }
                    }
                    if (value == 3) {
                        if (world instanceof ServerLevel _level) {
                            Entity entityToSpawn = new VillagerScoutEntity(BuxinModEntities.VILLAGER_SCOUT.get(), _level);

                           entityToSpawn.moveTo(entity.getX(), entity.getY(), entity.getZ(), _level.getRandom().nextFloat() * 360F, 0);
                            entityToSpawn.getPersistentData().putBoolean("su",true);
                            if (entityToSpawn instanceof Mob _mobToSpawn)
                                _mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                            world.addFreshEntity(entityToSpawn);
                        }
                    }
                });
            }
            if (world instanceof Level _level && !_level.isClientSide()) {
                ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.DIAMOND));
                entityToSpawn.setPickUpDelay(10);
                entityToSpawn.setUnlimitedLifetime();
                _level.addFreshEntity(entityToSpawn);
            }
            dropLoot2(entity.level(),entity.getX(), entity.getY(), entity.getZ());
            BuxinMod.queueServerWork(5, () -> {
                if (world instanceof Level _level && !_level.isClientSide()) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.OAK_LOG));
                    entityToSpawn.setPickUpDelay(10);
                    entityToSpawn.setUnlimitedLifetime();
                    _level.addFreshEntity(entityToSpawn);
                }
                if(Math.random() > 0.8){
                    if(Math.random() > 0.5) {
                        if (world instanceof Level _level && !_level.isClientSide()) {
                            ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(BuxinModItems.YUMIJUAN.get()));
                            entityToSpawn.setPickUpDelay(10);
                            entityToSpawn.setUnlimitedLifetime();
                            _level.addFreshEntity(entityToSpawn);
                        }
                    } else {
                        if (world instanceof Level _level && !_level.isClientSide()) {
                            ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                            entityToSpawn.setPickUpDelay(10);
                            entityToSpawn.setUnlimitedLifetime();
                            _level.addFreshEntity(entityToSpawn);
                        }
                    }
                }
                if (Math.random() > 0.5) {
					dropLoot2(entity.level(),entity.getX(), entity.getY(), entity.getZ());
                    if (world instanceof Level _level && !_level.isClientSide() && Math.random() > 0.8) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.WHITE_BED));
                        entityToSpawn.setPickUpDelay(10);
                        entityToSpawn.setUnlimitedLifetime();
                        _level.addFreshEntity(entityToSpawn);
                    }
                } else {
                    if (world instanceof Level _level && !_level.isClientSide() && Math.random() > 0.92) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.RED_BED));
                        entityToSpawn.setPickUpDelay(10);
                        entityToSpawn.setUnlimitedLifetime();
                        _level.addFreshEntity(entityToSpawn);
                    }
                }
                if (world instanceof Level _level && !_level.isClientSide()) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.ENDER_PEARL));
                    entityToSpawn.setPickUpDelay(10);
                    entityToSpawn.setUnlimitedLifetime();
                    _level.addFreshEntity(entityToSpawn);
                }
            });
            BuxinMod.queueServerWork(1, () -> {
                if (world instanceof Level _level && !_level.isClientSide()) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.EMERALD));
                    entityToSpawn.setPickUpDelay(10);
                    entityToSpawn.setUnlimitedLifetime();
                    _level.addFreshEntity(entityToSpawn);
                }
                if (world instanceof Level _level && !_level.isClientSide()) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.GOLDEN_APPLE));
                    entityToSpawn.setPickUpDelay(10);
                    entityToSpawn.setUnlimitedLifetime();
                    _level.addFreshEntity(entityToSpawn);
                }
                if (world instanceof Level _level && !_level.isClientSide()) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.BREAD));
                    entityToSpawn.setPickUpDelay(10);
                    entityToSpawn.setUnlimitedLifetime();
                    _level.addFreshEntity(entityToSpawn);
                }
                if (world instanceof Level _level && !_level.isClientSide() && Math.random() > 0.2) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                    entityToSpawn.setPickUpDelay(10);
                    entityToSpawn.setUnlimitedLifetime();
                    _level.addFreshEntity(entityToSpawn);
                }
                if (world instanceof Level _level && !_level.isClientSide()) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.EMERALD));
                    entityToSpawn.setPickUpDelay(10);
                    entityToSpawn.setUnlimitedLifetime();
                    _level.addFreshEntity(entityToSpawn);
                }
                if (world instanceof Level _level && !_level.isClientSide()) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.GOLDEN_APPLE));
                    entityToSpawn.setPickUpDelay(10);
                    entityToSpawn.setUnlimitedLifetime();
                    _level.addFreshEntity(entityToSpawn);
                }
            });
			/*
			BuxinMod.queueServerWork(11, () -> {
				if (world instanceof Level _level && !_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.IRON_SWORD));
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
			});
			BuxinMod.queueServerWork(21, () -> {
				if (world instanceof Level _level && !_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.BREAD));
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				if (world instanceof Level _level && !_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.EMERALD));
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				if (world instanceof Level _level && !_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.ENDER_PEARL));
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				if (world instanceof Level _level && !_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
			});
			BuxinMod.queueServerWork(27, () -> {
				if (world instanceof Level _level && !_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.GOLD_INGOT));
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				if(Math.random() > 0.5) {
					if (world instanceof Level _level && !_level.isClientSide()) {
						ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.GOLDEN_HELMET));
						entityToSpawn.setPickUpDelay(10);
						entityToSpawn.setUnlimitedLifetime();
						_level.addFreshEntity(entityToSpawn);
					}
				} else {
					if (world instanceof Level _level && !_level.isClientSide()) {
						ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.GOLDEN_CHESTPLATE));
						entityToSpawn.setPickUpDelay(10);
						entityToSpawn.setUnlimitedLifetime();
						_level.addFreshEntity(entityToSpawn);
					}
				}
				if (world instanceof Level _level && !_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.DIAMOND));
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				if (world instanceof Level _level && !_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.GOLDEN_APPLE));
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				if (world instanceof Level _level && !_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.STICK));
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
				if (world instanceof Level _level && !_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.APPLE));
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
			});

			 */
		}
	}
}
