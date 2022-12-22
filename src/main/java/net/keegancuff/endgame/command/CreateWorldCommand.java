package net.keegancuff.endgame.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.world.dimension.ModDimensions;
import net.keegancuff.endgame.world.dimension.tools.DimensionFactory;
import net.keegancuff.endgame.world.dimension.tools.DynamicDimensionManager;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import org.apache.logging.log4j.core.jmx.Server;

public class CreateWorldCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment){
        dispatcher.register(CommandManager.literal("dimension").requires(source -> source.hasPermissionLevel(3))
                .then(CommandManager.argument("entity", EntityArgumentType.player())
                .then(CommandManager.argument("id", IntegerArgumentType.integer()).executes((CreateWorldCommand::run)))));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Integer id = context.getArgument("id", Integer.class);
        Entity target = context.getArgument("entity", EntitySelector.class).getPlayer(context.getSource());
        EndGame.LOGGER.info("CreateWorldCommand: Running dimension command!");
        if (!(target instanceof ServerPlayerEntity)){
            return -1;
        }
        DynamicDimensionManager.sendPlayerToDimension((ServerPlayerEntity)target ,
                DynamicDimensionManager.getOrCreateWorld(context.getSource().getServer(),
                        RegistryKey.of(RegistryKeys.WORLD, new Identifier(EndGame.MOD_ID, id.toString())),
                        DimensionFactory::createDimension),
                target.getPos());
        return id;
    }
}
