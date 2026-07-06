
package net.mcreator.buxin.config.common;

import net.minecraftforge.common.ForgeConfigSpec;

public class RunningSpeedMultipleConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.DoubleValue RunningSpeedMultiple;
    static {
        BUILDER.push("RunningSpeedMultiple");
        RunningSpeedMultiple = BUILDER.comment("Running speed multiplier when consuming stamina.").defineInRange("RunningSpeedMultiple", 1.25d, 0d, 10.0d);
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
