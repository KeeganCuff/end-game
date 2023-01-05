package net.keegancuff.endgame;

import net.fabricmc.api.ModInitializer;
import net.keegancuff.endgame.block.ModBlocks;
import net.keegancuff.endgame.block.entity.ModBlockEntities;
import net.keegancuff.endgame.item.ModColorProvider;
import net.keegancuff.endgame.item.ModItemGroup;
import net.keegancuff.endgame.item.ModItems;
import net.keegancuff.endgame.screen.ModScreenHandlers;
import net.keegancuff.endgame.util.ModRegistries;
import net.keegancuff.endgame.world.dimension.ModDimensions;
import net.keegancuff.endgame.world.feature.ModConfiguredFeatures;
import net.keegancuff.endgame.world.feature.ModPlacedFeatures;
import net.keegancuff.endgame.world.gen.ModOreGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndGame implements ModInitializer {
	public static final String MOD_ID = "endgame";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override

	public void onInitialize() {
		ModConfiguredFeatures.registerConfiguredFeatures();
		ModPlacedFeatures.registerPlacedFeatures();
		ModOreGeneration.registerOreGeneration();

		ModItemGroup.registerModGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();

		ModColorProvider.registerColorProviders();

		ModDimensions.register();

		ModRegistries.registerModStuffs();
	}
}
