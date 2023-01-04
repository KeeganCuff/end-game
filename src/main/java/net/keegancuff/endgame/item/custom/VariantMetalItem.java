package net.keegancuff.endgame.item.custom;

import net.keegancuff.endgame.item.ModColorProvider;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VariantMetalItem extends Item {

    public VariantMetalItem(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && hand == Hand.MAIN_HAND){
            cycleColor(user.getStackInHand(hand));

        }
        return super.use(world, user, hand);
    }

    private void cycleColor(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        int oldColor = nbt.getInt(ModColorProvider.MOD_COLOR_NBT_ID);
        if (oldColor >= ModColorProvider.NUM_VARIANT_COLORS-1){
            nbt.putInt(ModColorProvider.MOD_COLOR_NBT_ID, 0);
        } else {
            nbt.putInt(ModColorProvider.MOD_COLOR_NBT_ID, oldColor + 1);
        }

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
        stack.getOrCreateNbt().putInt(ModColorProvider.MOD_COLOR_NBT_ID, 0);
        return stack;
    }
}
