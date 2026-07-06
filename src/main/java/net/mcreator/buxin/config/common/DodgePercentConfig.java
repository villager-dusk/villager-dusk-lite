
package net.mcreator.buxin.config.common;

import net.minecraftforge.common.ForgeConfigSpec;

public class DodgePercentConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.IntValue EntityDodgePercentage;
    static {
        BUILDER.push("EntityDodgePercentge");
        EntityDodgePercentage = BUILDER.comment("The probability of avoiding attacks for Minecraft Story Mode mobs/ some hostile mobs.").defineInRange("EntityDodgePercentge", 25, 0, 100);
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
