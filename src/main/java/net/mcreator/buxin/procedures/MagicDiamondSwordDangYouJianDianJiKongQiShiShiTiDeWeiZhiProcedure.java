
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.config.common.VFXParticleConfig;
import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MagicDiamondSwordDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null || world.isClientSide())
			return;
		Random random = new Random();
		int v = random.nextInt(4);
		if (true) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(20 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
						.collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if ((entityiterator == entity) == false) {
						if (world instanceof ServerLevel _level)
							_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), x, y, z, 3, 1, 1, 1, 1);
						if (world instanceof ServerLevel _level)
							_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
									"impactful @p shake 25 3 5");
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:dian_hhu")), SoundSource.NEUTRAL, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:dian_hhu")), SoundSource.NEUTRAL, 1, 1, false);
							}
						}
						if(VFXParticleConfig.VFXParticleConfig.get() && BuxinMod.isWindows()) {
							Method_114514.entity_use_command(entityiterator, "/vfx buxin dragon_beam_hit");
						}
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
							if(v == 0)
								entityToSpawn.setCustomName(Component.literal("p"));
							else if(v == 1)
								entityToSpawn.setCustomName(Component.literal("b"));
							else if(v == 2)
								entityToSpawn.setCustomName(Component.literal("h"));
							entityToSpawn.moveTo((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), 0, 0);
							entityToSpawn.setYBodyRot(0);
							entityToSpawn.setYHeadRot(0);
							entityToSpawn.setDeltaMovement(0, 0, 0);
							if (entityToSpawn instanceof Mob _mobToSpawn)
								_mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
							world.addFreshEntity(entityToSpawn);
						}
						if (world instanceof ServerLevel _level)
							_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), (entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), 2, 1, 1, 1, 1);
						entityiterator.hurt(entityiterator.damageSources().magic(), 30);
						entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(EntityPatch -> {
							if (EntityPatch instanceof LivingEntityPatch<?> LivingEntityPatch) {
								LivingEntityPatch.playAnimationSynchronized(Animations.WRATHFUL_LIGHTING, 0F);
							}
						});
						if (entity instanceof Player _player)
							_player.getCooldowns().addCooldown(itemstack.getItem(), 140);
					}
				}
			}
		}

	}
}
