package net.scord.wisps.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.scord.wisps.WispsMod;
import net.scord.wisps.entity.custom.WispEntity;

public class ModEntities {
    public static final EntityType<WispEntity> WISP = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(WispsMod.MOD_ID, "wisp"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WispEntity::new)
                    .dimensions(EntityDimensions.changing(0.7f, 0.7f)).build());
}
