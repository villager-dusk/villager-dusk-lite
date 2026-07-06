
package net.mcreator.buxin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class HotbarSwitchSound {

    private static int lastSelectedSlot = 0;
    private static long lastSwitchTime = 0;

    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        int currentSlot = mc.player.getInventory().selected;
        long currentTime = System.currentTimeMillis();

        if (currentSlot != lastSelectedSlot) {
            ItemStack newItem = mc.player.getInventory().getSelected();
            if (!newItem.isEmpty()) {
                playHotbarSwitchSound(newItem, event.getScrollDelta() > 0);
            }
            lastSelectedSlot = currentSlot;
            lastSwitchTime = currentTime;
        }
    }

    private static void playHotbarSwitchSound(ItemStack itemStack, boolean scrollUp) {
        Minecraft mc = Minecraft.getInstance();

        SoundEvent sound = getHotbarSoundForItem(itemStack);
        if (sound == null) return;
        float pitch = 1;

        if (mc.level != null && mc.player != null) {
            mc.getSoundManager().play(
                new SimpleSoundInstance(
                    sound.getLocation(),
                    SoundSource.PLAYERS,
                    1,
                    pitch,
                    mc.level.random,
                    false,
                    0,
                    SoundInstance.Attenuation.NONE,
                    mc.player.getX(),
                    mc.player.getY(),
                    mc.player.getZ(),
                    false
                )
            );
        }
    }
    
    private static SoundEvent getHotbarSoundForItem(ItemStack itemStack) {
        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(itemStack.getItem());
        if (itemId != null && itemId.getNamespace().equals("buxin") && itemStack.getItem() instanceof SwordItem) {
            return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin", "sword_draw"));
        } else if (itemStack.getItem() instanceof SwordItem) {
            return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin", "sword_draw"));
        }
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.item.pickup"));
    }
}
