package net.mcreator.buxin.procedures;

import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DarkLegendarySwordDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == BuxinModItems.DARK_CHESTPLATE.get()) {
			if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == BuxinModItems.DARK_HELMET.get()) {
				if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == BuxinModItems.DARK_LEG_LEGGINGS.get()) {
					if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == BuxinModItems.DARK_BOOTS.get()) {
						if (true) {
							{
								final Vec3 _center = new Vec3(x, y, z);
								List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(20 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
										.collect(Collectors.toList());
								for (Entity entityiterator : _entfound) {
									if ((entity == entityiterator) == false) {
										world.addParticle((SimpleParticleType) (BuxinModParticleTypes.DARK.get()), (entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), 0, 0, 0);
										if (world instanceof Level _level) {
											if (!_level.isClientSide()) {
												_level.playSound(null, new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)entityiterator.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:dark")), SoundSource.NEUTRAL,
														1, 1);
											} else {
												_level.playLocalSound((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:dark")), SoundSource.NEUTRAL, 1, 1,
														false);
											}
										}
										if (world instanceof Level _level && !_level.isClientSide())
											_level.explode(null, (entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), 4, Level.ExplosionInteraction.NONE);
										if (world instanceof ServerLevel _level) {
											LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
											entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)entityiterator.getZ())));
											entityToSpawn.setVisualOnly(false);
											_level.addFreshEntity(entityToSpawn);
										}
										entityiterator.hurt(entityiterator.damageSources().generic(), 35);
									}
								}
							}
						}
					}
				}
			}
		}
	}
}