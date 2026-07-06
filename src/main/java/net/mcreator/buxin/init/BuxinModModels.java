
package net.mcreator.buxin.init;

import net.mcreator.buxin.client.model.*;
import net.mcreator.buxin.client.model.dragon.ModelBabyEnderDragon;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class BuxinModModels {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModelScoutH.LAYER_LOCATION, ModelScoutH::createBodyLayer);
		event.registerLayerDefinition(ModelGrave_head.LAYER_LOCATION, ModelGrave_head::createBodyLayer);
		event.registerLayerDefinition(Model大甲.LAYER_LOCATION, Model大甲::createBodyLayer);
		event.registerLayerDefinition(Model村骑头盔.LAYER_LOCATION, Model村骑头盔::createBodyLayer);
		event.registerLayerDefinition(Model村头盔.LAYER_LOCATION, Model村头盔::createBodyLayer);
		event.registerLayerDefinition(Modeltrident.LAYER_LOCATION, Modeltrident::createBodyLayer);
		event.registerLayerDefinition(Model格雷.LAYER_LOCATION, Model格雷::createBodyLayer);
		event.registerLayerDefinition(Model铁傀儡护臂.LAYER_LOCATION, Model铁傀儡护臂::createBodyLayer);
		event.registerLayerDefinition(Model大.LAYER_LOCATION, Model大::createBodyLayer);
		event.registerLayerDefinition(ModelustomModel.LAYER_LOCATION, ModelustomModel::createBodyLayer);
		event.registerLayerDefinition(ModelCustomModel.LAYER_LOCATION, ModelCustomModel::createBodyLayer);
		event.registerLayerDefinition(Modelzaievillagers.LAYER_LOCATION, Modelzaievillagers::createBodyLayer);
		event.registerLayerDefinition(Model盔影牢大盔甲.LAYER_LOCATION, Model盔影牢大盔甲::createBodyLayer);
		event.registerLayerDefinition(Model村民头套.LAYER_LOCATION, Model村民头套::createBodyLayer);
		event.registerLayerDefinition(Modeliron_golem.LAYER_LOCATION, Modeliron_golem::createBodyLayer);
		event.registerLayerDefinition(Modelgrave.LAYER_LOCATION, Modelgrave::createBodyLayer);
		event.registerLayerDefinition(Model大盔甲.LAYER_LOCATION, Model大盔甲::createBodyLayer);
		event.registerLayerDefinition(Modelbig_obs.LAYER_LOCATION, Modelbig_obs::createBodyLayer);
		event.registerLayerDefinition(Modelcape.LAYER_LOCATION, Modelcape::createBodyLayer);
		event.registerLayerDefinition(Modelherobrine_weapon.LAYER_LOCATION, Modelherobrine_weapon::createBodyLayer);
		event.registerLayerDefinition(Model格雷夫双剑胸甲.LAYER_LOCATION, Model格雷夫双剑胸甲::createBodyLayer);
		event.registerLayerDefinition(ModelBabyEnderDragon.LAYER_LOCATION, ModelBabyEnderDragon::createBodyLayer);
		event.registerLayerDefinition(ModelSnakeBlade.LAYER_LOCATION, ModelSnakeBlade::createBodyLayer);
		event.registerLayerDefinition(ModelSnakeBladeFragment.LAYER_LOCATION, ModelSnakeBladeFragment::createBodyLayer);
		event.registerLayerDefinition(ModelPrisoner.LAYER_LOCATION, ModelPrisoner::createBodyLayer);
	}
}
