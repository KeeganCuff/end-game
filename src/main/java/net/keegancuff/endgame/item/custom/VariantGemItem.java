package net.keegancuff.endgame.item.custom;

import net.keegancuff.endgame.item.ModColorProvider;
import net.keegancuff.endgame.screen.VariantGemScreen;
import net.keegancuff.endgame.screen.VariantMetalScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientPlayerEntity;
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

public class VariantGemItem extends Item {

    public VariantGemItem(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.isSneaking() && user.isCreative() && world.isClient()){
            MinecraftClient.getInstance().setScreen(new VariantGemScreen(user.getStackInHand(hand)));
        }
        return super.use(world, user, hand);
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
        stack.getOrCreateNbt().putInt(ModColorProvider.MOD_COLOR_NBT_ID, ModColorProvider.DEFAULT_GEM_COLOR);
        return stack;
    }
}
