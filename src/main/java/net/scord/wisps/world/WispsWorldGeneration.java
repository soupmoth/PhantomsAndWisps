package net.scord.wisps.world;

import net.scord.wisps.world.feature.WispsOreGeneration;

@Deprecated
public class WispsWorldGeneration {

    public static void generate() {

        //underground decoration step
        WispsOreGeneration.generateOres();
    }
}
