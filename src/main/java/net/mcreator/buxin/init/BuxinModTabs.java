
package net.mcreator.buxin.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class BuxinModTabs {
    public static CreativeModeTab TAB_WL_114514_FZ_MC_VILLAGER;
    public static CreativeModeTab TAB_WHATTTTT;

    public static void load() {
        TAB_WL_114514_FZ_MC_VILLAGER = CreativeModeTab.builder()
                .title(net.minecraft.network.chat.Component.translatable("itemGroup.tabwl_114514_fz_mc_villager"))
                .icon(() -> new ItemStack(BuxinModItems.LANEMO_CHESTPLATE.get()))
                .displayItems((featureFlags, output) -> {})
                .build();

        TAB_WHATTTTT = CreativeModeTab.builder()
                .title(net.minecraft.network.chat.Component.translatable("itemGroup.tabwhattttt"))
                .icon(() -> new ItemStack(BuxinModItems.DARK_LEGENDARY_SWORD.get()))
                .displayItems((featureFlags, output) -> {})
                .build();
    }
}
