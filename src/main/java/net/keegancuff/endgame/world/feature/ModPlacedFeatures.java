package net.keegancuff.endgame.world.feature;

import net.keegancuff.endgame.EndGame;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> ORE_ENDERIUM_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(EndGame.MOD_ID, "ore_enderium"));

    public static void registerPlacedFeatures() {
        EndGame.LOGGER.debug("Registering the ModPlacedFeatures for " + EndGame.MOD_ID);
    }
}
