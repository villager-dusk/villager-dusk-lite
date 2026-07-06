
package net.mcreator.buxin.config.common;

import net.minecraftforge.common.ForgeConfigSpec;

public class GuardShakeValueConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.IntValue GuardShakeTimeValue;
    public static final ForgeConfigSpec.IntValue GuardShakeAmplitudeValue;
    public static final ForgeConfigSpec.IntValue GuardShakeFrequencyValue;
    static {
        BUILDER.push("GuardShakeTimeValue");
        GuardShakeTimeValue = BUILDER.comment("The duration of the screen shake effect produced when blocking.").defineInRange("GuardShakeTimeValue", 18, 0, 400);
        BUILDER.pop();
        BUILDER.push("GuardShakeAmplitudeValue");
        GuardShakeAmplitudeValue = BUILDER.comment("The intensity of the screen shake effect produced when blocking.").defineInRange("GuardShakeAmplitudeValue", 4, 0, 10);
        BUILDER.pop();
        BUILDER.push("GuardShakeFrequencyValue");
        GuardShakeFrequencyValue = BUILDER.comment("The magnitude of the screen shake effect produced when blocking.").defineInRange("GuardShakeFrequencyValue", 4, 0, 10);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
    public static void saveConfig() {
        try {
            SPEC.save();
        } catch (Exception e) {
            // 空catch块，保留原逻辑
        }
    }
}
