
package net.mcreator.buxin.entity.snakeblade_test;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.mcreator.buxin.entity.ShifangEntity;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class SimpleChainAttackCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
                Commands.literal("chain")
                        .requires(source -> source.hasPermission(2))
                        .executes(SimpleChainAttackCommand::executeAuto)
        );
    }

    private static int executeAuto(CommandContext<CommandSourceStack> context) {
        if (context.getSource().getEntity() instanceof LivingEntity entity) {
            if (SnakeBladeMethod.process((LivingEntity) context.getSource().getEntity())) {
                if(context.getSource().getEntity() instanceof ShifangEntity){
                    Method_114514.entity_use_command(context.getSource().getEntity(),"/indestructible play \"wom:biped/combat/enderblaster_onehand_dash\" 0 1");
                }
                Method_114514.play_sound(entity.level,entity.getX(),entity.getY(),entity.getZ(),"buxin:she_ren");
            }
            return 1;
        }
        return 0;
    }
}
