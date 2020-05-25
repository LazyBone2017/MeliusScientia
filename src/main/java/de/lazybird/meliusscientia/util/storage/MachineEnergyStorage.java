package de.lazybird.meliusscientia.util.storage;

import net.minecraftforge.energy.EnergyStorage;

public class MachineEnergyStorage extends EnergyStorage {

    public MachineEnergyStorage(int capacity, int maxInsert) {
        super(capacity, maxInsert, 0);
    }

    public boolean decrementEnergy(int energy) {
        if (this.energy - energy < 0)
            return false;
        this.energy = this.energy - energy;
        return true;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

}
