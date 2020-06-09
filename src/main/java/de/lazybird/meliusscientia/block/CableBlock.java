package de.lazybird.meliusscientia.block;

import de.lazybird.meliusscientia.init.ModTileEntityType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;
import java.util.Objects;

public class CableBlock extends Block {

    public CableBlock() {
        super(Block.Properties.create(Material.CLAY));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityType.cable.get().create();
    }

    // This appears to be the wrong method. It only calls when a neighbor is removed
    @Override
    public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
        if (!world.isRemote()) {
            System.out.println("Update");
            Direction d = null;
            if (neighbor.equals(pos.north())) d = Direction.NORTH;
            else if (neighbor.equals(pos.east())) d = Direction.EAST;
            else if (neighbor.equals(pos.south())) d = Direction.SOUTH;
            else if (neighbor.equals(pos.west())) d = Direction.WEST;
            else if (neighbor.equals(pos.up())) d = Direction.UP;
            else if (neighbor.equals(pos.down())) d = Direction.DOWN;
            if (d == null) return;
            Direction finalD = d;
            TileEntity te = world.getTileEntity(neighbor);
            if (te == null) {
                String name = world.getBlockState(neighbor).getBlock().getNameTextComponent().getString();
                System.out.println("Neighbour " + name + " is null :(");
                return;
            }
            te.getCapability(CapabilityEnergy.ENERGY, d).ifPresent(storage -> {
                System.out.println(finalD.toString() + " Connected");
            });
        }
    }
}
