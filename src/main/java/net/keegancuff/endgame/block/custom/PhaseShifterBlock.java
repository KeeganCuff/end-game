package net.keegancuff.endgame.block.custom;

import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.world.dimension.ModDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PhaseShifterBlock extends Block {
    public PhaseShifterBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient() && hand.equals(Hand.MAIN_HAND)){
            player.sendMessage(Text.literal("Takes you to: " + (world.getRegistryKey() == ModDimensions.MIRROR_DIMENSION ? "Overworld" : "Mirror world")));
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        System.out.println("PhaseShifterBlock: Stepped on me! - " + (world.isClient()? "client" : "server"));
        if (world.isClient() || !entity.isSneaking()){
            return;
        }
        EndGame.LOGGER.info("PhaseShifterBlock: now we're in the " + (world.isClient()? "client" : "server"));
        if (world instanceof ServerWorld && world.getRegistryKey() != World.END && world.getRegistryKey() != World.NETHER && !entity.hasVehicle() && !entity.hasPassengers() && entity.canUsePortals()) {
            RegistryKey<World> registryKey = world.getRegistryKey() == ModDimensions.MIRROR_DIMENSION ? World.OVERWORLD : ModDimensions.MIRROR_DIMENSION;
            ServerWorld serverWorld = ((ServerWorld)world).getServer().getWorld(registryKey);
            if (serverWorld == null) {
                return;
            }
            entity.moveToWorld(serverWorld);
        }


        super.onSteppedOn(world, pos, state, entity);
    }

}
