
package net.mcreator.buxin.block;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.Herobrine3Entity;
import net.mcreator.buxin.entity.shadow_herorbrine.ShadowHerobrineEntity;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AmyBlock extends Block {
    public AmyBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.STONE)
                .sound(SoundType.DEEPSLATE_BRICKS)
                .strength(1f, 10f)
                .lightLevel(s -> 7)
        );
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 15;
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

        BuxinMod.queueServerWork(45, () -> {
            world.setBlock(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), Blocks.AIR.defaultBlockState(), 3);
        });

        final Vec3 _center = new Vec3(pos.getX(), pos.getY(), pos.getZ());
        List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true)
                .stream()
                .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                .collect(Collectors.toList());

        for (Entity entityiterator : _entfound) {
            if (Math.random() > 0.85)
                Method_114514.entity_use_command(entityiterator, "/particle epicfight:hit_blunt ^ ^1.5 ^ 0.1 0.1 0.1 1 1");
            if (!(entityiterator instanceof ShadowHerobrineEntity) && !(entityiterator instanceof Herobrine3Entity))
                entityiterator.hurt(entityiterator.damageSources().generic(), 3f);
            if (entityiterator instanceof Player playerEnt) {
                if (Math.random() > 0.5) {
                    if (Math.random() > 0.5)
                        Method_114514.entity_use_command(entityiterator, "/playsound buxin:metal2 voice @s");
                    else
                        Method_114514.entity_use_command(entityiterator, "/playsound buxin:obsidian_hit voice @s");
                } else {
                    if (Math.random() > 0.5)
                        Method_114514.entity_use_command(entityiterator, "/playsound buxin:metal1 voice @s");
                    else
                        Method_114514.entity_use_command(entityiterator, "/playsound buxin:obsidian_hit voice @s");
                }
                BuxinMod.queueServerWork(45, () -> Method_114514.entity_use_command(entityiterator, "/playsound buxin:shi_qu voice @s"));
            }
        }
    }

    @Override
    public void attack(BlockState blockstate, Level level, BlockPos blockpos, Player player) {
        super.attack(blockstate, level, blockpos, player);
        if (Math.random() < 0.25) {
            Method_114514.play_sound(level, player.getX(), player.getY(), player.getZ(), "buxin:metal2");
            Method_114514.entity_use_command(player, "/particle epicfight:hit_blunt ^ ^1.5 ^ 0.1 0.1 0.1 1 1");
        }
    }
}
