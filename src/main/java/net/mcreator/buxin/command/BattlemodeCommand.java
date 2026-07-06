
package net.mcreator.buxin.command;

import net.mcreator.buxin.procedures.BattlemodeEventProcedure;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BattlemodeCommand {
	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
		dispatcher.register(Commands.literal("battlemode")
			.executes(arguments -> {
				ServerLevel world = arguments.getSource().getLevel();
				double x = arguments.getSource().getPosition().x();
				double y = arguments.getSource().getPosition().y();
				double z = arguments.getSource().getPosition().z();
				Entity entity = arguments.getSource().getEntity();
				if (entity == null || world.isClientSide())
					entity = FakePlayerFactory.getMinecraft(world);
				Direction direction = entity.getDirection();

				BattlemodeEventProcedure.execute(entity, world, x, y, z);
				return Command.SINGLE_SUCCESS;
			})
		);
	}
}
