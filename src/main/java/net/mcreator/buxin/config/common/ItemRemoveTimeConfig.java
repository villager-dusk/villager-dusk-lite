
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.mcreator.buxin.config.common;

import net.minecraftforge.common.ForgeConfigSpec;

public class ItemRemoveTimeConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.IntValue ItemRemoveTime;
    static {
        BUILDER.push("ItemRemoveTime");
        ItemRemoveTime = BUILDER.comment("Item despawn time (in ticks, 20 ticks = 1 second)").defineInRange("ItemRemoveTime", 400, 100, 1200);
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
