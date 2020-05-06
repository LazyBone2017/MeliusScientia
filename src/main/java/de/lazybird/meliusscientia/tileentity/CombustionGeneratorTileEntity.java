package de.lazybird.meliusscientia.tileentity;

import de.lazybird.meliusscientia.init.ModTileEntityType;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

public class CombustionGeneratorTileEntity extends TileEntity implements ITickableTileEntity {

    public final EnergyStorage energyStorage = new EnergyStorage(1000, 0, 20, 100);
    public final LazyOptional<EnergyStorage> instance = LazyOptional.of(() -> energyStorage);

    public CombustionGeneratorTileEntity(final TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public CombustionGeneratorTileEntity() {
        this(ModTileEntityType.combustion_generator.get());
    }

    @Override
    public void tick() {
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
    }

    @Override
    public <T> LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> cap, Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return this.instance.cast();
        }
        return super.getCapability(cap, side);
    }

}
