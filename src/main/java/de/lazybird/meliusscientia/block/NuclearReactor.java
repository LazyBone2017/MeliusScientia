package de.lazybird.meliusscientia.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class NuclearReactor extends Block {

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    private static final VoxelShape SHAPE = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(0, 1, 0, 16, 16, 1),
            Block.makeCuboidShape(0, 1, 1, 1, 16, 15),
            Block.makeCuboidShape(0, 1, 15, 16, 16, 16),
            Block.makeCuboidShape(15, 1, 1, 16, 16, 15),
            Block.makeCuboidShape(1, 15, 1, 5, 16, 15),
            Block.makeCuboidShape(11, 15, 1, 15, 16, 15),
            Block.makeCuboidShape(5, 15, 11, 11, 16, 15),
            Block.makeCuboidShape(5, 15, 1, 11, 16, 5),
            Block.makeCuboidShape(6, 1, 6, 10, 15, 10)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    public NuclearReactor() {
        super(Block.Properties.from(Blocks.IRON_BLOCK).lightValue(15));
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(ACTIVE, Boolean.FALSE));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
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

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote())worldIn.setBlockState(pos, state.with(ACTIVE, !state.get(ACTIVE)));
        return ActionResultType.SUCCESS;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.get(ACTIVE) ? 15 : 0;
    }
}
