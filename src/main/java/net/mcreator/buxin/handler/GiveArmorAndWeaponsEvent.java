
package net.mcreator.buxin.handler;

import net.mcreator.buxin.entity.*;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

@Mod.EventBusSubscriber
public class GiveArmorAndWeaponsEvent {
    @SubscribeEvent
    public static void playerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event){
        Player player = event.getEntity();
        player.getPersistentData().putInt("played_minutes",0);
        player.getPersistentData().putInt("killed_count",0);
        if(!player.getPersistentData().getBoolean("shouldNotGive")){
            if (!player.level().isClientSide() && player.getServer() != null) {
                player.getPersistentData().putBoolean("shouldNotGive",true);
                CommandSourceStack stack = new CommandSourceStack(
                        player,
                        player.position(),
                        player.getRotationVector(),
                        player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null,
                        6,
                        player.getName().getString(),
                        player.getDisplayName(),
                        player.getServer(),
                        player
                );

                player.getServer().getCommands().performPrefixedCommand(stack, "/give @s minecraft:iron_sword");
                player.getServer().getCommands().performPrefixedCommand(stack, "/give @s buxin:c_sword");
                player.getServer().getCommands().performPrefixedCommand(stack, "/give @s minecraft:diamond_leggings");
                player.getServer().getCommands().performPrefixedCommand(stack, "/give @s minecraft:diamond_boots");
                player.getServer().getCommands().performPrefixedCommand(stack, "/give @s minecraft:ender_pearl 8");
                player.getServer().getCommands().performPrefixedCommand(stack, "/give @s minecraft:enchanted_golden_apple 16");
            }
        }
    }
    @SubscribeEvent
    public static void reSpawn(PlayerEvent.PlayerRespawnEvent event){
        event.getEntity().getPersistentData().putBoolean("shouldNotGive",true);
    }

    @SubscribeEvent
    public static void die(LivingDeathEvent event){
        if(event.getSource().getEntity() != null && !event.getEntity().level().isClientSide()) {
            Entity sourceEntity = event.getSource().getEntity();
            sourceEntity.getPersistentData().putInt("killed_count", sourceEntity.getPersistentData().getInt("killed_count") + 1);

            int killed_count = sourceEntity.getPersistentData().getInt("killed_count");
            int played_minutes = sourceEntity.getPersistentData().getInt("played_minutes");
            Random random = new Random();
            int p = random.nextInt(10);

            if(p != 0){ return;}
            if(played_minutes > 10 || killed_count > 50){

                if(!sourceEntity.level().isClientSide() && event.getEntity() != null) {
                    int p2 = random.nextInt(4);
                    Entity entity = event.getEntity();

                    if(p2 == 0) {
                        PurpleDemonEntity purpleDemonEntity = new PurpleDemonEntity(BuxinModEntities.PURPLEDEMON.get(), sourceEntity.level());
                        purpleDemonEntity.moveTo(entity.position());
                        sourceEntity.level().addFreshEntity(purpleDemonEntity);
                    }

                    if(p2 == 1) {
                        //蓝恶魔
                        LanemobentiEntity lanemobentiEntity = new LanemobentiEntity(BuxinModEntities.LANEMOBENTI.get(), sourceEntity.level());
                        lanemobentiEntity.moveTo(entity.position());
                        sourceEntity.level().addFreshEntity(lanemobentiEntity);
                    }

                    if(p2 == 2) {
                        //114514
                        WL114514Entity wl114514Entity = new WL114514Entity(BuxinModEntities.WL_114514.get(), sourceEntity.level());
                        wl114514Entity.moveTo(entity.position());
                        sourceEntity.level().addFreshEntity(wl114514Entity);
                    }

                    if(p2 == 3) {
                        //蛇刃
                        ShifangEntity shifangEntity = new ShifangEntity(BuxinModEntities.SHIFANG.get(), sourceEntity.level());
                        shifangEntity.moveTo(entity.position());
                        sourceEntity.level().addFreshEntity(shifangEntity);
                    }

                    sourceEntity.getPersistentData().putInt("killed_count",0);
                    sourceEntity.getPersistentData().putInt("played_minutes",0);
                }
            }
        }
    }
}
