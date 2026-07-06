
package net.mcreator.buxin.my_method;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class Math114514 {
    public static int squared_int(int value){
        return value * value;
    }
    public static double squared_double(double value){
        return value * value;
    }
    public static float squared_float(float value){
        return value * value;
    }
    public static double entity1_distance_to_entity2_xyz(Entity entity1, Entity entity2){
        double deltaX = entity1.getX() - entity2.getX();
        double deltaY = entity1.getY() - entity2.getY();
        double deltaZ = entity1.getZ() - entity2.getZ();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }
    public static double entity1_distance_to_entity2_xy(Entity entity1, Entity entity2){
        double deltaX = entity1.getX() - entity2.getX();
        double deltaZ = entity1.getZ() - entity2.getZ();
        return Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
    }
    public static float health_percentage_of_entity(Entity entity){
        return ((LivingEntity) entity).getHealth() / ((LivingEntity) entity).getMaxHealth();
    }
    public static int getHealthColor(float percent) {
        float r = Math.min(1.0F, (1.0F - percent) * 2.0F);
        float g = Math.min(1.0F, percent * 2.0F);
        return 0xFF000000 | ((int)(r * 255) << 16) | ((int)(g * 255) << 8);
    }
    public static float normalRGBToMcRGB(float normalRGB){
        return (float) 1 / 255 * normalRGB;
    }

    public static int convertVersionToInt(String version) {
        String numeric = version.replace(".", "");
        return Integer.parseInt(numeric);
    }
}
