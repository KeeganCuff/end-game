package net.keegancuff.endgame.screen;

import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.item.custom.VariantMetalBlockItem;
import net.keegancuff.endgame.item.custom.VariantMetalItem;
import net.keegancuff.endgame.world.dimension.fantasy.VariantMaterialHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.report.ReporterEnvironment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ArtificerStationScreenHandler extends ScreenHandler {

    public final Inventory inventory;
    public final CraftingResultInventory result = new CraftingResultInventory();

    private String info;
    private boolean displayingInfo;
    private final ScreenHandlerContext context;
    private final PlayerEntity player;

    public ArtificerStationScreenHandler(int syncId, PlayerInventory inventory){
        this(syncId, inventory, new SimpleInventory(2), ScreenHandlerContext.EMPTY);
    }

    public ArtificerStationScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, ScreenHandlerContext context) {
        super(ModScreenHandlers.ARTIFICER_STATION_SCREEN_HANDLER, syncId);
        checkSize(inventory, 2);
        this.inventory = inventory;

        this.inventory.onOpen(playerInventory.player);
        displayingInfo = false;
        this.inventory.markDirty();
        this.context = context;
        this.player = playerInventory.player;

        this.addSlot(new Slot(this.inventory, 0, 12, 15){
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof VariantMetalItem || stack.getItem() instanceof VariantMetalBlockItem;
            }
        });
        this.addSlot(new Slot(this.inventory, 1, 12, 59));
        this.addSlot(new Slot(result, 0, 152, 37){
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                super.onTakeItem(player, stack);
                inventory.setStack(0, ItemStack.EMPTY);
            }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        showInfo();
    }


    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()){
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)){
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()){
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public void close(PlayerEntity player) {
        inventory.markDirty();
        super.close(player);
    }

    private void showInfo() {
        if (hasVariantMaterial() && !showingInfo()){
            ItemStack variant = inventory.getStack(0);
            info = VariantMaterialHelper.getArtificerText(variant);
            displayingInfo = true;
        } else if (!hasVariantMaterial()){
            info = "";
            displayingInfo = false;
        }

    }

    @Override
    public void onContentChanged(Inventory inventory) {
        showInfo();
        //tryVariantChange1(this, this.player, inventory, result);
        super.onContentChanged(inventory);
        tryVariantChange2();
    }

    private static void tryVariantChange1(ArtificerStationScreenHandler handler, PlayerEntity player, Inventory inventory, CraftingResultInventory resultInventory) {
        String info = handler.getInfo();
        if (!handler.hasVariantMaterial() || Objects.equals(info, "") || info == null) { // OR String is invalid
            return;
        }
        ItemStack variant = VariantMaterialHelper.textToNbt(inventory.getStack(0), info);
        resultInventory.setStack(0, variant);
        handler.setPreviousTrackedSlot(0, variant);
        if (player instanceof ServerPlayerEntity spe){
            spe.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, variant));
        }
    }

    private void tryVariantChange2(){
        if (!hasVariantMaterial() || Objects.equals(info, "") || info == null) { // OR String is invalid
            return;
        }
        ItemStack variant = VariantMaterialHelper.textToNbt(inventory.getStack(0), info);
        result.setStack(0, variant);
    }

    public boolean hasVariantMaterial(){
        Item item = inventory.getStack(0).getItem();
        return item instanceof VariantMetalItem || item instanceof VariantMetalBlockItem;
    }

    public String getInfo() {
        if (info == null){
            info = "";
        }
        return info;
    }

    public void setInfo(String info){
        this.info = info;
    }

    public boolean showingInfo(){
        return displayingInfo;
    }



    private void addPlayerInventory(PlayerInventory playerInventory){
        for (int i = 0; i< 3; i++){
            for (int l = 0; l < 9; l++){
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory){
        for (int i = 0; i < 9; i++){
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }
}
