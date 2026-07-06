
package net.mcreator.buxin.procedures;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
//import se.gory_moon.player_mobs.PlayerMobs;
//import se.gory_moon.player_mobs.entity.EntityRegistry;
//import se.gory_moon.player_mobs.entity.PlayerMobEntity;

public class Herobrine3DangZheGeShiTiShaSiLingGeShiTiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:you_cant_stop_me")), SoundSource.NEUTRAL, 1, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:you_cant_stop_me")), SoundSource.NEUTRAL, 1, 1, false);
			}
		}

		if (sourceentity instanceof Player player) {
			if (!entity.level().isClientSide()) {
				/*
				PlayerMobEntity playerMobEntity = new PlayerMobEntity(EntityRegistry.PLAYER_MOB_ENTITY.get(), entity.level());
				playerMobEntity.setCustomName(player.getCustomName());
				playerMobEntity.setProfile(player.getGameProfile());
				if (entity instanceof Mob) {
					playerMobEntity.setTarget(((Mob) entity).getTarget());
				}
				entity.level().addFreshEntity(playerMobEntity);

				 */
			}
		}
	}
}
