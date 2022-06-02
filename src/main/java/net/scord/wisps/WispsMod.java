package net.scord.wisps;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.scord.wisps.block.ModBlocks;
import net.scord.wisps.effect.ModEffects;
import net.scord.wisps.item.ModItems;
import net.scord.wisps.util.ModLootTables;
import net.scord.wisps.util.ModRegistries;
import net.scord.wisps.world.biomes.ModBiomes;
import net.scord.wisps.world.biomes.WispsRegion;
import net.scord.wisps.world.feature.ModPlacedFeatures;
import net.scord.wisps.world.feature.WispsConfiguredFeatures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class WispsMod implements ModInitializer, TerraBlenderApi {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "wisps";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final int HUNT_DURATION = 30 * 20;
	public static final int HUNT_DURATION_MAX = 60 * 20;



	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		WispsConfiguredFeatures.registerConfiguredFeatures();



		WispsMod.LOGGER.info("WispsMod Initialising");
		//General behaviour

		//Hunt amplifier increase code. The chance of Hunt increasing decreases with every level of Hunt.
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
			if (entity instanceof PlayerEntity && killedEntity instanceof PhantomEntity) {
				StatusEffectInstance hunt = ((PlayerEntity) entity).getStatusEffect(ModEffects.HUNT);

				//if we already have a status effect...
				if (hunt != null) {

					int huntLevel = hunt.getAmplifier();

					if ((world.random.nextInt(huntLevel+1)) == 0) {
						huntLevel++;
					}
					//upgrade the status effect/refresh it
					int duration = Math.min(HUNT_DURATION_MAX, hunt.getDuration() + HUNT_DURATION);
					((PlayerEntity) entity).removeStatusEffect(ModEffects.HUNT);
					((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(ModEffects.HUNT, duration, huntLevel));
				}
				else {
					((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(ModEffects.HUNT, HUNT_DURATION, 0));
				}



			}

		});


		//Most important to register first
		ModRegistries.registerModStuffs();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEffects.registerEffects();


		ModLootTables.modifyLootTables();
		WispsMod.LOGGER.info("Registering Biome details");
		ModBiomes.registerBiomes();


	}


	@Override
	public void onTerraBlenderInitialized() {
		Regions.register(new WispsRegion(new Identifier(MOD_ID, "astral_river"), 2));

	}
}
