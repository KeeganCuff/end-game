package net.keegancuff.endgame.world.dimension;

import net.keegancuff.endgame.EndGame;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class ModDimensions {
    public static final RegistryKey<World> MIRROR_DIMENSION = RegistryKey.of(RegistryKeys.WORLD,
            new Identifier(EndGame.MOD_ID, "mirror"));
    public static final RegistryKey<DimensionType> MIRROR_TYPE_KEY = RegistryKey.of(
            RegistryKeys.DIMENSION_TYPE, MIRROR_DIMENSION.getValue());

    public static void register(){
        EndGame.LOGGER.debug("Registering ModDimensions for " + EndGame.MOD_ID);
    }
}
