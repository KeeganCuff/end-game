package net.keegancuff.endgame.world.dimension.fantasy;

import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.server.ModServerInfo;
import net.keegancuff.endgame.server.PersistentWorldState;
import net.keegancuff.endgame.world.dimension.ModDimensions;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import xyz.nucleoid.fantasy.Fantasy;
import xyz.nucleoid.fantasy.RuntimeWorldConfig;
import xyz.nucleoid.fantasy.util.VoidChunkGenerator;

import java.io.IOException;

public class FantasyDimensionHelper {

    public static final String PHASE_DIMENSION_KEY_HEADER = "phase_dimension_";

    public static RuntimeWorldConfig getPhaseConfig(MinecraftServer server){
        return new RuntimeWorldConfig()
                .setDimensionType(ModDimensions.PHASE_DIMENSION_TYPE)
                .setDifficulty(server.getSaveProperties().getDifficulty())
                //.setGenerator(new NoiseChunkGenerator())
                .setGenerator((server.getCombinedDynamicRegistries().getCombinedRegistryManager().get(RegistryKeys.DIMENSION).get(new Identifier(EndGame.MOD_ID, "phase_dimensions"))).chunkGenerator())
                //.setGenerator(new VoidChunkGenerator(server.getRegistryManager().get(RegistryKeys.BIOME).getEntry(0).get()))
                .setSeed(server.getOverworld().getSeed());
    }

    public static void refreshPersistentWorlds(MinecraftServer server) {
        EndGame.LOGGER.info("FantasyDimensionHelper: refreshing persistent worlds");
        Fantasy fantasy = Fantasy.get(server);
        int[] ids = PersistentWorldState.getServerState(server).phaseWorlds;
        if (ids == null) return;
        for (int id : ids) {
            EndGame.LOGGER.info("loading phase world with id: " + id);
            fantasy.getOrOpenPersistentWorld(new Identifier(EndGame.MOD_ID, PHASE_DIMENSION_KEY_HEADER + id), getPhaseConfig(server));
        }
    }

    public static void addNewId(int id, MinecraftServer server){
        int[] old = PersistentWorldState.getServerState(server).phaseWorlds;
        if (old == null){
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
}
