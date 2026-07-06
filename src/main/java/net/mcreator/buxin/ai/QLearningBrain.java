
package net.mcreator.buxin.ai;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

public class QLearningBrain {
    private Map<String, Double> qTable = new HashMap<>();
    private String lastState;
    private boolean lastFlee;
    private double lastQValue = 0.0;
    private String lastHealthState;
    private String lastTargetHealthState = "high";

    public boolean shouldFlee(double health, Entity target) {
        String myState = getHealthState(health);
        String targetState = getTargetHealthState(target);

        String state = myState + "_vs_" + targetState;

        if (!qTable.containsKey(state)) {
            qTable.put(state, getInitialQValue(myState, targetState));
        }

        lastQValue = qTable.get(state);

        if (lastState != null && lastHealthState != null) {
            double reward = getReward(health, target, lastTargetHealthState);
            double newQ = qTable.get(lastState) + 0.3 * (reward + 0.9 * qTable.get(state) - qTable.get(lastState));
            qTable.put(lastState, newQ);
        }

        boolean flee = qTable.get(state) < 0;

        lastState = state;
        lastFlee = flee;
        lastHealthState = myState;
        lastTargetHealthState = targetState;

        return flee;
    }

    public double getLastQValue() {
        return lastQValue;
    }

    public boolean getLastDecision() {
        return lastFlee;
    }

    private String getHealthState(double health) {
        if (health < 0.33) return "low";
        if (health < 0.66) return "mid";
        return "high";
    }

    private String getTargetHealthState(Entity target) {
        if (target == null) return "high";

        double targetHealth = 1.0;
        if (target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity) target;
            targetHealth = livingTarget.getHealth() / livingTarget.getMaxHealth();
        }

        if (targetHealth < 0.33) return "low";
        if (targetHealth < 0.66) return "mid";
        return "high";
    }

    private double getInitialQValue(String myState, String targetState) {
        double baseValue = switch (myState) {
            case "low" -> -6.0;
            case "mid" -> 0.0;
            case "high" -> 3.0;
            default -> 0.0;
        };

        double targetEffect = switch (targetState) {
            case "low" -> 4.0;
            case "mid" -> 0.0;
            case "high" -> -3.0;
            default -> 0.0;
        };

        return baseValue + targetEffect;
    }

    private double getReward(double currentHealth, Entity target, String lastTargetState) {
        String currentMyState = getHealthState(currentHealth);
        String currentTargetState = getTargetHealthState(target);

        double reward = 0;

        if (lastFlee) {
            reward = evaluateFleeDecision(currentMyState, lastHealthState, currentTargetState, lastTargetState);
        } else {
            reward = evaluateFightDecision(currentMyState, lastHealthState, currentTargetState, lastTargetState);
        }

        return reward;
    }

    private double evaluateFleeDecision(String currentMyState, String lastMyState,
                                        String currentTargetState, String lastTargetState) {
        double reward = 0;

        if (currentTargetState.equals("high") && lastTargetState.equals("high")) {
            return 0;
        }

        if (isHealthImproved(currentMyState, lastMyState)) {
            reward += 6;
        }

        if (isHealthImproved(currentTargetState, lastTargetState) &&
                lastTargetState.equals("low")) {
            reward -= 4;
        }

        if (currentTargetState.equals("low")) {
            reward -= 5;
        }

        if (currentMyState.equals("low") && currentTargetState.equals("high")) {
            reward += 8;
        }

        if (currentMyState.equals("mid") && currentTargetState.equals("high")) {
            reward += 3;
        }

        return reward;
    }

    private double evaluateFightDecision(String currentMyState, String lastMyState,
                                         String currentTargetState, String lastTargetState) {
        double reward = 0;

        if (currentTargetState.equals("high") && lastTargetState.equals("high")) {
            return 0;
        }

        if (isHealthWorsened(currentTargetState, lastTargetState)) {
            reward += 7;
        }

        if (currentTargetState.equals("low")) {
            reward += 8;
        }

        if (isHealthWorsened(currentMyState, lastMyState)) {
            reward -= 10;
        }

        if (lastMyState.equals("low") && currentTargetState.equals("low")) {
            reward += 12;
        }

        if (lastMyState.equals(currentMyState) && currentTargetState.equals("low")) {
            reward += 5;
        }

        if (lastMyState.equals("high") && currentTargetState.equals("low")) {
            reward += 3;
        }

        if (lastMyState.equals("low") && currentTargetState.equals("high")) {
            reward -= 8;
        }

        return reward;
    }

    private boolean isHealthImproved(String current, String last) {
        return getHealthPriority(current) > getHealthPriority(last);
    }

    private boolean isHealthWorsened(String current, String last) {
        return getHealthPriority(current) < getHealthPriority(last);
    }

    private int getHealthPriority(String state) {
        return switch (state) {
            case "low" -> 1;
            case "mid" -> 2;
            case "high" -> 3;
            default -> 0;
        };
    }
}
