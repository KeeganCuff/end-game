package net.keegancuff.endgame.block.custom;

import net.keegancuff.endgame.item.ModColorProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VariantGemBlock extends Block {
    public static final IntProperty COLOR = IntProperty.of(ModColorProvider.MOD_COLOR_NBT_ID, 0, ModColorProvider.NUM_VARIANT_COLORS-1);

    public boolean ore;

    public VariantGemBlock(Settings settings, boolean isOre) {
        super(settings);
        ore = isOre;
        setDefaultState(getDefaultState().with(COLOR, ModColorProvider.DEFAULT_GEM_COLOR));
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(COLOR);
    }


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
    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, boolean dropExperience) {
        super.onStacksDropped(state, world, pos, stack, dropExperience);
        if (dropExperience && ore) {
            this.dropExperienceWhenMined(world, pos, stack, UniformIntProvider.create(3, 7));
        }
    }
}
