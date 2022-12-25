package net.keegancuff.endgame.world.dimension.fantasy;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import xyz.nucleoid.fantasy.Fantasy;
import xyz.nucleoid.fantasy.RuntimeWorldConfig;
import xyz.nucleoid.fantasy.util.VoidChunkGenerator;

public class FantasyDimensionHelper {
    public static RuntimeWorldConfig getPhaseConfig(MinecraftServer server){
        return new RuntimeWorldConfig()
                //.setDimensionType(DimensionTypes.OVERWORLD)
                .setDifficulty(server.getSaveProperties().getDifficulty())
                .setGenerator(server.getOverworld().getChunkManager().getChunkGenerator())
                //.setGenerator(new VoidChunkGenerator(server.getRegistryManager().get(RegistryKeys.BIOME).getEntry(0).get()))
                .setSeed(server.getOverworld().getSeed());
    }
}
