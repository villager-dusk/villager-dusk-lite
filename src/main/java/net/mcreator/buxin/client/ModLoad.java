
package net.mcreator.buxin.client;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.my_method.SystemMethod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.awt.*;

@Mod.EventBusSubscriber(modid = BuxinMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModLoad {
    @SubscribeEvent
    public static void client2(FMLCommonSetupEvent context) {
        if (!ModList.get().isLoaded("epicfight")) {
            if (Minecraft.getInstance().player != null) {
                SystemMethod.msgbox("缺少epicfight,即 史诗战斗 模组!");
            }
            Minecraft.getInstance().stop();
        }
    }
}
