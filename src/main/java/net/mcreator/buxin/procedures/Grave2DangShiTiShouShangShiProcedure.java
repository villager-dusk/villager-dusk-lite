
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.TntEntity;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.init.BuxinModMobEffects;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Grave2DangShiTiShouShangShiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.hasEffect(BuxinModMobEffects.DAMAGE.get()) : false) == false) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
						.collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if ((entity == entityiterator) == false) {
						if ((entityiterator == (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null)) == true) {
							if (Math.random() > 0.9) {

								if (entity instanceof LivingEntity _entity) {
									ItemStack _setstack = new ItemStack(BuxinModItems.BAO_ZHA_BI_SHOU.get());
									_setstack.setCount(1);
									_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
									if (_entity instanceof Player _player)
										_player.getInventory().setChanged();
								}
								if (world instanceof Level _level) {
									if (!_level.isClientSide()) {
										_level.playSound(null, new BlockPos((int)x,(int) y,(int) z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:bomb")), SoundSource.NEUTRAL, (float) 1.5, 1);
									} else {
										_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:bomb")), SoundSource.NEUTRAL, (float) 1.5, 1, false);
									}
								}
								if (world instanceof ServerLevel projectileLevel) {
									Projectile _entityToSpawn = new Object() {
										public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
											AbstractArrow entityToSpawn = new TntEntity(BuxinModEntities.TNT.get(), level);
											entityToSpawn.setOwner(shooter);
											entityToSpawn.setBaseDamage(damage);
											entityToSpawn.setKnockback(knockback);
											entityToSpawn.setSilent(true);
											return entityToSpawn;
										}
									}.getArrow(projectileLevel, entity, 10, 1);
									_entityToSpawn.setPos(x, y, z);
									_entityToSpawn.shoot(entityiterator.getLookAngle().x, entityiterator.getLookAngle().y, entityiterator.getLookAngle().z, 1.4f, 1.7f);
									projectileLevel.addFreshEntity(_entityToSpawn);
								}
								BuxinMod.queueServerWork(80, () -> {
									if (entity instanceof LivingEntity _entity) {
										ItemStack _setstack = new ItemStack(BuxinModItems.ZUAN_SHI_WAN_DAO.get());
										_setstack.setCount(1);
										_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
										if (_entity instanceof Player _player)
											_player.getInventory().setChanged();
									}
								});
							}
							if (Math.random() < 0.45) {
								if (entity instanceof Mob _entity) {
									_entity.getNavigation().moveTo(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ(), 1.0);
								}
								entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(entityiterator.getX(), entityiterator.getY(), entityiterator.getZ()));
							}
						}
					}
				}
			}
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < 300) {
				if (Math.random() > 0.7) {
					BuxinMod.queueServerWork(10, () -> {
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack = new ItemStack(BuxinModItems.ZUAN_SHI_DAO_PIAN.get());
							_setstack.setCount(1);
							_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
							if (_entity instanceof Player _player)
								_player.getInventory().setChanged();
						}
					});
				}
			}
		}
	}
}
