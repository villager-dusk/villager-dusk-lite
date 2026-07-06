
package net.mcreator.buxin.init;

import net.minecraft.world.level.GameRules;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BuxinModGameRules {
    public static final GameRules.Key<GameRules.IntegerValue> SPARK_NUMBER = GameRules.register("sparkNumber", GameRules.Category.PLAYER, GameRules.IntegerValue.create(100));
    public static final GameRules.Key<GameRules.IntegerValue> CLASH_PERCENT = GameRules.register("clashPercent", GameRules.Category.PLAYER, GameRules.IntegerValue.create(65));
    public static final GameRules.Key<GameRules.BooleanValue> DOMOBSUSELAVA = GameRules.register("domobsuselava", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
    public static final GameRules.Key<GameRules.BooleanValue> DO_MOBS_USE_FIRE_BALL = GameRules.register("doMobsUseFireBall", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
    public static final GameRules.Key<GameRules.BooleanValue> DOMOBSUSEENDERPEARL = GameRules.register("domobsuseenderpearl", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
    public static final GameRules.Key<GameRules.BooleanValue> DO_PLAYER_MOBS_USE_WEAPONS = GameRules.register("doPlayerMobsUseWeapons", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
    public static final GameRules.Key<GameRules.BooleanValue> DOM_MOBS_USE_WATER = GameRules.register("domMobsUseWater", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
    public static final GameRules.Key<GameRules.BooleanValue> DO_MOBS_CAN_LEAVE_FROM_BOAT = GameRules.register("doMobsCanLeaveFromBoat", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
    public static final GameRules.Key<GameRules.BooleanValue> DO_ANIMATION_CLASH = GameRules.register("doAnimationClash", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
    public static final GameRules.Key<GameRules.BooleanValue> SPECIAL_ATTACK = GameRules.register("specialAttack", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));
}
