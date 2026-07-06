
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.mcreator.buxin.config.client;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientGuiConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.BooleanValue EnableTimeGui;
    static {
        BUILDER.push("EnableTimeGui");
        EnableTimeGui = BUILDER.comment("When enabled, the time and the name of the modpack will be displayed in the top right corner.").define("EnableTimeGui", true);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
    public static void saveConfig() {
        try {
            SPEC.save();
        } catch (Exception e) {
        }
    }
}
