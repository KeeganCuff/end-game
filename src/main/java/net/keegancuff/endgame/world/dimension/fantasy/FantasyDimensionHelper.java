package net.keegancuff.endgame.world.dimension.fantasy;

import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.server.PersistentWorldState;
import net.keegancuff.endgame.server.VariantMetalData;
import net.keegancuff.endgame.world.dimension.ModDimensions;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.*;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import xyz.nucleoid.fantasy.Fantasy;
import xyz.nucleoid.fantasy.RuntimeWorldConfig;
import xyz.nucleoid.fantasy.RuntimeWorldHandle;
import xyz.nucleoid.fantasy.util.VoidChunkGenerator;

import java.io.IOException;
import java.util.Arrays;

public class FantasyDimensionHelper {

    public static final String PHASE_DIMENSION_KEY_HEADER = "phase_dimension_";

    /**
     * Called to create a new variant dimension
     * @param server the server to add the dimension to
     * @param id the id of the dimension - used to create the world folder and refresh the dimension on server restart
     * @return the new world
     */
    public static ServerWorld newWorld(MinecraftServer server, int id){
        Fantasy fantasy = Fantasy.get(server);
        ServerWorld world = fantasy.getOrOpenPersistentWorld(new Identifier(EndGame.MOD_ID, PHASE_DIMENSION_KEY_HEADER + id),
                getPhaseConfig(server)).asWorld();
        addNewId(id, server);
        VariantMaterialHelper.initRandomMaterials(world);
        return world;
    }

    /**
     * Helper method creates the default config for variant dimensions
     * @param server the server that the variant dimension is being added to or is found
     * @return the world config for the variant dimension
     */
    public static RuntimeWorldConfig getPhaseConfig(MinecraftServer server){
        return new RuntimeWorldConfig()
                .setDimensionType(ModDimensions.PHASE_DIMENSION_TYPE)
                .setDifficulty(server.getSaveProperties().getDifficulty())
                //.setGenerator(new NoiseChunkGenerator())
                .setGenerator((server.getCombinedDynamicRegistries().getCombinedRegistryManager().get(RegistryKeys.DIMENSION).get(new Identifier(EndGame.MOD_ID, "phase_dimensions"))).chunkGenerator())
                //.setGenerator(new VoidChunkGenerator(server.getRegistryManager().get(RegistryKeys.BIOME).getEntry(0).get()))
                .setSeed(server.getOverworld().getSeed());
    }

    /**
     * Called on server start.
     * Reloads all variant dimensions using ids in the server's PersistentWorldState
     * @param server the server being refreshed
     */
    public static void refreshPersistentWorlds(MinecraftServer server) {
        EndGame.LOGGER.info("FantasyDimensionHelper: refreshing persistent worlds");
        Fantasy fantasy = Fantasy.get(server);
        int[] ids = PersistentWorldState.getServerState(server).phaseWorlds;
        if (Arrays.equals(ids, new int[]{-1})) return;
        for (int id : ids) {
            EndGame.LOGGER.info("loading phase world with id: " + id);
            fantasy.getOrOpenPersistentWorld(new Identifier(EndGame.MOD_ID, PHASE_DIMENSION_KEY_HEADER + id), getPhaseConfig(server));
        }
    }

    /**
     * Adds the id of a new variant dimension to the server data.
     * necessary for the server to remember what dimensions to refresh on server start.
     * @param id id being added
     * @param server server that is gaining the data.
     */
    public static void addNewId(int id, MinecraftServer server){
        int[] old = PersistentWorldState.getServerState(server).phaseWorlds;
        if (Arrays.equals(old, new int[]{-1})){
            PersistentWorldState.getServerState(server).phaseWorlds = new int[]{id};
            return;
        }
        for (int i : old){
            if (i == id){
                return;
            }
        }
        int[] out = new int[old.length+1];
        System.arraycopy(old, 0, out, 0, old.length);
        out[old.length] = id;
        PersistentWorldState.getServerState(server).phaseWorlds = out;
    }

    /**
     * Given a variant dimension, returns the id
     * Given other world, returns -1
     * @param world the world with desired id
     * @return the id of the world
     */
    public static int toId(ServerWorld world){
        if (world.getRegistryKey().getValue().toString().contains(PHASE_DIMENSION_KEY_HEADER)){
            return Integer.parseInt(world.getRegistryKey().getValue().toString().substring(24));
        }
        return -1;
    }

}
