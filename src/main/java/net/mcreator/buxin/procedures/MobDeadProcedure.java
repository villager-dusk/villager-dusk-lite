
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class MobDeadProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity(), event.getSource().getEntity());
		}
		if(event != null && event.getEntity() instanceof Player) {
			Level world = event.getEntity().level();
			Entity entity = event.getEntity();
			if (!world.isClientSide() && world.getServer() != null) {
				try {

				} catch (Exception e){
					BuxinMod.LOGGER.error(e);
				}
			}
		}
	}

	//

	public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
		execute(null, world, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (!(entity instanceof Player)) {
            if (!entity.level().isClientSide() && entity.getServer() != null) {
				entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level() instanceof ServerLevel ? (ServerLevel) entity.level() : null, 6,
						entity.getName().getString(), entity.getDisplayName(), entity.getServer(), entity), "/indestructible @s play \"epicfight:biped/living/death\" 0.1 0.1");
			}
		}
	}
}
