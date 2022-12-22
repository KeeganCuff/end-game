package net.keegancuff.endgame.world.dimension.tools;

import net.keegancuff.endgame.EndGame;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;

public class DimensionFactory {
    public static final RegistryKey<DimensionType> TYPE_KEY = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            new Identifier(EndGame.MOD_ID, "phase_dimensions"));

    public static DimensionOptions createDimension(MinecraftServer server, RegistryKey<DimensionOptions> key)
    {
        EndGame.LOGGER.info("DimensionFactory: Creating Dimension...");
        return new DimensionOptions(getDimensionTypeEntry(server),
                new NoiseChunkGenerator(server.getOverworld().getChunkManager().getChunkGenerator().getBiomeSource(),
                        ((NoiseChunkGenerator)server.getOverworld().getChunkManager().getChunkGenerator()).getSettings())); // NoiseChunkGenerator to be replaced with custom mod generator?
    }

    public static RegistryEntry.Reference<DimensionType> getDimensionTypeEntry(MinecraftServer server)
    {
        return server.getCombinedDynamicRegistries() // get dynamic registries
                .getCombinedRegistryManager().get(RegistryKeys.DIMENSION_TYPE).getReadOnlyWrapper().getOrThrow(TYPE_KEY); // bruh
    }
}
