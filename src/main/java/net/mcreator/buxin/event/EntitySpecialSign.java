
package net.mcreator.buxin.event;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.client.ExecutionTextRenderer;
import net.mcreator.buxin.client.FLTextRenderer;
import net.mcreator.buxin.entity.father.BattleVillagerEntity;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.VFXTool;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.types.LongHitAnimation;

@Mod.EventBusSubscriber
public class EntitySpecialSign {
    @SubscribeEvent
    public static void onEntityTick(TickEvent.PlayerTickEvent event) {
        if(event.player.tickCount % 5 != 0){
            return;
        }
        if (event.phase == TickEvent.Phase.END) {
            event.player.level().getEntitiesOfClass(
                LivingEntity.class,
                event.player.getBoundingBox().inflate(20)
            ).forEach(entity -> {
                if(AnimationPlayer.getlivingEntityPatch(entity) != null) {
                    if (!entity.getPersistentData().getBoolean("isfuck") && entity.getHealth() < entity.getMaxHealth() * 0.3 && entity instanceof LivingEntity || 
                        !entity.getPersistentData().getBoolean("isfuck") && AnimationPlayer.getAnimation(entity) instanceof LongHitAnimation && entity instanceof LivingEntity) {
                        ExecutionTextRenderer.addExecutionEntity(entity);
                    } else {
                        ExecutionTextRenderer.removeExecutionEntity(entity);
                    }
                }
                if(entity instanceof BattleVillagerEntity) {
                    if (entity.getHealth() < 7 || AnimationPlayer.entity_getAnimation(entity) instanceof LongHitAnimation) {
                        FLTextRenderer.addFLEntity(entity);
                    } else {
                        FLTextRenderer.removeFLEntity(entity);
                    }
                }
            });
        }
    }
}
