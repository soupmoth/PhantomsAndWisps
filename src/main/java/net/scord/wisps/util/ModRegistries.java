package net.scord.wisps.util;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.scord.wisps.WispsMod;
import net.scord.wisps.entity.ModEntities;
import net.scord.wisps.entity.custom.WispEntity;

public class ModRegistries {

    public static void registerModStuffs() {
        WispsMod.LOGGER.info("Trying Attributes...");
        registerAttributes();
    }

    private static void registerAttributes() {

        FabricDefaultAttributeRegistry.register(ModEntities.WISP, WispEntity.setAttributes());
        WispsMod.LOGGER.info("Registered Attributes");
    }


}
