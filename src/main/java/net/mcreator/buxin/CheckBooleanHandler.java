
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.mcreator.buxin;


import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class CheckBooleanHandler {
    public static int id;
    public CheckBooleanHandler() {
    }

    public static boolean isDestructible(BlockState blockState) {
        return !blockState.isAir() && !blockState.is(Blocks.BARRIER) && !blockState.is(Blocks.BEDROCK) && !(blockState.getBlock() instanceof CommandBlock) && !(blockState.getBlock() instanceof EndGatewayBlock) && !(blockState.getBlock() instanceof EndPortalFrameBlock) && !(blockState.getBlock() instanceof EndPortalBlock);
    }

}
