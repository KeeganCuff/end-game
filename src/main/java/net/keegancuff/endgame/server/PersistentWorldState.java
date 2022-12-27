package net.keegancuff.endgame.server;

import net.keegancuff.endgame.EndGame;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class PersistentWorldState extends PersistentState {

    public static final String PHASE_WORLDS_NBT_ID = "endgame.phaseWorlds";

    public int[] phaseWorlds;

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putIntArray(PHASE_WORLDS_NBT_ID, phaseWorlds);
        return nbt;
    }

    public static PersistentWorldState createFromNbt(NbtCompound tag){
        PersistentWorldState worldState = new PersistentWorldState();
        worldState.phaseWorlds = tag.getIntArray(PHASE_WORLDS_NBT_ID);
        return worldState;
    }

    public static PersistentWorldState getServerState(MinecraftServer server){
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        PersistentWorldState worldState = persistentStateManager.getOrCreate(
                PersistentWorldState::createFromNbt,
                PersistentWorldState::new,
                EndGame.MOD_ID);

        worldState.markDirty();

        return worldState;
    }
}
