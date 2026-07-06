
package net.mcreator.buxin.block;

import net.mcreator.buxin.procedures.DarkBlockZhuFangZhiFangKuaiShiProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class DarkBlockZhu2Block extends Block {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	public DarkBlockZhu2Block() {
		super(Properties.of()
				.sound(new ForgeSoundType(1.0f, 1.0f, 
						() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:nosound")),
						() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:nosound")),
						() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:metal2")),
						() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:nosound")),
						() -> ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:nosound"))))
				.strength(1f, 10f).noOcclusion().hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true).isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
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

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		RandomSource random = context.getLevel().getRandom();
		Direction[] horizontals = {
				Direction.NORTH,
				Direction.EAST,
				Direction.SOUTH,
				Direction.WEST
		};
		Direction randomFacing = horizontals[random.nextInt(horizontals.length)];
		return this.defaultBlockState().setValue(FACING, randomFacing);
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

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
