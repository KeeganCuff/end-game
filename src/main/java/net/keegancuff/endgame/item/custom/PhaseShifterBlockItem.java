package net.keegancuff.endgame.item.custom;

import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PhaseShifterBlockItem extends BlockItem {
    public PhaseShifterBlockItem(Block block, Settings settings) {
        super(block, settings);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context){
        if(!Screen.hasShiftDown()){
            tooltip.add(Text.literal("Press shift for more info!"));
        } else{
            tooltip.add(Text.literal("Acts as a portal to different phase dimensions."));
            tooltip.add(Text.literal("Right click on the block to see what dimension it takes you to."));
            tooltip.add(Text.literal("Crouch while standing on the it to shift to that world."));
        }
    }
}
