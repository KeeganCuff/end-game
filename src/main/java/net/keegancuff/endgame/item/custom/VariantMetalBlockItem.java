package net.keegancuff.endgame.item.custom;

import net.keegancuff.endgame.block.custom.VariantMetalBlock;
import net.keegancuff.endgame.item.ModColorProvider;
import net.keegancuff.endgame.screen.VariantGemScreen;
import net.keegancuff.endgame.screen.VariantMetalScreen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VariantMetalBlockItem extends BlockItem {
    public VariantMetalBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context){
        BlockState state = super.getPlacementState(context);
        if (state == null) return null;
        NbtCompound nbt = context.getStack().getOrCreateNbt();
        state = state.with(VariantMetalBlock.COLOR, nbt.getInt(ModColorProvider.MOD_COLOR_NBT_ID));
        return state;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context){
        if (stack.getNbt() == null){
            tooltip.add(Text.literal("Color: "));
        } else {
            tooltip.add(Text.literal("Color: " + stack.getNbt().getInt(ModColorProvider.MOD_COLOR_NBT_ID)));
        }
    }

    @Override
    public ItemStack getDefaultStack(){
        ItemStack stack = super.getDefaultStack();
        stack.getOrCreateNbt().putInt(ModColorProvider.MOD_COLOR_NBT_ID, ModColorProvider.DEFAULT_METAL_COLOR);
        return stack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        TypedActionResult<ItemStack> result = super.use(world, user, hand);
        if (!result.getResult().isAccepted() && user.isCreative() && world.isClient()){
            MinecraftClient.getInstance().setScreen(new VariantMetalScreen(user.getStackInHand(hand)));
            return TypedActionResult.consume(user.getStackInHand(hand));
        }
        return result;
    }
}
