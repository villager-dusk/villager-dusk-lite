
package net.mcreator.buxin.init;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BuxinModBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, BuxinMod.MODID);
    public static final RegistryObject<Block> DARK_BLOCK_ZHU = REGISTRY.register("dark_block_zhu", () -> new DarkBlockZhuBlock());
    public static final RegistryObject<Block> DARK_BLOCK_ZHU_2 = REGISTRY.register("dark_block_zhu_2", () -> new DarkBlockZhu2Block());
    public static final RegistryObject<Block> AMY = REGISTRY.register("amy", () -> new AmyBlock());
    public static final RegistryObject<Block> LIGHT_BLOCK_PHASE_1 = REGISTRY.register("light_block_phase_1", () -> new LightBlockPhase1Block());
    public static final RegistryObject<Block> LIGHT_BLOCK_PHASE_2 = REGISTRY.register("light_block_phase_2", () -> new LightBlockPhase2Block());
    public static final RegistryObject<Block> LIGHT_BLOCK_PHASE_3 = REGISTRY.register("light_block_phase_3", () -> new LightBlockPhase3Block());
    public static final RegistryObject<Block> PURPLE_OBSIDIAN = REGISTRY.register("purple_obsidian", () -> new PurpleObsidianBlock());
    public static final RegistryObject<Block> BETTER_DIAMOND_BLOCK = REGISTRY.register("better_diamond_block", () -> new BetterDiamondBlock());
}
