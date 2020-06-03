package de.lazybird.meliusscientia.container;

import de.lazybird.meliusscientia.init.ModBlock;
import de.lazybird.meliusscientia.init.ModContainerTypes;
import de.lazybird.meliusscientia.tileentity.CombustionGeneratorTileEntity;
import de.lazybird.meliusscientia.tileentity.CrusherTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

import java.util.Objects;

public class CrusherContainer extends Container {

    private final IWorldPosCallable canInteractWithCallable;
    private final CrusherTileEntity tileentity;

    public CrusherContainer(int windowId, final PlayerInventory playerInventory, final CrusherTileEntity tileEntity) {
        super(ModContainerTypes.crusher.get(), windowId);
        this.tileentity = tileEntity;
        this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
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
}
