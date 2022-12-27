package net.keegancuff.endgame.mixin;

import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.server.ModServerInfo;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin implements ModServerInfo {
    private List<RegistryKey<World>> phaseWorlds = new ArrayList<>();

    @Override
    public List<RegistryKey<World>> getPhaseWorlds() {
        EndGame.LOGGER.info("MinecraftServerMixin: " + phaseWorlds.toString());
        return phaseWorlds;
    }

    @Override
    public void addPhaseWorld(RegistryKey<World> newWorld) {
        phaseWorlds.add(newWorld);
        EndGame.LOGGER.info("MinecraftServerMixin: " + phaseWorlds.toString());
    }
}
