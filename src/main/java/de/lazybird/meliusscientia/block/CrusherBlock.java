package de.lazybird.meliusscientia.block;

import de.lazybird.meliusscientia.init.ModTileEntityType;
import de.lazybird.meliusscientia.tileentity.CombustionGeneratorTileEntity;
import de.lazybird.meliusscientia.tileentity.CrusherTileEntity;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class CrusherBlock extends Block {

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public CrusherBlock() {
        super(Block.Properties.create(Material.IRON));
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(ACTIVE, Boolean.FALSE));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (player.isCrouching()) return ActionResultType.FAIL;
        if (worldIn.isRemote)
            return ActionResultType.SUCCESS;
        CrusherTileEntity te = (CrusherTileEntity) worldIn.getTileEntity(pos);
        player.sendMessage(new StringTextComponent("Energy: " + te.energyStorage.getEnergyStored() + " Timeleft: " + te.timeLeft));
        NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) worldIn.getTileEntity(pos), pos);
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityType.crusher.get().create();
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing());
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, ACTIVE);
    }

}
