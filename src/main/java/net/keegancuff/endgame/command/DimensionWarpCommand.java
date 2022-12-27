package net.keegancuff.endgame.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.mixin.MinecraftServerAccessor;
import net.keegancuff.endgame.server.ModServerInfo;
import net.keegancuff.endgame.server.PersistentWorldState;
import net.keegancuff.endgame.world.dimension.fantasy.FantasyDimensionHelper;
import net.keegancuff.endgame.world.dimension.tools.DimensionFactory;
import net.keegancuff.endgame.world.dimension.tools.DynamicDimensionManager;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import xyz.nucleoid.fantasy.Fantasy;
import xyz.nucleoid.fantasy.RuntimeWorldHandle;

public class DimensionWarpCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment){
        dispatcher.register(CommandManager.literal("dimwarp").requires(source -> source.hasPermissionLevel(3))
                .then(CommandManager.argument("entity", EntityArgumentType.player())
                .then(CommandManager.argument("id", IntegerArgumentType.integer()).executes((DimensionWarpCommand::run)))));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Integer id = context.getArgument("id", Integer.class);
        Entity target = context.getArgument("entity", EntitySelector.class).getPlayer(context.getSource());
        //EndGame.LOGGER.info("CreateWorldCommand: Running dimension command!");
        if (!(target instanceof ServerPlayerEntity playerEntity)){
            return -1;
        }
        MinecraftServer server = context.getSource().getServer();
        Fantasy fantasy = Fantasy.get(server);
        RuntimeWorldHandle worldHandle = fantasy.getOrOpenPersistentWorld(new Identifier(EndGame.MOD_ID, "phase_dimension_" + id),
                FantasyDimensionHelper.getPhaseConfig(server));
        FantasyDimensionHelper.addNewId(id, server);
        playerEntity.teleport(worldHandle.asWorld(), playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), playerEntity.getYaw(), playerEntity.getPitch());
        return id;
    }
}
