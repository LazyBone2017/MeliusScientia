package de.lazybird.meliusscientia.util.storage;

import net.minecraftforge.energy.EnergyStorage;

public class CableEnergyStorage extends EnergyStorage {

    public CableEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
