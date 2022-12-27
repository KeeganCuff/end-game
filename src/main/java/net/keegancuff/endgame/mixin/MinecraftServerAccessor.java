package net.keegancuff.endgame.mixin;

import com.google.common.eventbus.AllowConcurrentEvents;
import net.fabricmc.api.EnvironmentInterfaces;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.concurrent.Executor;

@Mixin(MinecraftServer.class)
public interface MinecraftServerAccessor {
    @Accessor
    WorldGenerationProgressListenerFactory getWorldGenerationProgressListenerFactory();
    @Accessor
    Executor getWorkerExecutor();
    @Accessor
    LevelStorage.Session getSession();
    @Accessor
    Map<RegistryKey<World>, ServerWorld> getWorlds();

    @Accessor("worlds")
    public void setWorlds(Map<RegistryKey<World>, ServerWorld> worlds);



    /*@Shadow
    private WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory;
    @Shadow
    private Executor workerExecutor;
    @Shadow
    protected LevelStorage.Session session;

    public WorldGenerationProgressListenerFactory getWorldGenerationProgressListenerFactory(){
        return worldGenerationProgressListenerFactory;
    }

    public Executor getWorkerExecutor() {
        return workerExecutor;
    }

    public LevelStorage.Session getSession() {
        return session;
    }*/
}
