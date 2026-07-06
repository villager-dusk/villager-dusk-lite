
package net.mcreator.buxin.init;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.potion.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BuxinModMobEffects {
	public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, BuxinMod.MODID);
	public static final RegistryObject<MobEffect> ZUANSHI = REGISTRY.register("zuanshi", () -> new ZuanshiMobEffect());
	public static final RegistryObject<MobEffect> KILL = REGISTRY.register("kill", () -> new KillMobEffect());
	public static final RegistryObject<MobEffect> DAMAGE = REGISTRY.register("damage", () -> new DamageMobEffect());
	public static final RegistryObject<MobEffect> SUPER_STAMINA_EFFECT = REGISTRY.register("super_stamina_effect", () -> new SuperStaminaEffectMobEffect());
	public static final RegistryObject<MobEffect> ENTITY_CANT_BLOCK = REGISTRY.register("entity_cant_block", () -> new EntityCantBlockMobEffect());
}
