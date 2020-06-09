package de.lazybird.meliusscientia.container;

import de.lazybird.meliusscientia.init.ModBlock;
import de.lazybird.meliusscientia.init.ModContainerTypes;
import de.lazybird.meliusscientia.init.RecipeInit;
import de.lazybird.meliusscientia.tileentity.CombustionGeneratorTileEntity;
import de.lazybird.meliusscientia.tileentity.CrusherTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class CrusherContainer extends Container {

    private final IWorldPosCallable canInteractWithCallable;
    private final CrusherTileEntity tileentity;

    public CrusherContainer(int windowId, final PlayerInventory playerInventory, final CrusherTileEntity tileEntity) {
        super(ModContainerTypes.crusher.get(), windowId);
        this.tileentity = tileEntity;
        this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        addSlot(new SlotItemHandler(tileentity.inputStorage, 0, 44, 36));
        addSlot(new SlotItemHandler(tileentity.outputStorage, 0, 97, 36));

        int startX = 44;
        int startY = 36;
        int slotSizePlus2 = 18;

        // Player Inventory
        int startPlayerInvX = startX - 36;
        int startPlayerInvY = startY + 48;
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startPlayerInvX + (column * slotSizePlus2), startPlayerInvY + (row * slotSizePlus2)));
            }
        }

        // Hotbar
        int hotbarX = startPlayerInvX;
        int hotbarY = startPlayerInvY + 58;
        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, hotbarX + (column * slotSizePlus2), hotbarY));
        }
    }

    public CrusherContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    public static CrusherTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof CrusherTileEntity) {
            return (CrusherTileEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    public CrusherTileEntity getTileEntity(){
        return tileentity;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, ModBlock.crusher.get());
    }



    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        stack = slot.getStack();
        if(index == 0){
            if(!mergeItemStack(stack, 2, 38, false)){
                return ItemStack.EMPTY;
            }
        }
        else if(index == 1){
            if(!mergeItemStack(stack, 2, 38, false)){
                return ItemStack.EMPTY;
            }
        }
        else {
            if(RecipeInit.crusherRecipeHandler.getResult(stack.getItem()) != null){
                if(!mergeItemStack(stack, 0, 1, false))return ItemStack.EMPTY;
            }
        }
        return ItemStack.EMPTY;
    }

    @OnlyIn(Dist.CLIENT)
    public int getEnergyProgressionScaled(){
        int max = this.tileentity.energyStorage.getMaxEnergyStored();
        int stored = tileentity.energyStorage.getEnergyStored();
        return Math.round(stored * (50.0F / max));
    }

    @OnlyIn(Dist.CLIENT)
    public int getTimeProgressionScaled(){
        int timeMax = 200;
        int remaining = tileentity.timeLeft;
        if(remaining == 0)return 23;
        return Math.round(remaining * (23.0F / timeMax));
    }
}
