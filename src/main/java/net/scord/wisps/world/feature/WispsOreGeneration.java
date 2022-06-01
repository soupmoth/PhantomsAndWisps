package net.scord.wisps.world.feature;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.scord.wisps.world.biomes.ModBiomes;
import net.scord.wisps.world.biomes.WispsBiomes;

@Deprecated
public class WispsOreGeneration {
    public static void generateOres() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BEACH),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.STARBLE_PLACED.getKey().get());
    }
}
