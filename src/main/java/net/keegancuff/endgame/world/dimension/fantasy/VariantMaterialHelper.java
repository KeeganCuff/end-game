package net.keegancuff.endgame.world.dimension.fantasy;

import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.block.ModBlocks;
import net.keegancuff.endgame.block.custom.VariantMetalBlock;
import net.keegancuff.endgame.item.ModColorProvider;
import net.keegancuff.endgame.item.custom.VariantMetalBlockItem;
import net.keegancuff.endgame.item.custom.VariantMetalItem;
import net.keegancuff.endgame.server.VariantMetalData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.StructureWorldAccess;

public class VariantMaterialHelper {

    public static final String COLOR_HEADER = "Color: ";

    public static void initRandomMaterials(ServerWorld world){
        VariantMetalData.createRandomData(world);
    }

    public static BlockState getMetalData(StructureWorldAccess world, Block block) {
        //EndGame.LOGGER.info("VariantMetalHelper: grabbing metal data");
        ServerWorld serverWorld;
        if (world instanceof ChunkRegion region){
            serverWorld = region.toServerWorld();
        } else {
            serverWorld = (ServerWorld) world;
        }
        VariantMetalData metal = VariantMetalData.getWorldMetalData(serverWorld);
        BlockState state = block.getDefaultState().with(VariantMetalBlock.COLOR, metal.color);
        return state;
    }

    public static String getArtificerText(ItemStack stack){
        if (!(stack.getItem() instanceof VariantMetalItem || stack.getItem() instanceof VariantMetalBlockItem)) {
            EndGame.LOGGER.info("Item not a variant material");
            return "";
        }
        int color = stack.getOrCreateNbt().getInt(ModColorProvider.MOD_COLOR_NBT_ID);
        return (COLOR_HEADER + color + "\n");
    }

    public static ItemStack textToNbt(ItemStack stack, String text){
        if (!(stack.getItem() instanceof VariantMetalItem || stack.getItem() instanceof VariantMetalBlockItem))
            return stack.copy();
        ItemStack stack2 = stack.copy();
        try {
            stack2.getOrCreateNbt().putInt(ModColorProvider.MOD_COLOR_NBT_ID, Integer.parseInt(text.substring(COLOR_HEADER.length(), text.contains("\n") ? text.indexOf('\n') : text.length())));
        } catch (Exception ignored){}
        return stack2;
    }
}
