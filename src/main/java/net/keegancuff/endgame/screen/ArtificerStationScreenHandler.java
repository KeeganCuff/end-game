package net.keegancuff.endgame.screen;

import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.block.entity.CreativeArtificerStationBlockEntity;
import net.keegancuff.endgame.item.ModItems;
import net.keegancuff.endgame.item.custom.VariantMetalBlockItem;
import net.keegancuff.endgame.item.custom.VariantMetalItem;
import net.keegancuff.endgame.world.dimension.fantasy.VariantMaterialHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

public class ArtificerStationScreenHandler extends ScreenHandler {

    public final Inventory inventory = new SimpleInventory(3) {
        @Override
        public void markDirty() {
            super.markDirty();
            ArtificerStationScreenHandler.this.onContentChanged(this);
        }
    };
    private final Inventory blockInventory;

    private String info;
    private boolean displayingInfo;

    public ArtificerStationScreenHandler(int syncId, PlayerInventory inventory){
        this(syncId, inventory, new SimpleInventory(3));
    }

    public ArtificerStationScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.ARTIFICER_STATION_SCREEN_HANDLER, syncId);
        checkSize(inventory, 3);
        blockInventory = inventory;
        inventoryCopy(this.inventory, inventory);
        this.inventory.onOpen(playerInventory.player);
        displayingInfo = false;
        this.inventory.markDirty();

        this.addSlot(new Slot(this.inventory, 0, 12, 15){
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof VariantMetalItem || stack.getItem() instanceof VariantMetalBlockItem;
            }
        });
        this.addSlot(new Slot(this.inventory, 1, 12, 59));
        this.addSlot(new Slot(this.inventory, 2, 152, 37){
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    private void inventoryCopy(Inventory to, Inventory from) {
        EndGame.LOGGER.info("Copying inventory: " + from.getClass().toString() + " to " + to.getClass().toString());
        for (int i = 0; i<to.size(); i++){
            to.setStack(i, from.getStack(i));
            EndGame.LOGGER.info("Stack " + i + ": " + to.getStack(i));
        }
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
        inventoryCopy(blockInventory, inventory);
        blockInventory.markDirty();
        super.close(player);
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

    public boolean hasVariantMaterial(){
        Item item = inventory.getStack(0).getItem();
        return item instanceof VariantMetalItem || item instanceof VariantMetalBlockItem;
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        if (id != 356)
            return super.onButtonClick(player, id);
        ItemStack variant = getStacks().get(0);
        String variantInfo = info;
        blockInventory.setStack(0, ItemStack.EMPTY);
        blockInventory.setStack(2, VariantMaterialHelper.textToNbt(variant, variantInfo));
        inventoryCopy(inventory, blockInventory);
        return true;
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        EndGame.LOGGER.info("Content Changed!");
        if (inventory == this.inventory){
            showInfo(inventory);
        }
        //inventoryCopy(blockInventory, inventory);
        super.onContentChanged(inventory);
    }

    private void showInfo(Inventory inventory) {
        if (hasVariantMaterial() && !showingInfo()){
            ItemStack variant = inventory.getStack(0);
            info = (VariantMaterialHelper.getArtificerText(variant));
            displayingInfo = true;
        } else if (!hasVariantMaterial()){
            info = "";
            displayingInfo = false;
        }
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
}
