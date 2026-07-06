
package net.mcreator.buxin.config.client;

import net.minecraftforge.common.ForgeConfigSpec;

public class BrightValueConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.IntValue BrightValue;
    static {
        BUILDER.push("BrightValue");
        BrightValue = BUILDER.comment("Luminous item brightness").defineInRange("BrightValue",15728880,1,15728880);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
    public static void saveConfig() {
        try {
            SPEC.save();
        } catch (Exception e) {
            // Exception handling retained as per original
        }
    }
}
