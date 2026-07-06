package net.mcreator.buxin.gameasset;

import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.collider.OBBCollider;

public class BuxinModColliders {
    public static final Collider NONE = new MultiOBBCollider(1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    public static final Collider STAFF = new MultiOBBCollider(4, 0.5, 0.5, 1.9, 0.0, 0.0, 0.0);
    public static final Collider STAFF_EXTENTION = new MultiOBBCollider(4, 0.5, 0.5, 2.5, 0.0, 0.0, 0.0);
    public static final Collider STAFF_CHARYBDIS = new MultiOBBCollider(4, 0.6, 0.6, 2.3, 0.0, 0.0, 0.0);
    public static final Collider GREATSWORD = new MultiOBBCollider(3, 0.2, 0.8, 1.0, 0.0, 0.0, -1.2);
    public static final Collider AGONY = new MultiOBBCollider(5, 0.45, 0.45, 1.4, 0.0, 0.0, -1.0);
    public static final Collider AGONY_AIRSLASH = new MultiOBBCollider(4, 0.55, 0.55, 1.6, 0.0, 0.0, -0.8);
    public static final Collider AGONY_PLUNGE = new OBBCollider(5.0, 2.0, 5.0, 0.0, 0.0, 0.0);
    public static final Collider PLUNDER_PERDITION = new OBBCollider(8.0, 4.0, 8.0, 0.0, 0.0, 0.0);
    public static final Collider RUINE = new MultiOBBCollider(5, 0.4, 0.4, 1.45, 0.0, 0.0, -1.05);
    public static final Collider RUINE_COMET = new MultiOBBCollider(4, 0.6, 0.6, 1.35, 0.0, 0.0, -0.95);
    public static final Collider RUINE_REDEMPTION = new OBBCollider(1.5, 1.5, 1.5, 0.0, 0.0, 0.0);
    public static final Collider SHOULDER_BUMP = new OBBCollider(0.8, 0.8, 1.6, 0.0, 1.2, -0.6);
    public static final Collider TORMENT = new MultiOBBCollider(3, 0.4000000059604645, 0.6000000238418579, 1.399999976158142, 0.0, -0.20000000298023224, -0.6000000238418579);
    public static final Collider TORMENT_AIRSLAM = new OBBCollider(1.399999976158142, 0.800000011920929, 1.399999976158142, 0.0, 0.800000011920929, -1.600000023841858);
    public static final Collider TORMENT_BERSERK_AIRSLAM = new OBBCollider(2.0999999046325684, 2.0999999046325684, 2.0999999046325684, 0.0, 2.0999999046325684, -2.0999999046325684);
    public static final Collider TORMENT_BERSERK_DASHSLAM = new OBBCollider(1.7999999523162842, 1.600000023841858, 1.7999999523162842, 0.0, 1.600000023841858, -1.7999999523162842);
    public static final Collider KATANA_SHEATHED_AUTO = new OBBCollider(2.0, 1.0, 2.0, 0.0, 1.0, -1.0);
    public static final Collider KATANA_SHEATHED_DASH = new OBBCollider(1.600000023841858, 0.699999988079071, 2.5, 0.0, 1.0, -0.5);
    public static final Collider KATANA = new MultiOBBCollider(5, 0.2, 0.3, 1.0, 0.0, 0.0, -1.0);
    public static final Collider FATAL_DRAW = new OBBCollider(1.75, 0.7, 1.35, 0.0, 1.0, -1.0);
    public static final Collider FATAL_DRAW_DASH = new OBBCollider(1.0, 1.0, 2.75, 0.0, 1.2, -0.2);
    public static final Collider PUNCH = new MultiOBBCollider(4, 0.4, 0.6, 0.4, 0.0, 0.0, 0.0);
    public static final Collider ENDER_BLASTER_CROSS = new MultiOBBCollider(4, 0.4, 0.4, 0.4, 0.0, 0.0, 0.6);
    public static final Collider KICK = new MultiOBBCollider(4, 0.4, 0.4, 0.4, 0.0, 0.6, 0.0);
    public static final Collider KICK_HUGE = new MultiOBBCollider(4, 0.8, 0.8, 0.8, 0.0, 0.9, 0.0);
    public static final Collider ENDER_DASH = new OBBCollider(1.5, 1.5, 1.5, 0.0, 1.0, -1.0);
    public static final Collider KNEE = new MultiOBBCollider(4, 0.8, 0.8, 0.8, 0.0, 0.6, 0.0);
    public static final Collider ENDER_LASER = new MultiOBBCollider(4, 0.3, 20.0, 0.3, 0.0, -20.2, 0.0);
    public static final Collider ENDER_SHOOT = new MultiOBBCollider(4, 0.3, 3.0, 0.3, 0.0, -3.1, 0.0);
    public static final Collider ENDER_BULLET_DASH = new MultiOBBCollider(4, 1.2, 5.0, 1.2, 0.0, -5.1, 0.0);
    public static final Collider ENDER_BULLET_BACKANDFORTH = new MultiOBBCollider(4, 2.5, 10.0, 2.5, 0.0, 1.0, 0.0);
    public static final Collider ENDER_BULLET_ALl_DIRECTION = new MultiOBBCollider(4, 2.5, 10.0, 10.0, 0.0, 1.0, 0.0);
    public static final Collider ENDER_BULLET_WIDE = new OBBCollider(7.5, 1.5, 5.0, 0.0, 0.75, -5.0);
    public static final Collider ENDER_PISTOLERO = new OBBCollider(2.5, 2.0, 2.5, 0.0, -1.5, 0.0);
    public static final Collider ANTITHEUS = new MultiOBBCollider(3, 0.4000000059604645, 1.2000000476837158, 1.0, 0.0, -0.20000000298023224, -1.7000000476837158);
    public static final Collider ANTITHEUS_AGRESSION = new MultiOBBCollider(3, 1.2000000476837158, 1.600000023841858, 1.399999976158142, 0.0, -0.6000000238418579, -2.0);
    public static final Collider ANTITHEUS_AGRESSION_REAP = new OBBCollider(1.2000000476837158, 1.600000023841858, 1.600000023841858, 0.0, 0.800000011920929, -3.4000000953674316);
    public static final Collider ANTITHEUS_GUILLOTINE = new OBBCollider(3.5, 1.5, 3.5, 0.0, 0.0, 0.0);
    public static final Collider ANTITHEUS_ASCENDED_PUNCHES = new OBBCollider(1.0, 1.0, 1.7999999523162842, 0.0, 1.0, -1.149999976158142);
    public static final Collider ANTITHEUS_ASCENDED_BLINK = new OBBCollider(3.5, 1.5, 3.5, 0.0, 0.75, 0.0);
    public static final Collider ANTITHEUS_ASCENDED_DEATHFALL = new OBBCollider(5.0, 3.0, 5.0, 0.0, 0.0, 0.0);
    public static final Collider ANTITHEUS_SHOOT = new MultiOBBCollider(4, 0.5, 1.5, 0.5, 0.0, -1.5, 0.0);
    public static final Collider HERSCHER = new MultiOBBCollider(5, 0.2, 0.3, 0.8, 0.0, 0.0, -0.8);
    public static final Collider HERSCHER_THRUST = new OBBCollider(0.6000000238418579, 0.6000000238418579, 1.2999999523162842, 0.0, 0.800000011920929, -1.100000023841858);
    public static final Collider HERSCHER_CHARGE_1 = new MultiOBBCollider(6, 0.2, 0.4, 1.2, 0.0, 0.0, -1.2);
    public static final Collider HERSCHER_CHARGE_2 = new MultiOBBCollider(8, 0.2, 0.6, 1.6, 0.0, 0.0, -1.6);
    public static final Collider HERSCHER_CHARGE_3 = new MultiOBBCollider(10, 0.2, 0.8, 2.0, 0.0, 0.0, -2.0);
    public static final Collider GESETZ = new MultiOBBCollider(5, 0.4, 0.6, 1.0, 0.0, 0.0, 0.4);
    public static final Collider GESETZ_KRUMMEN = new MultiOBBCollider(5, 0.4, 0.6, 1.6, 0.0, 0.0, 0.0);
    public static final Collider GESETZ_INSET_LARGE = new MultiOBBCollider(5, 0.9, 0.6, 1.0, 0.3, 0.0, 0.4);
    public static final Collider MOONLESS = new MultiOBBCollider(4, 0.2, 1.5, 0.4, 0.0, 1.0, 0.2);
    public static final Collider MOONLESS_BYPASS = new MultiOBBCollider(4, 0.2, 1.8, 0.7, 0.0, 1.0, 0.3);
    public static final Collider LUNAR_ECHO = new OBBCollider(6.0, 6.0, 6.0, 0.0, 0.0, -6.0);
    public static final Collider BODY_CLOSE = new OBBCollider(1.5, 1.5, 1.5, 0.0, 0.0, 0.0);
    public static final Collider SOLAR = new MultiOBBCollider(4, 0.4000000059604645, 0.6000000238418579, 1.7999999523162842, 0.0, 0.0, -1.0);
    public static final Collider SOLAR_HORNO = new OBBCollider(5.0, 1.5, 5.0, 0.0, 0.0, 0.0);

    public BuxinModColliders() {
    }
}