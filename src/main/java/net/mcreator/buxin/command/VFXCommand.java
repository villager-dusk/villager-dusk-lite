
package net.mcreator.buxin.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.mcreator.buxin.config.common.VFXParticleConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import static net.mcreator.buxin.BuxinMod.isWindows;

public class VFXCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        try {
            dispatcher.register(Commands.literal("vfx")
                    .requires(source -> source.hasPermission(1))
                    .then(Commands.argument("modid", StringArgumentType.string())
                            .then(Commands.argument("filename", StringArgumentType.string())
                                    .executes(context -> addVFXParticle(context.getSource(), context.getSource().getPosition(), StringArgumentType.getString(context, "modid"), StringArgumentType.getString(context, "filename"), context.getSource().getLevel())))));
        } catch (Exception e){

        }
    }
    public static int addVFXParticle(CommandSourceStack source,Vec3 position, String modid, String VFXname, Level world){
        if((isWindows()) && VFXParticleConfig.VFXParticleConfig.get()) {
            try {
                //ParticleEmitterInfo VFX = new ParticleEmitterInfo(new ResourceLocation(modid, VFXname));
                //var info = VFX.clone().position(position);
                //AAALevel.addParticle(world, info);
            } catch (Exception e) {
            }
        }
        return 1;
    }
}
