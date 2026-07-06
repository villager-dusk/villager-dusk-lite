
package net.mcreator.buxin.item.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mcreator.buxin.interfaces.RendersPlayerArms;
import net.mcreator.buxin.item.SherenItem;
import net.mcreator.buxin.item.model.SherenItemModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.joml.Vector3f;

@SuppressWarnings("all")
public class SherenItemRenderer extends GeoItemRenderer<SherenItem> implements RendersPlayerArms {
    public SherenItemRenderer() {
        super(new SherenItemModel());
    }


    private static final float SCALE_RECIPROCAL = 1.0f / 16.0f;
    protected boolean renderArms = false;
    protected MultiBufferSource currentBuffer;
    protected RenderType renderType;
    public ItemDisplayContext transformType;
    protected SherenItem animatable;
    private float aimProgress = 0.0f;
    private final Set<String> hiddenBones = new HashSet<>();
    private final Set<String> suppressedBones = new HashSet<>();
    private final Map<String, Vector3f> queuedBoneSetMovements = new HashMap<>();
    private final Map<String, Vector3f> queuedBoneSetRotations = new HashMap<>();
    private final Map<String, Vector3f> queuedBoneAddRotations = new HashMap<>();

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext transformType, PoseStack matrixStack, MultiBufferSource bufferIn, int packedLightIn, int packedOverlayIn) {
        this.transformType = transformType;
        super.renderByItem(itemStack, transformType, matrixStack, bufferIn, packedLightIn, packedOverlayIn);
    }


    @Override
    public ResourceLocation getTextureLocation(SherenItem instance) {
        return super.getTextureLocation(instance);
    }

    public void hideBone(String name, boolean hide) {
        if (hide) {
            this.hiddenBones.add(name);
        } else {
            this.hiddenBones.remove(name);
        }
    }

    public ItemDisplayContext getCurrentTransform() {
        return this.transformType;
    }

    public void suppressModification(String name) {
        this.suppressedBones.add(name);
    }

    public void allowModification(String name) {
        this.suppressedBones.remove(name);
    }

    public void setBonePosition(String name, float x, float y, float z) {
        this.queuedBoneSetMovements.put(name, new Vector3f(x, y, z));
    }

    public void addToBoneRotation(String name, float x, float y, float z) {
        this.queuedBoneAddRotations.put(name, new Vector3f(x, y, z));
    }

    public void setBoneRotation(String name, float x, float y, float z) {
        this.queuedBoneSetRotations.put(name, new Vector3f(x, y, z));
    }

    public ItemStack getCurrentItem() {
        return this.currentItemStack;
    }

   
}
