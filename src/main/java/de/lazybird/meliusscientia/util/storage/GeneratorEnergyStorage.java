package de.lazybird.meliusscientia.util.storage;

import net.minecraftforge.energy.EnergyStorage;

public class GeneratorEnergyStorage extends EnergyStorage {

    public GeneratorEnergyStorage(int capacity, int maxExtract) {
        super(capacity, 0, maxExtract);
    }

    public boolean incrementEnergy(int value) {
        if (energy == capacity)
            return false;
        energy = Math.min(capacity, energy + value);
        return true;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

}
