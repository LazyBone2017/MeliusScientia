package de.lazybird.meliusscientia.container;

import de.lazybird.meliusscientia.init.ModBlock;
import de.lazybird.meliusscientia.init.ModContainerTypes;
import de.lazybird.meliusscientia.tileentity.CrusherTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.IWorldPosCallable;

public class CrusherContainer extends Container {

    private final IWorldPosCallable canInteractWithCallable;

    public CrusherContainer(int windowId, final PlayerInventory playerInventory, final CrusherTileEntity tileEntity) {
        super(ModContainerTypes.combustion_generator.get(), windowId);
        this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, ModBlock.combustion_generator.get());
    }

}
