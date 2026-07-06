
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.config.common.GuardShakeValueConfig;

import net.mcreator.buxin.entity.WhatShitPhase2Entity;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModGameRules;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.init.BuxinModMobEffects;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber
public class BuxinEntityBlock {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null && event.getSource().getEntity() != null && !event.getEntity().level().isClientSide() && event.getEntity().getServer() != null && event.getEntity().level().getServer() != null) {
            // MODIFIED: Changed getLevel() to level() for LivingEntity in 1.20.1 (LivingEntity.level() is the new method)
            if(AnimationPlayer.getAnimation(event.getEntity()) == BuxinAnimations.KILL_SELF){
                if(event.isCancelable()){
                    event.setCanceled(true);
                }
            }
            try {
                execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(), event.getSource().getEntity(),event.getAmount());
            } catch (Exception e){
                System.err.println(e);
            }
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity,double amount) {
		execute(null, world, x, y, z, entity, sourceentity,amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity,double amount) {
		if (entity == null || sourceentity == null || event == null)
			return;
        if(entity instanceof WhatShitPhase2Entity){
            event.setCanceled(true);
        }
        /*            if(sourceentity instanceof Player && entity instanceof LivingEntity livingEntity && livingEntity.getHealth() < livingEntity.getMaxHealth() * 0.3 && !(entity.getPersistentData().getInt("display_die") == 0)){
                System.err.println("cj");
                sourceentity.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
                sourceentity.sendSystemMessage(Component.literal("§c" + entity.getDisplayName().getString() + "已可以处决!"));
                entity.getPersistentData().putInt("display_die",1);
            }*/
        boolean EntityAttackBlock = true;
        /*
        if (entity.getPersistentData().getBoolean("YH") || entity instanceof BlueDemonTridentHolidyEntity || entity instanceof HerobrinePlayerMobEntity || entity.getPersistentData().getBoolean("wu")) {
            if (event.isCancelable()) {
                event.setCanceled(true);
            }
        }

         */
        /*
        if(entity instanceof LivingEntity && ((LivingEntity) entity).getHealth() / ((LivingEntity) entity).getMaxHealth() <= 0.3 && sourceentity instanceof Player && (entity.getPersistentData().getInt("display_die") == 0)){
            entity.getPersistentData().putInt("display_die",1);
           // sourceentity.sendSystemMessage(Component.literal(String.valueOf(((LivingEntity) entity).getHealth() / ((LivingEntity) entity).getMaxHealth())));
            if(!entity.level().isClientSide) {
                Method_114514.entity_use_command(sourceentity, "/playsound minecraft:ui.button.click voice @s ~ ~ ~ 2 0.6");
                sourceentity.sendSystemMessage(Component.literal("§c" + entity.getDisplayName().getString() + " 已可以进行必杀处决!"));
            }
        }

         */

        if (entity instanceof LivingEntity && !entity.isOnFire() && !(((LivingEntity) entity).getMainHandItem().isEmpty())) {
            if (!(entity.getPersistentData().getDouble("byd2") == 1)) {
                if (!(entity instanceof Player) && !(entity.getPersistentData().getInt("se") == 1)) {


                }
            }
        }
    }
}
