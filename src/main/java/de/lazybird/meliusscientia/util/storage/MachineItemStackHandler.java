package de.lazybird.meliusscientia.util.storage;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.Set;

public class MachineItemStackHandler extends ItemStackHandler {

    private Set<Item> validItems;

    public MachineItemStackHandler(Set<Item> validItems) {
        this.validItems = validItems;
    }

    public MachineItemStackHandler() {
        this(null);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (validItems == null)
            return true;
        return validItems.contains(stack.getItem());
    }

    public CompoundNBT serializeNBT(String prefix)
    {
        ListNBT nbtTagList = new ListNBT();
        for (int i = 0; i < stacks.size(); i++)
        {
            if (!stacks.get(i).isEmpty())
            {
                CompoundNBT itemTag = new CompoundNBT();
                itemTag.putInt(prefix + "Slot", i);
                stacks.get(i).write(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        CompoundNBT nbt = new CompoundNBT();
        nbt.put(prefix + "Items", nbtTagList);
        nbt.putInt(prefix + "Size", stacks.size());
        return nbt;
    }

    public void deserializeNBT(CompoundNBT nbt, String prefix)
    {
        setSize(nbt.contains(prefix + "Size", Constants.NBT.TAG_INT) ? nbt.getInt(prefix + "Size") : stacks.size());
        ListNBT tagList = nbt.getList(prefix + "Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++)
        {
            CompoundNBT itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt(prefix + "Slot");

            if (slot >= 0 && slot < stacks.size())
            {
                stacks.set(slot, ItemStack.read(itemTags));
            }
        }
        onLoad();
    }

}
