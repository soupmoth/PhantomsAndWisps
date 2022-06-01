package net.scord.wisps.world.feature;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.scord.wisps.WispsMod;
import net.scord.wisps.block.ModBlocks;

import java.util.List;

public class WispsConfiguredFeatures {



    public static final List<OreFeatureConfig.Target> OVERWORLD_STARBLE = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES,
                    ModBlocks.MOONSLATE.getDefaultState()),
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
                    ModBlocks.STARBLE.getDefaultState()));

    public static final RegistryEntry<ConfiguredFeature<ReplaceBlobsFeatureConfig, ?>> STARBLE_BLOB =
            ConfiguredFeatures.register("starble_blob", Feature.NETHERRACK_REPLACE_BLOBS,
                    new ReplaceBlobsFeatureConfig(Blocks.STONE.getDefaultState(), ModBlocks.STARBLE.getDefaultState(),
                            UniformIntProvider.create(5, 8)));

    public static final RegistryEntry<ConfiguredFeature<ReplaceBlobsFeatureConfig, ?>> MOONSLATE_BLOB =
            ConfiguredFeatures.register("moonslate_blob", Feature.NETHERRACK_REPLACE_BLOBS,
                    new ReplaceBlobsFeatureConfig(Blocks.DEEPSLATE.getDefaultState(), ModBlocks.MOONSLATE.getDefaultState(),
                            UniformIntProvider.create(5, 8)));

    //MOSS_PATCH = ConfiguredFeatures.register("moss_patch", Feature.VEGETATION_PATCH, new VegetationPatchFeatureConfig(BlockTags.MOSS_REPLACEABLE, BlockStateProvider.of(Blocks.MOSS_BLOCK), PlacedFeatures.createEntry(MOSS_VEGETATION, new PlacementModifier[0]), VerticalSurfaceType.FLOOR, ConstantIntProvider.create(1), 0.0F, 5, 0.8F, UniformIntProvider.create(4, 7), 0.3F));

    public static final RegistryEntry<ConfiguredFeature<SimpleBlockFeatureConfig, ?>> STAR_PLANT =
            ConfiguredFeatures.register("star_plant", Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.NOVA_LICHEN)));

    public static final RegistryEntry<ConfiguredFeature<VegetationPatchFeatureConfig, ?>> NEBULA_MOSS_CEILING =
            ConfiguredFeatures.register("nebula_moss_patch_ceiling", Feature.VEGETATION_PATCH,
                    new VegetationPatchFeatureConfig(BlockTags.MOSS_REPLACEABLE, BlockStateProvider.of(ModBlocks.NEBULA_MOSS),
                            PlacedFeatures.createEntry(STAR_PLANT, new PlacementModifier[0]), VerticalSurfaceType.CEILING,
                            UniformIntProvider.create(2, 4), 0.0F, 5, 0.1F,
                            UniformIntProvider.create(4, 8), 0.3F));


    public static final RegistryEntry<ConfiguredFeature<VegetationPatchFeatureConfig, ?>> NEBULA_MOSS_PATCH_BONEMEAL =
            ConfiguredFeatures.register("nebula_moss_patch_bonemeal", Feature.VEGETATION_PATCH,
                    new VegetationPatchFeatureConfig(BlockTags.MOSS_REPLACEABLE, BlockStateProvider.of(ModBlocks.NEBULA_MOSS),
                            PlacedFeatures.createEntry(STAR_PLANT, new PlacementModifier[0]), VerticalSurfaceType.FLOOR,
                            ConstantIntProvider.create(1), 0.0F, 5, 0.6F,
                            UniformIntProvider.create(1, 2), 0.75F));







    public static void registerConfiguredFeatures() {
        System.out.println("Registering ModConfiguredFeatures for " + WispsMod.MOD_ID);
    }
}
