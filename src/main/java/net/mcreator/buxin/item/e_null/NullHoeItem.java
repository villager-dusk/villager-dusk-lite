package net.mcreator.buxin.item.e_null;

import net.mcreator.buxin.entity.e_null.NullHoeEntity;
import net.mcreator.buxin.init.BuxinModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

// 修改说明：
// 1. Minecraft 1.20.1 + Forge 47.4.10 中，HoeItem 构造函数签名未变，仍为 (Tier, int, float, Properties)
// 2. Tier 接口方法签名在 1.20.1 中保持不变（getUses/getSpeed/getAttackDamageBonus/getLevel/getEnchantmentValue/getRepairIngredient）
// 3. MobSpawnType.MOB_SUMMONED 在 1.20.1 中仍存在且有效（已验证：Forge 47.4.10 基于 MC 1.20.1，MobSpawnType 枚举未变更）
// 4. ServerLevel.getCurrentDifficultyAt(BlockPos) 方法在 1.20.1 中仍存在（参数类型为 BlockPos，此处使用 sword.blockPosition() 正确）
// 5. Player.getPersistentData().putUUID() 和 getUUID() 在 1.20.1 中仍为 UUID 类型，API 兼容
// 6. 所有 import 路径与 1.20.1 完全一致，无需修改
// → 综上：此文件**无需任何代码逻辑修改**，完全兼容 MC 1.20.1 + Forge 47.4.10
// （依赖库 geckolib/epicfight/indestructible 的升级不影响本类，因本类未直接引用这些库）

public class NullHoeItem extends HoeItem {

    public NullHoeItem() {
        super(new Tier() {
            public int getUses() {
                return 100;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 5.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 2;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 3, -3.0F, (new Properties()));
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionhand) {
        ItemStack stack = player.getItemInHand(interactionhand);

        if (false) {
            return InteractionResultHolder.pass(stack);
        }

        if (!(level instanceof ServerLevel server)) {
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }

        if (player.getPersistentData().contains("NullHoeUUID")) {
            return InteractionResultHolder.pass(stack);
        }

        var look = player.getLookAngle();
        double sx = player.getX() + look.x * 1.0;
        double sy = player.getEyeY() - 0.2;
        double sz = player.getZ() + look.z * 1.0;

        NullHoeEntity sword = BuxinModEntities.NULL_HOE.get().create(server);
        if (sword == null) {
            return InteractionResultHolder.fail(stack);
        }

        sword.moveTo(sx, sy, sz, player.getYRot(), player.getXRot());
        sword.setPlayer(player);
        sword.setPlayerUUID(player.getUUID());
        sword.setReturnGameTime(server.getGameTime() + 600L);
        sword.finalizeSpawn(server, server.getCurrentDifficultyAt(sword.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
        server.addFreshEntity(sword);
        sword.setItemInHand(InteractionHand.MAIN_HAND, stack.copy());

        player.setItemInHand(interactionhand, ItemStack.EMPTY);
        player.getCooldowns().addCooldown(this, 10);
        player.getPersistentData().putUUID("NullHoeUUID", sword.getUUID());

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}