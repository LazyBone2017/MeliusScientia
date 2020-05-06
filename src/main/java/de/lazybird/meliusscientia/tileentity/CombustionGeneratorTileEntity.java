package de.lazybird.meliusscientia.tileentity;

import de.lazybird.meliusscientia.init.ModTileEntityType;
import de.lazybird.meliusscientia.util.storage.GeneratorEnergyStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class CombustionGeneratorTileEntity extends TileEntity implements ITickableTileEntity {

    public final GeneratorEnergyStorage energyStorage = new GeneratorEnergyStorage(10000, 20);
    public final ItemStackHandler itemStorage = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return ForgeHooks.getBurnTime(stack) > 0;
        }
    };
    public final LazyOptional<GeneratorEnergyStorage> energyInstance = LazyOptional.of(() -> energyStorage);
    public final LazyOptional<ItemStackHandler> itemInstance = LazyOptional.of(() -> itemStorage);
    public int timeleft = 0;

    public CombustionGeneratorTileEntity(final TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public CombustionGeneratorTileEntity() {
        this(ModTileEntityType.combustion_generator.get());
    }

    @Override
    public void tick() {
        // Output energy
        for (Direction facing : Direction.values()) {
            BlockPos checking = this.pos.offset(facing);
            TileEntity checkingTile = this.world.getTileEntity(checking);
            if (checkingTile != null) {
                checkingTile.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite()).ifPresent(storage -> {
                    int energy = storage.receiveEnergy(Math.min(this.energyStorage.getEnergyStored(), 20), false);
                    if (energy > 0) {
                        this.energyStorage.extractEnergy(energy, false);
                    }
                });
            }
        }

        // Burn fuel
        if (timeleft <= 0) { // Not active, start burning if possible
            if (!itemStorage.getStackInSlot(0).isEmpty()) {
                timeleft = ForgeHooks.getBurnTime(itemStorage.getStackInSlot(0));
                itemStorage.getStackInSlot(0).setCount(itemStorage.getStackInSlot(0).getCount() - 1);
            }
        } else { // Generate energy
            if (energyStorage.incrementEnergy(5))
                timeleft--;
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> cap, Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return this.energyInstance.cast();
        } else if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.itemInstance.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		compound.putInt("energy", energyStorage.getEnergyStored());
		compound.merge(itemStorage.serializeNBT());
		return compound;
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		energyStorage.setEnergy(compound.getInt("energy"));
		itemStorage.deserializeNBT(compound);
	}

}
