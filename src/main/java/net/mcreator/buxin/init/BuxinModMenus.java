/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.buxin.init;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.world.inventory.HorrorHerobrineScreenMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BuxinModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, BuxinMod.MODID);
	public static final RegistryObject<MenuType<HorrorHerobrineScreenMenu>> HORROR_HEROBRINE_SCREEN = REGISTRY.register("horror_herobrine_screen",
			() -> IForgeMenuType.create((windowId, inv, data) -> new HorrorHerobrineScreenMenu(windowId, inv, data)));
}