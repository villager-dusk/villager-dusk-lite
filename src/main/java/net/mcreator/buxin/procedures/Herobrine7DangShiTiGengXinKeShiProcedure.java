package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.init.BuxinModBlocks;
import net.mcreator.buxin.init.BuxinModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Herobrine7DangShiTiGengXinKeShiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
			for (Entity entityiterator : _entfound) {
				if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entityiterator) {
					if (Math.random() < 0.07) {
						if (Math.random() > 0.2) {
							if(Math.random() > 0.5) {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:metal2")), SoundSource.NEUTRAL, 1, 1);
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:metal2")), SoundSource.NEUTRAL, 1, 1, false);
									}
								}
							} else {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.NEUTRAL, 1, 1);
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.NEUTRAL, 1, 1, false);
									}
								}
							}
							if (entity instanceof LivingEntity _entity) {
								entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(cap -> {
									if (cap instanceof LivingEntityPatch) {
										((LivingEntityPatch) cap).playAnimationSynchronized(Animations.FIST_AUTO1, 0F);
									}
								});
								ItemStack _setstack = new ItemStack(BuxinModItems.OBS_5.get());
								_setstack.setCount(1);
								_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
								if (_entity instanceof Player _player)
									_player.getInventory().setChanged();
							}
							BuxinMod.queueServerWork(35, () -> {
								if (entity instanceof LivingEntity _entity) {
									ItemStack _setstack = new ItemStack(BuxinModItems.OBS.get());
									_setstack.setCount(1);
									_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
									if (_entity instanceof Player _player)
										_player.getInventory().setChanged();
								}
							});
						}
						if (Math.random() < 0.5) {
							if(Math.random() > 0.5) {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.NEUTRAL, 1, 1);
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.repair")), SoundSource.NEUTRAL, 1, 1, false);
									}
								}
							} else {
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:block_place")), SoundSource.NEUTRAL, 1, 1);
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:block_place")), SoundSource.NEUTRAL, 1, 1, false);
									}
								}
							}
							if (entity instanceof LivingEntity _entity) {
								entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(cap -> {
									if (cap instanceof LivingEntityPatch) {
										((LivingEntityPatch) cap).playAnimationSynchronized(Animations.FIST_AUTO1, 0F);
									}
								});
								ItemStack _setstack = new ItemStack(BuxinModItems.OBS_5.get());
								_setstack.setCount(1);
								_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
								if (_entity instanceof Player _player)
									_player.getInventory().setChanged();
							}
							BuxinMod.queueServerWork(35, () -> {
								if (entity instanceof LivingEntity _entity) {
									ItemStack _setstack = new ItemStack(BuxinModItems.OBS.get());
									_setstack.setCount(1);
									_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
									if (_entity instanceof Player _player)
										_player.getInventory().setChanged();
								}
							});
						}
						world.setBlock(new BlockPos((int)(entityiterator.getX() + Mth.nextDouble(RandomSource.create(), -1, 2)), (int)entityiterator.getY(), (int)(entityiterator.getZ() + 1)), BuxinModBlocks.DARK_BLOCK_ZHU.get().defaultBlockState(), 3);
						BuxinMod.queueServerWork(5, () -> {
							if(Math.random() > 0.86) {
								world.setBlock(new BlockPos((int)(entityiterator.getX() + Mth.nextDouble(RandomSource.create(), -2, 4)), (int)entityiterator.getY(), (int)(entityiterator.getZ() + Mth.nextDouble(RandomSource.create(), -2, 3))),
										BuxinModBlocks.DARK_BLOCK_ZHU.get().defaultBlockState(), 3);
							}
						});
					}
				}
			}
		}
	}
}