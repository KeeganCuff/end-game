package net.keegancuff.endgame.server;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.List;

public interface ModServerInfo {

    List<RegistryKey<World>> getPhaseWorlds();
    void addPhaseWorld(RegistryKey<World> newWorld);
}
