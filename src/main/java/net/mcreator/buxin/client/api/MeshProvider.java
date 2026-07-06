
package net.mcreator.buxin.client.api;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.client.model.Mesh;

@OnlyIn(Dist.CLIENT)
@FunctionalInterface
public interface MeshProvider {
    Mesh get();
}
