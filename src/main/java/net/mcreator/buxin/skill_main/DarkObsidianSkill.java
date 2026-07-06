
package net.mcreator.buxin.skill_main;

import io.netty.buffer.Unpooled;
import net.mcreator.buxin.init.BuxinModBlocks;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.UUID;

public class DarkObsidianSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("2fbc808b-5aaa-4dfb-ad3f-12462f1b290a");

    public DarkObsidianSkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    public void onInitiate(SkillContainer container) {
        if (!container.getExecuter().isLogicalClient()) {
            this.setConsumption(container, 6.0F);
            this.setConsumptionSynchronize((ServerPlayerPatch) container.getExecuter(), 6.0F);
        }
        container.setResource(6.0F);
    }

    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getExecuter().getEventListener().removeListener(EventType.HURT_EVENT_POST, EVENT_UUID);
    }

    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        if (!container.getExecuter().isLogicalClient()) {
            ServerPlayerPatch playerpatch = (ServerPlayerPatch)container.getExecuter();
            SkillContainer WeaponInnateContainer = playerpatch.getSkill(SkillSlots.WEAPON_INNATE);
            if (((ServerPlayer)playerpatch.getOriginal()).getMainHandItem().getItem() == BuxinModItems.NIUBI_8.get()) {
                WeaponInnateContainer.getSkill().setConsumptionSynchronize(playerpatch, WeaponInnateContainer.getResource() + 0.25F);
            }
        }
    }
    public WeaponInnateSkill registerPropertiesToAnimation() {
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    public FriendlyByteBuf gatherArguments(LocalPlayerPatch executer, ControllEngine controllEngine) {
        Input input = ((LocalPlayer) executer.getOriginal()).input;
        float pulse = Mth.clamp(0.3F + EnchantmentHelper.getSneakingSpeedBonus((LivingEntity) executer.getOriginal()), 0.0F, 1.0F);
        input.tick(false, pulse);
        int forward = input.up ? 1 : 0;
        int backward = input.down ? -1 : 0;
        int left = input.left ? 1 : 0;
        int right = input.right ? -1 : 0;
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(forward);
        buf.writeInt(backward);
        buf.writeInt(left);
        buf.writeInt(right);
        return buf;
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        this.setStackSynchronize(executer, 1);
        PlayerPatch<?> PlayerPatch = executer;
        if (PlayerPatch instanceof ServerPlayerPatch ServerPlayerPatch) {
            super.executeOnServer(executer, args);
            Player player = ServerPlayerPatch.getOriginal();
            Level level = player.level();
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            Method_114514.entity_use_command(player,"/playsound buxin:obsidian_hit voice @s");
            Method_114514.shootChain(player, BuxinModBlocks.AMY.get().defaultBlockState(), 1f, 20);
            AnimationPlayer.playAnimation(player, Animations.FIST_AIR_SLASH);
            Method_114514.entity_use_command(player, "/weather clear");
        }
    }
}
