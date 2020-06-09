package de.lazybird.meliusscientia.block;

import de.lazybird.meliusscientia.tileentity.CombustionGeneratorTileEntity;
import de.lazybird.meliusscientia.tileentity.CrusherTileEntity;
import de.lazybird.meliusscientia.tileentity.MachineTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.ComparatorTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class MachineBlock<T extends MachineTileEntity> extends Block {

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    private RegistryObject<TileEntityType<T>> tileEntityTypeRegistryObject;

    public MachineBlock(RegistryObject<TileEntityType<T>> tileEntityTypeRegistryObject) {
        super(Block.Properties.create(Material.IRON));
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.SOUTH).with(ACTIVE, Boolean.FALSE));
        this.tileEntityTypeRegistryObject = tileEntityTypeRegistryObject;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (player.isCrouching()) return ActionResultType.FAIL;
        if (worldIn.isRemote)
            return ActionResultType.SUCCESS;
        MachineTileEntity tileEntity = (MachineTileEntity)worldIn.getTileEntity(pos);
        player.sendMessage(new StringTextComponent("Energy: " + ((CrusherTileEntity)tileEntity).energyStorage.getEnergyStored() + " Timeleft: " + ((CrusherTileEntity)tileEntity).timeLeft));
        NetworkHooks.openGui((ServerPlayerEntity) player, tileEntity, pos);
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return tileEntityTypeRegistryObject.get().create();
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, ACTIVE);
    }
}
