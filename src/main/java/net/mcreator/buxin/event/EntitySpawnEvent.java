
package net.mcreator.buxin.event;

import net.mcreator.buxin.BuxinMod;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BuxinMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntitySpawnEvent {
    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinLevelEvent event) {
        event.getEntity().getPersistentData().putInt("display_die", 0);
        if (event.getEntity() instanceof Player) {
            event.getEntity().getPersistentData().putInt("press_x_c", 0);
        }
    }
}
