
package net.mcreator.buxin.item.e_null;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.e_null.NullPickaxeEntity;
import net.mcreator.buxin.init.BuxinModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class NullPickaxeItem extends PickaxeItem {

    public NullPickaxeItem() {
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

        if (!(level instanceof ServerLevel server)) {
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }

        if (player.getPersistentData().contains("NullPickaxeUUID")) {
            return InteractionResultHolder.pass(stack);
        }

        var look = player.getLookAngle();
        double sx = player.getX() + look.x;
        double sy = player.getEyeY() - 0.2;
        double sz = player.getZ() + look.z;

        if(!level.isClientSide) {
            NullPickaxeEntity sword = new NullPickaxeEntity(BuxinModEntities.NULL_PICKAXE.get(), player.level);
            sword.moveTo(player.position());
            if(player.level instanceof ServerLevel serverLevel){
                serverLevel.addFreshEntity(sword);
                sword.setPlayer(player);
                sword.setPlayerUUID(player.getUUID());
                sword.setReturnGameTime(server.getGameTime() + 600L);
                sword.setItemInHand(InteractionHand.MAIN_HAND, stack.copy());
                player.setItemInHand(interactionhand, ItemStack.EMPTY);
                player.getCooldowns().addCooldown(this, 10);
                player.getPersistentData().putUUID("NullPickaxeUUID", sword.getUUID());
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
