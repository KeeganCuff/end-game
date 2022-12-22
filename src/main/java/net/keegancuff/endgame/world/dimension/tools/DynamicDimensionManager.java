package net.keegancuff.endgame.world.dimension.tools;


import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.impl.dimension.FabricDimensionInternals;
import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.mixin.MinecraftServerAccessor;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.ServerDynamicRegistryType;
import net.minecraft.resource.DataConfiguration;
import net.minecraft.server.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorderListener;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGenerators;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.level.LevelProperties;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.apache.logging.log4j.core.jmx.Server;
import org.joml.Vector3d;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

//Based on Code made by Commoble
public class DynamicDimensionManager {

    public static void sendPlayerToDimension(ServerPlayerEntity serverPlayer, ServerWorld targetWorld, Vec3d targetVec)
    {
        // ensure destination chunk is loaded before we put the player in it
        EndGame.LOGGER.info("DynamicDimensionManager: Attempting to send player to dimension...");
        targetWorld.getChunk(new BlockPos(targetVec));
        //FabricDimensions.teleport(serverPlayer, targetWorld,
        //        new TeleportTarget(targetVec, serverPlayer.getVelocity(), serverPlayer.getYaw(), serverPlayer.getPitch()));
        serverPlayer.teleport(targetWorld, targetVec.getX(), targetVec.getY(), targetVec.getZ(), serverPlayer.getYaw(), serverPlayer.getPitch());
    }

    public static ServerWorld getOrCreateWorld(MinecraftServer server, RegistryKey<World> worldKey, BiFunction<MinecraftServer, RegistryKey<DimensionOptions>, DimensionOptions> dimensionFactory){
        Map<RegistryKey<World>, ServerWorld> map = ((MinecraftServerAccessor)server).getWorlds();

        // if the world already exists, return it
        if (map.containsKey(worldKey))
        {
            EndGame.LOGGER.info("DynamicDimensionManager: Dimension found.");
            return map.get(worldKey);
        }
        else
        {
            EndGame.LOGGER.info("DynamicDimensionManager: Dimension not found... attempting to create a new one...");
            // for vanilla worlds, forge fires the world load event *after* the world is put into the map
            // we'll do the same for consistency
            // (this is why we're not just using map::computeIfAbsent)
            ServerWorld newWorld = createAndRegisterWorldAndDimension(server, map, worldKey, dimensionFactory);

            return newWorld;
        }
    }

    /*private static Map<RegistryKey<World>, ServerWorld> getWorldMap(MinecraftServer server) {
        Map<RegistryKey<World>, ServerWorld> map = new HashMap<>();
        Iterator<RegistryKey<World>> worlds = server.getWorldRegistryKeys().iterator();
        Iterator<ServerWorld> serverWorlds = server.getWorlds().iterator();
        while(worlds.hasNext()){
            map.put(worlds.next(), serverWorlds.next());
        }
        return map;
    }*/

    private static ServerWorld createAndRegisterWorldAndDimension(MinecraftServer server, Map<RegistryKey<World>, ServerWorld> map, RegistryKey<World> worldKey, BiFunction<MinecraftServer, RegistryKey<DimensionOptions>, DimensionOptions> dimensionFactory) {

        ServerWorld overworld = server.getOverworld();
        RegistryKey<DimensionOptions> dimensionKey = RegistryKey.of(RegistryKeys.DIMENSION, worldKey.getValue());
        DimensionOptions dimension = dimensionFactory.apply(server, dimensionKey);

        EndGame.LOGGER.info("DynamicDimensionManager: Fetching private fields...");
        // we need to get some private fields from MinecraftServer here
        // chunkStatusListenerFactory
        // backgroundExecutor
        // anvilConverterForAnvilFile
        // the int in create() here is radius of chunks to watch, 11 is what the server uses when it initializes worlds
        WorldGenerationProgressListener chunkListener = ((MinecraftServerAccessor)server).getWorldGenerationProgressListenerFactory().create(11);
        Executor executor = ((MinecraftServerAccessor)server).getWorkerExecutor();
        LevelStorage.Session levelSave = ((MinecraftServerAccessor)server).getSession();

        EndGame.LOGGER.info("DynamicDimensionManager: Registering new dimension...");
        // this is the same order server init creates these worlds:
        // instantiate world, add border listener, add to map, fire world load event
        // (in server init, the dimension is already in the dimension registry,
        // that'll get registered here before the world is instantiated as well)
        SaveProperties serverConfig = server.getSaveProperties();
        GeneratorOptions dimensionGeneratorSettings = serverConfig.getGeneratorOptions();
        // this next line registers the Dimension
        EndGame.LOGGER.info("DynamicDimensionManager: trying to register lmao");

        //server.getCombinedDynamicRegistries().get(ServerDynamicRegistryType.DIMENSIONS).get(RegistryKeys.DIMENSION).createEntry(dimension);
        EndGame.LOGGER.info("DynamicDimensionManager: did it?");
        ServerWorldProperties worldProperties = new LevelProperties(serverConfig.getLevelInfo(), serverConfig.getGeneratorOptions(), LevelProperties.SpecialProperty.NONE, Lifecycle.experimental());
        // now we have everything we need to create the world instance
        ServerWorld newWorld = new ServerWorld(
                server,
                executor,
                levelSave,
                worldProperties,
                worldKey,
                dimension, //contains the DimensionType and ChunkGenerator
                chunkListener,
                serverConfig.isDebugWorld(), // boolean: is-debug-world
                dimensionGeneratorSettings.getSeed(),
                ImmutableList.of(), // "special spawn list"
                // phantoms, raiders, travelling traders, cats are overworld special spawns
                // the dimension loader is hardcoded to initialize preexisting non-overworld worlds with no special spawn lists
                // so this can probably be left empty for best results and spawns should be handled via other means
                false); // "tick time", true for overworld, always false for everything else
        EndGame.LOGGER.info("DynamicDimensionManager: Dimension successfully created!");
        // add world border listener
        overworld.getWorldBorder().addListener(new WorldBorderListener.WorldBorderSyncer(newWorld.getWorldBorder()));

        // register world
        map.put(worldKey, newWorld);

        // update forge's world cache (very important, if we don't do this then the new world won't tick!)
        server.tickWorlds(() -> true);


        //MinecraftForge.EVENT_BUS.post(new WorldEvent.Load(newWorld)); // event isn't cancellable

        return newWorld;
    }
}
