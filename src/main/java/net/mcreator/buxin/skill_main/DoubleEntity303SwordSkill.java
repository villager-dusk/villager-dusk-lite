
package net.mcreator.buxin.skill_main;

import net.mcreator.buxin.entity.fake_entity.FakeEntity;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.UUID;

public class DoubleEntity303SwordSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("d206b5bc-b98b-4413-b83e-16ae590db359");

    public DoubleEntity303SwordSkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getExecuter().getEventListener().removeListener(EventType.HURT_EVENT_PRE, EVENT_UUID);
    }

    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        if (!container.getExecuter().isLogicalClient()) {
            ServerPlayerPatch playerpatch = (ServerPlayerPatch)container.getExecuter();
            SkillContainer WeaponInnateContainer = playerpatch.getSkill(SkillSlots.WEAPON_INNATE);
            if (((ServerPlayer)playerpatch.getOriginal()).getMainHandItem().getItem() == BuxinModItems.HUI_MIE_ZHI_JIAN.get()) {
                WeaponInnateContainer.getSkill().setConsumptionSynchronize(playerpatch, WeaponInnateContainer.getResource() + 0.25F);
            }
        }
    }

    public WeaponInnateSkill registerPropertiesToAnimation() {
        return this;
    }

    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        super.executeOnServer(executer, args);
        ServerPlayer serverPlayer = executer.getOriginal();
        ServerLevel serverLevel = serverPlayer.serverLevel();
        Vec3 startPos = serverPlayer.position();
        int particleCount = 4;
        float radius = 3F;
        Vec3 particleOrigin = startPos.subtract(0, 1, 0);
        for (int i = 0; i < particleCount; i++) {
            float angle = (float) i / particleCount * (float) Math.PI * 2;
            float xOffset = radius * (float) Math.cos(angle);
            float zOffset = radius * (float) Math.sin(angle);
            Vec3 particlePos = particleOrigin.add(xOffset, 0, zOffset);
            serverLevel.sendParticles(ParticleTypes.POOF, particlePos.x, particlePos.y + 2, particlePos.z, 15, 0, 0, 0, 0.1);
            Method_114514.play_sound(serverPlayer,"entity.item.pickup");
            FakeEntity fakeEntity = new FakeEntity(serverPlayer);
            fakeEntity.setPos(particleOrigin.add(xOffset, 1, zOffset));
            fakeEntity.setCustomName(serverPlayer.getName());
            serverPlayer.level().addFreshEntity(fakeEntity);
        }
    }
}
