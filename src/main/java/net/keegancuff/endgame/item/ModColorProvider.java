package net.keegancuff.endgame.item;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.keegancuff.endgame.block.ModBlocks;
import net.keegancuff.endgame.block.custom.VariantMetalBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

public class ModColorProvider {

    public static final String MOD_COLOR_NBT_ID = "endgame_color";
    public static final int NUM_VARIANT_COLORS = 32;
    private static final int[] COLORS = {
            0x4287f5, 0x774ef2, 0xfbadb6, 0x9c90b2, 0xa75a40, 0xb3f7c6, 0xebd52f, 0xd4024e,
            0x39921d, 0xc38d33, 0xd73aa0, 0x665b81, 0x3fc5a8, 0x794747, 0x0e1e31, 0x210228,
            0xc075fb, 0x15c65c, 0x4e6d65, 0x0c7c67, 0x194e5e, 0x5f265f, 0xcff79f, 0x0a3e94,
            0xc9c9c9, 0x954c6f, 0x490413, 0x1a2b24, 0xcec0ff, 0xd7771d, 0xef9675, 0xdb2f29
    };


    public static void registerColorProviders(){
        ColorProviderRegistry.ITEM.register(ModColorProvider::getVariantItemColorProvider,
                ModItems.VARIANT_METAL_INGOT,
                ModItems.RAW_VARIANT_METAL,
                ModBlocks.VARIANT_METAL_ORE,
                ModBlocks.VARIANT_METAL_BLOCK);
        ColorProviderRegistry.BLOCK.register(ModColorProvider::getVariantBlockColorProvider,
                ModBlocks.VARIANT_METAL_ORE,
                ModBlocks.VARIANT_METAL_BLOCK);
    }

    private static int getVariantBlockColorProvider(BlockState state, BlockRenderView view, BlockPos pos, int tintIndex) {
        return COLORS[state.get(VariantMetalBlock.COLOR)];
    }

    private static int getVariantItemColorProvider(ItemStack stack, int tintIndex) {
        return COLORS[stack.getOrCreateNbt().getInt(MOD_COLOR_NBT_ID)];
    }

}
