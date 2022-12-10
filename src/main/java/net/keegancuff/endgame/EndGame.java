package net.keegancuff.endgame;

import net.fabricmc.api.ModInitializer;
import net.keegancuff.endgame.block.ModBlocks;
import net.keegancuff.endgame.item.ModItemGroup;
import net.keegancuff.endgame.item.ModItems;
import net.keegancuff.endgame.world.feature.ModOreConfiguredFeatures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndGame implements ModInitializer {
	public static final String MOD_ID = "endgame";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override

	public void onInitialize() {
		ModOreConfiguredFeatures.registerConfiguredFeatures();

		ModItemGroup.registerModGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

	}
}
