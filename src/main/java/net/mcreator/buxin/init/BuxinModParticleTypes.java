
package net.mcreator.buxin.init;

import net.mcreator.buxin.BuxinMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BuxinModParticleTypes {
	public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BuxinMod.MODID);
	public static final RegistryObject<SimpleParticleType> NIUBI_22 = REGISTRY.register("niubi_22", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> NIUBI_23 = REGISTRY.register("niubi_23", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> NIUBI_24 = REGISTRY.register("niubi_24", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> HUOHUA = REGISTRY.register("huohua", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> HUOHUA_2 = REGISTRY.register("huohua_2", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> HUANG_SE_LI_ZI = REGISTRY.register("huang_se_li_zi", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> HACK_PAR = REGISTRY.register("hack_par", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> BLOOD = REGISTRY.register("blood", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> DARK = REGISTRY.register("dark", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> BLOOD_2 = REGISTRY.register("blood_2", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> RAINBOWPAR = REGISTRY.register("rainbowpar", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> PINK_LIGHT_PARTICLES = REGISTRY.register("pink_light_particles", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> POWER = REGISTRY.register("power", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> ELECTRONIC = REGISTRY.register("electronic", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> ENDER = REGISTRY.register("ender", () -> new SimpleParticleType(true));
}
