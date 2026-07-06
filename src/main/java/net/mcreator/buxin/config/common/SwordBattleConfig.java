
package net.mcreator.buxin.config.common;

import net.minecraftforge.common.ForgeConfigSpec;

public class SwordBattleConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.IntValue SwordBattleNeutralizeValue;
    static {
        BUILDER.push("SwordBattleNeutralizeValue");
        SwordBattleNeutralizeValue = BUILDER.comment("Number of attacks required to break through a parry.").defineInRange("SwordBattleNeutralizeValue", 15, 1, 100);
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
