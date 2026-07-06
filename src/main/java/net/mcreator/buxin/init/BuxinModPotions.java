package net.mcreator.buxin.init;

import net.mcreator.buxin.BuxinMod;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BuxinModPotions {
    public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(ForgeRegistries.POTIONS, BuxinMod.MODID);
    public static final RegistryObject<Potion> ZUANSHIYAOSHUI = REGISTRY.register("zuanshiyaoshui", () -> new Potion(new MobEffectInstance(BuxinModMobEffects.ZUANSHI.get(), 6000)));
    public static final RegistryObject<Potion> SUPER_STAMINA = REGISTRY.register("super_stamina", () -> new Potion(new MobEffectInstance(BuxinModMobEffects.SUPER_STAMINA_EFFECT.get(), 7200, 0, false, false)));
}