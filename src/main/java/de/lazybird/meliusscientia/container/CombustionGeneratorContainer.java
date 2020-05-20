package de.lazybird.meliusscientia.container;

import de.lazybird.meliusscientia.init.ModBlock;
import de.lazybird.meliusscientia.init.ModContainerTypes;
import de.lazybird.meliusscientia.tileentity.CombustionGeneratorTileEntity;
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
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Objects;

public class CombustionGeneratorContainer extends Container {

    private final IWorldPosCallable canInteractWithCallable;
    private final CombustionGeneratorTileEntity tileEntity;
    private final ItemStackHandler itemStorage;

    public CombustionGeneratorContainer(int windowId, PlayerInventory playerInventory, CombustionGeneratorTileEntity tileEntity) {
        super(ModContainerTypes.combustion_generator.get(), windowId);
        this.itemStorage = tileEntity.itemStorage;
        this.tileEntity = tileEntity;
        this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        int startX = 44;
		int startY = 36;
		int slotSizePlus2 = 18;

		// Combustion generator Inventory
        addSlot(new SlotItemHandler(itemStorage, 0, startX, startY));

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

    public CombustionGeneratorContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(windowId, playerInventory, getTileEntity(playerInventory, data));
	}

	@Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, ModBlock.combustion_generator.get());
    }

    public static CombustionGeneratorTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
		Objects.requireNonNull(data, "data cannot be null");
		final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
		if (tileAtPos instanceof CombustionGeneratorTileEntity) {
			return (CombustionGeneratorTileEntity) tileAtPos;
		}
		throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index < 36) {
				if (!this.mergeItemStack(itemstack1, 36, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 36, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}
		return itemstack;
    }
    @OnlyIn(Dist.CLIENT)
    public int getEnergyProgressionScaled(){
    	int max = this.tileEntity.energyStorage.getMaxEnergyStored();
    	int stored = tileEntity.energyStorage.getEnergyStored();
		return Math.round(stored * (50.0F / max));
	}

	@OnlyIn(Dist.CLIENT)
	public int getTimeProgressionScaled(){
		int time = this.tileEntity.timeleft;
		int fullTime = tileEntity.consumedFuelTime;
		return Math.round(time * (24.0F / fullTime));
	}

	public CombustionGeneratorTileEntity getTileEntity(){
    	return tileEntity;
	}
}
