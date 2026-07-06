
package net.mcreator.buxin.config.common;

import net.minecraftforge.common.ForgeConfigSpec;

public class VFXParticleConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.BooleanValue VFXParticleConfig;
    static {
        BUILDER.push("VFXParticleConfig");
        VFXParticleConfig = BUILDER.comment("Enable VFX particles").define("VFXParticleConfig", true);
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
