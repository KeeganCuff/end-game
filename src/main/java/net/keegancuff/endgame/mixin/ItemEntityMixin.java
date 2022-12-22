package net.keegancuff.endgame.mixin;

import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.block.ModBlocks;
import net.keegancuff.endgame.item.ModArmorMaterials;
import net.keegancuff.endgame.item.ModItems;
import net.keegancuff.endgame.item.ModToolMaterials;
import net.keegancuff.endgame.item.custom.ModFloatingItem;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin extends EntityMixin{
    @Shadow
    private static final TrackedData<ItemStack> STACK = DataTracker.registerData(ItemEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    @Shadow
    public ItemStack getStack() {
        return this.getDataTracker().get(STACK);
    }


    @Inject(at = @At("TAIL"), method = "setStack")
    private void createEntity(ItemStack stack, CallbackInfo info){
        Item item = stack.getItem();
        if (item instanceof ModFloatingItem || item instanceof ArmorItem && ((ArmorItem)item).getMaterial() == ModArmorMaterials.ENDERIUM ||
        item instanceof ToolItem && ((ToolItem)item).getMaterial() == ModToolMaterials.ENDERIUM || item instanceof BlockItem &&
                (((BlockItem)item).getBlock() == ModBlocks.ENDERIUM_BLOCK || ((BlockItem)item).getBlock() == ModBlocks.ENDERIUM_ORE)){
            setNoGravity(true);
        }
    }


}
