
package net.mcreator.buxin.block;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.List;

public class LightBlockPhase2Block extends Block {
	public LightBlockPhase2Block() {
		super(BlockBehaviour.Properties.of()
				.sound(new ForgeSoundType(1.0f, 1.0f,
						() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:nosound")),
						() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:nosound")),
						() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:nosound")),
						() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:nosound")),
						() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:nosound"))))
				.strength(1f, 10f).lightLevel(s -> 10).hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true));
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 10;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		List<ItemStack> dropsOriginal = super.getDrops(state, builder);
		if (!dropsOriginal.isEmpty())
			return dropsOriginal;
		return Collections.singletonList(new ItemStack(this, 1));
	}
}
