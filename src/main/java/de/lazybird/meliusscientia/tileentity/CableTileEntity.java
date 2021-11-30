package de.lazybird.meliusscientia.tileentity;

import de.lazybird.meliusscientia.init.ModTileEntityType;
import de.lazybird.meliusscientia.util.storage.CableEnergyStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class CableTileEntity extends TileEntity implements ITickableTileEntity {

    private final int maxTransfer = 100;
    private final CableEnergyStorage energyStorage = new CableEnergyStorage(1000, maxTransfer);
    private final LazyOptional<CableEnergyStorage> energyInstance = LazyOptional.of(() -> energyStorage);
    public final ArrayList<Direction> currentConnections = new ArrayList<>();

    public CableTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public CableTileEntity() {
        this(ModTileEntityType.cable.get());
    }

    @Override
    public void tick() {
        if (!world.isRemote) {
            refreshConnections();

            for (Direction side : currentConnections) {
                TileEntity te = world.getTileEntity(pos.offset(side));
                if (te != null) {
                    LazyOptional<IEnergyStorage> optionalStorage = te.getCapability(CapabilityEnergy.ENERGY, side.getOpposite());
                    if (optionalStorage.isPresent()) {
                        IEnergyStorage storage = optionalStorage.orElseThrow(() -> new RuntimeException("energy storage doesn't exist"));
                        if (storage.canReceive() && energyStorage.canExtract()) {
                            int maxTransfer = Math.min(energyStorage.extractEnergy(this.maxTransfer, true), storage.receiveEnergy(this.maxTransfer, true));
                            energyStorage.extractEnergy(maxTransfer, false);
                            storage.receiveEnergy(maxTransfer, false);
                        }
                    }
                }
            }
        }
    }

    public void refreshConnections() {
        currentConnections.clear();
        for (Direction side : Direction.values()) {
            TileEntity te = world.getTileEntity(getPos().offset(side));
            if (te != null && te.getCapability(CapabilityEnergy.ENERGY, side.getOpposite()).isPresent()) {
                currentConnections.add(side);
            }
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return this.energyInstance.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("energy", energyStorage.getEnergyStored());
        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        energyStorage.setEnergy(compound.getInt("energy"));
    }
}
