package net.scord.wisps.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.MossBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.UndergroundConfiguredFeatures;
import net.scord.wisps.world.feature.WispsConfiguredFeatures;

import java.util.Random;

public class NebulaMossBlock extends MossBlock {


    public NebulaMossBlock(Settings settings) {
        super(settings);
    }

    /*
    Override of Moss Block to instead use the custom moss behaviour defined in WispConfiguredFeatures
     */
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        ((ConfiguredFeature) WispsConfiguredFeatures.NEBULA_MOSS_PATCH_BONEMEAL.value()).generate(world, world.getChunkManager().getChunkGenerator(), random, pos.up());
    }
}
