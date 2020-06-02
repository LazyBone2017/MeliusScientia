package de.lazybird.meliusscientia.tileentity;

import de.lazybird.meliusscientia.MeliusScientia;
import de.lazybird.meliusscientia.container.CrusherContainer;
import de.lazybird.meliusscientia.init.ModBlock;
import de.lazybird.meliusscientia.init.ModItem;
import de.lazybird.meliusscientia.init.ModTileEntityType;
import de.lazybird.meliusscientia.init.RecipeInit;
import de.lazybird.meliusscientia.util.storage.MachineEnergyStorage;
import de.lazybird.meliusscientia.util.storage.MachineItemStackHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.HashMap;

import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class CrusherTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    public final MachineEnergyStorage energyStorage = new MachineEnergyStorage(1000, 20);
    public final MachineItemStackHandler inputStorage = new MachineItemStackHandler(RecipeInit.crusherRecipeHandler.inputs());
    public final MachineItemStackHandler outputStorage = new MachineItemStackHandler();
    private final LazyOptional<MachineEnergyStorage> energyStorageLazyOptional = LazyOptional.of(() -> energyStorage);
    private final LazyOptional<MachineItemStackHandler> inputStorageLazyOptional = LazyOptional.of(() -> inputStorage);
    private final LazyOptional<MachineItemStackHandler> outputStorageLazyOptional = LazyOptional.of(() -> outputStorage);
    public int timeLeft = 0;
    private Item currentInput;

    public CrusherTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public CrusherTileEntity() {
        this(ModTileEntityType.crusher.get());
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (ENERGY.equals(cap)) {
            return energyStorageLazyOptional.cast();
        } else if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(cap)) {
            if (side == Direction.DOWN) {
                return outputStorageLazyOptional.cast();
            }
            return inputStorageLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container." + MeliusScientia.MODID + ".crusher");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
        return new CrusherContainer(id, inventory, this);
    }

    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        super.write(compound);
        compound.putInt("energy", energyStorage.getEnergyStored());
        compound.merge(inputStorage.serializeNBT("input"));
        compound.merge(outputStorage.serializeNBT("output"));
        return compound;
    }

    @Override
    public void read(@Nonnull CompoundNBT compound) {
        super.read(compound);
        energyStorage.setEnergy(compound.getInt("energy"));
        inputStorage.deserializeNBT(compound, "input");
        outputStorage.deserializeNBT(compound, "output");
    }

    @Override
    public void tick() {
        if (!world.isRemote()) {
            if (timeLeft <= 0) {
                if (RecipeInit.crusherRecipeHandler.getResult(inputStorage.getStackInSlot(0).getItem()) != null) {
                    currentInput = inputStorage.getStackInSlot(0).getItem();
                    inputStorage.extractItem(0, 1, false);
                    timeLeft = 200;
                }
            } else {
                if (energyStorage.decrementEnergy(20)) {
                    timeLeft--;
                    if (timeLeft == 0) {
                        outputStorage.insertItem(0, new ItemStack(RecipeInit.crusherRecipeHandler.getResult(currentInput)), false);
                    }
                }
            }
        }
    }
}
