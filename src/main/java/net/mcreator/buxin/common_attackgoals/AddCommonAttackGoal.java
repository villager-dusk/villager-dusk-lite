
package net.mcreator.buxin.common_attackgoals;

import net.mcreator.buxin.ai.PearlUseGoal;
import net.mcreator.buxin.ai.RaiseShieldGoal;
import net.mcreator.buxin.ai.ShieldBashUseGoal;
import net.mcreator.buxin.entity.*;
import net.mcreator.buxin.entity.e_null.NullEntity;
import net.mcreator.buxin.entity.father.BattleVillagerEntity;
import net.mcreator.buxin.entity.purple_obsidian_herobrine.PurpleObsidianHerobrineEntity;
import net.mcreator.buxin.entity.shadow_herorbrine.ShadowHerobrineEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

public class AddCommonAttackGoal {
    public static void Herobrine(Entity entity) {
        if(entity instanceof Mob entity1) {
            entity1.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(entity1, Player.class, false, false));
            entity1.goalSelector.addGoal(2, new PearlUseGoal(entity1));
        }
    }

    public static void Golem(Entity entity) {
        if(entity instanceof Mob entity1) {
            entity1.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(entity1, Player.class, false, false));
        }
    }

    public static void Villager(Entity entity) {
        if(entity instanceof Mob entity1) {
            entity1.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(entity1, Player.class, false, false));
            entity1.goalSelector.addGoal(2, new PearlUseGoal(entity1));
            entity1.goalSelector.addGoal(2, new ShieldBashUseGoal(entity1));
            entity1.goalSelector.addGoal(2, new PearlUseGoal(entity1));
            if(entity1 instanceof BattleVillagerEntity battleVillagerEntity) {
                entity1.goalSelector.addGoal(1, new RaiseShieldGoal(battleVillagerEntity));
            }
        }
    }

    public static void polarBear(Entity entity) {
        if(entity instanceof Mob entity1) {
            entity1.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(entity1, Player.class, false, false));
            entity1.goalSelector.addGoal(2, new PearlUseGoal(entity1));
        }
    }

    public static void Grave(Entity entity) {
        if(entity instanceof Mob entity1) {
            entity1.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(entity1, Player.class, false, false));
            entity1.goalSelector.addGoal(2, new PearlUseGoal(entity1));
        }
    }
    public static void Player(Entity entity) {
        if(entity instanceof Mob entity1) {
            entity1.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(entity1, Player.class, false, false));
            entity1.goalSelector.addGoal(2, new PearlUseGoal(entity1));
        }
    }
    public static void Bluedemon(Entity entity) {
        if(entity instanceof Mob entity1) {
            entity1.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(entity1, Player.class, false, false));
        }
    }
}
