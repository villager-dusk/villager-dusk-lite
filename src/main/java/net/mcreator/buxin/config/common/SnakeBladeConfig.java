
package net.mcreator.buxin.config.common;

import net.minecraftforge.common.ForgeConfigSpec;

public class SnakeBladeConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Integer> SnakeBladeMaxTarget;
    public static final ForgeConfigSpec.ConfigValue<Integer> SnakeBladeMaxRange;
    public static final ForgeConfigSpec.ConfigValue<Integer> SnakeBladeSmoothValue;

    static {
        BUILDER.push("SnakeBladeMaxTarget");
        SnakeBladeMaxTarget = BUILDER.comment("Maximum number of attacks per use for the Serpent Blade.").defineInRange("SnakeBladeMaxTarget", 10, 2, 100);
        BUILDER.pop();
        BUILDER.push("SnakeBladeMaxRange");
        SnakeBladeMaxRange = BUILDER.comment("Maximum detection range of the Serpent Blade (in blocks).").defineInRange("SnakeBladeMaxRange", 25, 2, 100);
        BUILDER.pop();

        BUILDER.push("SnakeBladeSmoothValue");
        SnakeBladeSmoothValue = BUILDER.comment("Blade curvature of the Serpent Blade.").defineInRange("SnakeBladeSmoothValue", 1, 0, 100);
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
