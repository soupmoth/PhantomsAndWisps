package net.scord.wisps;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.scord.wisps.block.ModBlocks;
import net.scord.wisps.entity.ModEntities;
import net.scord.wisps.entity.client.renderers.WispRenderer;
import net.scord.wisps.world.biomes.ModBiomes;

public class WispsClientMod implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        WispsMod.LOGGER.info("Registering Entity Renderers");
        EntityRendererRegistry.register(ModEntities.WISP, WispRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.NOVA_LICHEN, RenderLayer.getCutout());
    }
}
