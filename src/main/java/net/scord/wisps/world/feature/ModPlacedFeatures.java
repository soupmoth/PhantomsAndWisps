package net.scord.wisps.world.feature;

import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.UndergroundConfiguredFeatures;
import net.minecraft.world.gen.placementmodifier.*;

public class ModPlacedFeatures {


    public static final RegistryEntry<PlacedFeature> STARBLE_PLACED = PlacedFeatures.register("starble_placed",
            WispsConfiguredFeatures.STARBLE_BLOB, WispsOreFeatures.modifiersWithCount(75,
                    HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(45), YOffset.belowTop(64))));

    public static final RegistryEntry<PlacedFeature> MOONSLATE_PLACED = PlacedFeatures.register("moonslate_placed",
            WispsConfiguredFeatures.MOONSLATE_BLOB, WispsOreFeatures.modifiersWithCount(75,
                    HeightRangePlacementModifier.uniform(YOffset.aboveBottom(1), YOffset.aboveBottom(72))));

    public static final RegistryEntry<PlacedFeature> ASTRAL_RIVER_CEILING_FOILAGE = PlacedFeatures.register("astral_river_ceiling_vegetation",
        WispsConfiguredFeatures.NEBULA_MOSS_CEILING, new PlacementModifier[]{ CountPlacementModifier.of(150),
            SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE,
            EnvironmentScanPlacementModifier.of(Direction.UP, BlockPredicate.solid(), BlockPredicate.IS_AIR, 8),
            RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(-1)), BiomePlacementModifier.of()});


}
