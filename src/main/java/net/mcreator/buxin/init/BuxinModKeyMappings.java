
package net.mcreator.buxin.init;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.network.*;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class BuxinModKeyMappings {
    /*
     * No changes needed for KeyMapping usage:
     * - KeyMapping constructor signature remains identical between 1.19.2 and 1.20.1
     * - GLFW key constants are unchanged
     * - Minecraft.getInstance() and player access pattern is same
     * - consumeClick() method still exists and works identically in 1.20.1
     */

    public static final KeyMapping KILL_SELF = new KeyMapping("key.buxin.kill_self", GLFW.GLFW_KEY_N, "key.categories.buxin") {
        private boolean isDownOld = false;
        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (isDownOld != isDown && isDown) {
                BuxinMod.PACKET_HANDLER.sendToServer(new KillSelfMessage(0, 0));
                if (Minecraft.getInstance().player != null) {
                    KillSelfMessage.pressAction(Minecraft.getInstance().player, 0, 0);
                }
            }
            isDownOld = isDown;
        }
    };
    public static final KeyMapping RUNOFF = new KeyMapping("key.buxin.run_off", GLFW.GLFW_KEY_V, "key.categories.buxin") {
        private boolean isDownOld = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (isDownOld != isDown && isDown) {
                BuxinMod.PACKET_HANDLER.sendToServer(new RunOffMessage(0, 0));
                if (Minecraft.getInstance().player != null) {
                    RunOffMessage.pressAction(Minecraft.getInstance().player, 0, 0);
                }
            }
            isDownOld = isDown;
        }
    };
    public static final KeyMapping THROW_ENDER_PERAL = new KeyMapping("key.buxin.throw_ender_peral", GLFW.GLFW_KEY_Z, "key.categories.buxin") {
        private boolean isDownOld = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (isDownOld != isDown && isDown) {
                THROW_ENDER_PERAL_LASTPRESS = System.currentTimeMillis();
            } else if (isDownOld != isDown && !isDown) {
                int dt = (int) (System.currentTimeMillis() - THROW_ENDER_PERAL_LASTPRESS);
                BuxinMod.PACKET_HANDLER.sendToServer(new ThrowEnderPeralMessage(1, dt));
                if (Minecraft.getInstance().player != null) {
                    ThrowEnderPeralMessage.pressAction(Minecraft.getInstance().player, 1, dt);
                }
            }
            isDownOld = isDown;
        }
    };
    public static final KeyMapping PUT_ENTITY_TO_DIE = new KeyMapping("key.buxin.put_entity_to_die", GLFW.GLFW_KEY_X, "key.categories.buxin") {
        private boolean isDownOld = false;
        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (isDownOld != isDown && isDown) {
                if(Math.random() > 0.5) {
                    BuxinMod.PACKET_HANDLER.sendToServer(new PutEntityToDieMessage(0, 0));
                    if (Minecraft.getInstance().player != null) {
                        PutEntityToDieMessage.pressAction(Minecraft.getInstance().player, 0, 0);
                    }
                } else {
                    BuxinMod.PACKET_HANDLER.sendToServer(new SpecialSkillMessage(0, 0));
                    if (Minecraft.getInstance().player != null) {
                        SpecialSkillMessage.pressAction(Minecraft.getInstance().player, 0, 0);
                    }
                }
            }
            isDownOld = isDown;
        }
    };
    private static long THROW_ENDER_PERAL_LASTPRESS = 0;

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        //event.register(SPECIAL_KEY);
        //event.register(KICK);
        event.register(THROW_ENDER_PERAL);
        event.register(RUNOFF);
        event.register(KILL_SELF);
        event.register(PUT_ENTITY_TO_DIE);
    }

    @Mod.EventBusSubscriber({Dist.CLIENT})
    public static class KeyEventListener {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            // No change: TickEvent.ClientTickEvent is still valid in 1.20.1/Forge 47.4.10
            // and the event phase handling remains compatible
            if (Minecraft.getInstance().screen == null) {
                //KICK.consumeClick();
                THROW_ENDER_PERAL.consumeClick();
                //SPECIAL_KEY.consumeClick();
                RUNOFF.consumeClick();
                KILL_SELF.consumeClick();
                PUT_ENTITY_TO_DIE.consumeClick();
            }
        }
    }
}
