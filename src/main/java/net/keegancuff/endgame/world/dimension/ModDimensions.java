package net.keegancuff.endgame.world.dimension;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.world.dimension.fantasy.FantasyDimensionHelper;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModDimensions {
    public static final RegistryKey<World> MIRROR_DIMENSION = RegistryKey.of(RegistryKeys.WORLD,
            new Identifier(EndGame.MOD_ID, "mirror"));
    public static final RegistryKey<DimensionType> MIRROR_TYPE_KEY = RegistryKey.of(
            RegistryKeys.DIMENSION_TYPE, MIRROR_DIMENSION.getValue());

    public static final RegistryKey<DimensionType> PHASE_DIMENSION_TYPE = RegistryKey.of(
            RegistryKeys.DIMENSION_TYPE, new Identifier(EndGame.MOD_ID, "phase_dimensions"));

    public static void register(){
        ServerLifecycleEvents.SERVER_STARTED.register(FantasyDimensionHelper::refreshPersistentWorlds);
        EndGame.LOGGER.debug("Registering ModDimensions for " + EndGame.MOD_ID);
    }
}
