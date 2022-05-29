package net.scord.wisps.entity.client.models;

import net.minecraft.util.Identifier;
import net.scord.wisps.WispsMod;
import net.scord.wisps.entity.custom.WispEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WispModel extends AnimatedGeoModel<WispEntity> {

    @Override
    public Identifier getModelLocation(WispEntity object) {
        return new Identifier(WispsMod.MOD_ID, "geo/wisp.geo.json");
    }

    @Override
    public Identifier getTextureLocation(WispEntity object) {
        return new Identifier(WispsMod.MOD_ID, "textures/entity/wisp/wisp.png");
    }

    @Override
    public Identifier getAnimationFileLocation(WispEntity animatable) {
        return new Identifier(WispsMod.MOD_ID, "animations/wisp.animation.json");
    }


}
