package net.keegancuff.endgame.block.custom;

import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.item.ModColorProvider;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.awt.*;
import java.util.List;

public class VariantMetalBlock extends Block {

    //Controls what color the ore of the block appears as.
    public static final IntProperty COLOR = IntProperty.of(ModColorProvider.MOD_COLOR_NBT_ID, 0, ModColorProvider.NUM_VARIANT_COLORS-1);

    public VariantMetalBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(COLOR, ModColorProvider.DEFAULT_METAL_COLOR));
    }


    /*
     * Cycles the color of the block for testing.
     * TODO: Remove this once testing is done
     */
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//        if (!world.isClient && hand == Hand.MAIN_HAND){
//            world.setBlockState(pos, state.cycle(COLOR));
//        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    /*
     * Registers the blockstates to the builder
     * TODO: Add other tags
     */
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(COLOR);
    }


    /*
     * Copies the data in the block state to the item that is dropped.
     * TODO: Add other tags
     */
    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        List<ItemStack> stacks = super.getDroppedStacks(state, builder);
        for (ItemStack stack : stacks){
            stack.getOrCreateNbt().putInt(ModColorProvider.MOD_COLOR_NBT_ID, (state.get(COLOR)));
        }
        return stacks;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state){
        ItemStack stack = super.getPickStack(world, pos, state);
        stack.getOrCreateNbt().putInt(ModColorProvider.MOD_COLOR_NBT_ID, state.get(COLOR));

        //Can add future information here

        return stack;
    }

}
