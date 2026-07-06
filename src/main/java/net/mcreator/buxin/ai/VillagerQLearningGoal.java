
package net.mcreator.buxin.ai;

import net.mcreator.buxin.my_method.AnimationPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import yesman.epicfight.gameasset.Animations;

import java.util.EnumSet;

public class VillagerQLearningGoal extends Goal {
    private static Mob mob = null;
    private static QLearningBrain brain = null;
    private boolean isRegistered = false;

    public VillagerQLearningGoal(Mob mob) {
        VillagerQLearningGoal.mob = mob;
        brain = new QLearningBrain();
        this.setFlags(EnumSet.noneOf(Flag.class));
        registerEventListeners();
    }

    private void registerEventListeners() {
        if (isRegistered) return;
        MinecraftForge.EVENT_BUS.register(new ZombieEventListener());
        isRegistered = true;
    }

    @Override
    public boolean canUse() {
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        if (!isRegistered) {
            registerEventListeners();
        }
    }

    @Override
    public void stop() {
    }

    @Override
    public void tick() {
        updateDisplay();
    }

    public static void onAttacked() {
        double health = mob.getHealth() / mob.getMaxHealth();
        boolean shouldFlee = brain.shouldFlee(health, mob.getTarget());

        if (shouldFlee) {
            performTeleportFlee();
        }
        updateDisplay();
    }

    private void onAttack(Entity target) {
        double health = mob.getHealth() / mob.getMaxHealth();
        boolean shouldFlee = brain.shouldFlee(health, target);

        if (shouldFlee) {
            performTeleportFlee();
        }
        updateDisplay();
    }

    private static void performTeleportFlee() {
        Entity target = mob.getTarget();
        if (target == null) return;

        double oldX = mob.getX();
        double oldY = mob.getY();
        double oldZ = mob.getZ();

        double[] newPos = findSafeTeleportLocation();
        if (newPos == null) return;

        spawnSmokeEffect(oldX, oldY, oldZ);
        AnimationPlayer.playAnimation(mob, Animations.BIPED_STEP_BACKWARD);
        spawnSmokeEffect(newPos[0], newPos[1], newPos[2]);
        //mob.level().playSound(null, newPos[0], newPos[1], newPos[2], SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundSource.HOSTILE, 0.8F, 0.8F);
    }

    private static double[] findSafeTeleportLocation() {
        Entity target = mob.getTarget();
        if (target == null) return null;

        for (int i = 0; i < 8; i++) {
            double angle = mob.getRandom().nextDouble() * Math.PI * 2;
            double distance = 8 + mob.getRandom().nextDouble() * 4;

            double x = mob.getX() + Math.cos(angle) * distance;
            double z = mob.getZ() + Math.sin(angle) * distance;
            int y = mob.level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int)x, (int)z);

            if (isPositionSafe(x, y, z)) {
                return new double[]{x, y, z};
            }
        }
        return null;
    }

    private static boolean isPositionSafe(double x, double y, double z) {
        return x != 0;
    }

    private static void spawnSmokeEffect(double x, double y, double z) {
        if (!mob.level().isClientSide()) {
            ((ServerLevel) mob.level()).sendParticles(ParticleTypes.POOF, x, y + 1.0, z, 10, 0.5, 0.5, 0.5, 0.1);
        }
    }

    private static void updateDisplay() {
        if (mob.level().isClientSide()) return;

        double health = mob.getHealth() / mob.getMaxHealth();
        String state = health < 0.33 ? "低血量" : health < 0.66 ? "中血量" : "高血量";
        String action = brain.getLastDecision() ? "逃跑" : "追击";
        String qValue = String.format("%.1f", brain.getLastQValue());

        String displayText = "§6Ai-114514\n" +
                "§7状态: " + state + "§r\n" +
                "§7决策: " + action + "§r\n" +
                "§7Q值: " + qValue + "§r\n" +
                "§7血量: " + String.format("%.1f", health * 100) + "%";

        //mob.setCustomName(Component.literal(displayText));
        mob.setCustomNameVisible(false);
    }

    private class ZombieEventListener {
        public void onZombieDamaged(LivingDamageEvent event) {
            if (event.getEntity().level().isClientSide()) return;

            if (event.getEntity().getUUID().equals(mob.getUUID())) {
                mob.level().getServer().execute(() -> {
                    onAttacked();
                });
            }
        }

        public void onZombieAttack(LivingAttackEvent event) {
            if (event.getEntity().level().isClientSide()) return;

            if (event.getSource().getEntity() instanceof Zombie) {
                Zombie attacker = (Zombie) event.getSource().getEntity();
                if (attacker.getUUID().equals(mob.getUUID())) {
                    mob.level().getServer().execute(() -> {
                        onAttack(event.getEntity());
                    });
                }
            }
        }

        public void onZombieDeath(LivingDeathEvent event) {
            if (event.getEntity().level().isClientSide()) return;

            if (event.getEntity().getUUID().equals(mob.getUUID())) {
                MinecraftForge.EVENT_BUS.unregister(this);
            }
        }
    }
}
