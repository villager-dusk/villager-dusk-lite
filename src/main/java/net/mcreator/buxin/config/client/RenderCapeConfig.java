
package net.mcreator.buxin.config.client;

import net.minecraftforge.common.ForgeConfigSpec;

public class RenderCapeConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.BooleanValue RenderCape;
    static {
        BUILDER.push("RenderCape");
        RenderCape = BUILDER.comment("When enabled, villagers will render capes (excluding players).").define("RenderCape", true);
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
