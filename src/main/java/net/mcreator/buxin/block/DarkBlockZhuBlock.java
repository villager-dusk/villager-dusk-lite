
package net.mcreator.buxin.block;

import net.mcreator.buxin.procedures.DarkBlockZhuFangZhiFangKuaiShiProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.List;

public class DarkBlockZhuBlock extends Block {
	public DarkBlockZhuBlock() {
		super(BlockBehaviour.Properties.of()
				.sound(new ForgeSoundType(1.0f, 1.0f, 
						() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:nosound")), 
						() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:nosound")),
						() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:metal2")), 
						() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:nosound")),
						() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:nosound"))))
				.strength(1f, 10f).noOcclusion().hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true).isRedstoneConductor((bs, br, bp) -> false));
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 0;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	// 修复：1.20.1 中 getDrops 方法签名已改为使用 LootParams.Builder
	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		List<ItemStack> dropsOriginal = super.getDrops(state, builder);
		if (!dropsOriginal.isEmpty())
			return dropsOriginal;
		return Collections.singletonList(new ItemStack(this, 1));
	}

	@Override
	public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(blockstate, world, pos, oldState, moving);
		DarkBlockZhuFangZhiFangKuaiShiProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}
}
