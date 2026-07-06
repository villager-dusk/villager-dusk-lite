package net.mcreator.buxin.config.common;

import net.minecraftforge.common.ForgeConfigSpec;

public class FakePlayerJoinMessageConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.BooleanValue FakePlayerJoinMessage;
    static {
        BUILDER.push("FakePlayerJoinMessage");
        FakePlayerJoinMessage = BUILDER.comment("Fake player joins").define("FakePlayerJoinMessage", true);
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