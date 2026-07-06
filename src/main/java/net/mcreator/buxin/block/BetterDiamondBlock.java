
package net.mcreator.buxin.block;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.shadow_herorbrine.ShadowHerobrineEntity;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BetterDiamondBlock extends Block {
	public BetterDiamondBlock() {
		super(Properties.of()
				.mapColor(MapColor.STONE)
				.sound(SoundType.DEEPSLATE_BRICKS)
				.strength(1f, 10f)
				.lightLevel(s -> 7)
				.pushReaction(PushReaction.NORMAL)
		);
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 25;
	}

	public boolean isFoil(ItemStack itemstack) {
		return true;
	}
}
