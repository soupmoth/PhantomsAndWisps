package net.scord.wisps;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.scord.wisps.entity.ModEntities;
import net.scord.wisps.entity.client.renderers.WispRenderer;

public class WispsClientMod implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.WISP, WispRenderer::new);
    }
}
