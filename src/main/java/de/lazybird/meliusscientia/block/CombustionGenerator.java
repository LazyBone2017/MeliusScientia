package de.lazybird.meliusscientia.block;

import de.lazybird.meliusscientia.init.ModTileEntityType;
import de.lazybird.meliusscientia.tileentity.CombustionGeneratorTileEntity;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public class CombustionGenerator extends Block {

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    private static VoxelShape SHAPE_N = Stream.of(
            Block.makeCuboidShape(12, 0, 15, 13, 9, 16),
            Block.makeCuboidShape(12, 0, 0, 13, 9, 1),
            Block.makeCuboidShape(3, 0, 0, 4, 9, 1),
            Block.makeCuboidShape(3, 0, 15, 4, 9, 16),
            Block.makeCuboidShape(4, 8, 0, 12, 9, 1),
            Block.makeCuboidShape(4, 8, 15, 12, 9, 16),
            Block.makeCuboidShape(3, 8, 1, 4, 9, 15),
            Block.makeCuboidShape(12, 8, 1, 13, 9, 15),
            Block.makeCuboidShape(4, 0, 0, 12, 1, 1),
            Block.makeCuboidShape(4, 0, 15, 12, 1, 16),
            Block.makeCuboidShape(3, 0, 1, 4, 1, 15),
            Block.makeCuboidShape(12, 0, 1, 13, 1, 15),
            Block.makeCuboidShape(4, 5, 15, 12, 6, 16),
            Block.makeCuboidShape(4, 5, 0, 12, 6, 1),
            Block.makeCuboidShape(5, 5, 1, 11, 7, 15),
            Block.makeCuboidShape(5, 2, 6, 11, 5, 15),
            Block.makeCuboidShape(6, 2, 5, 10, 5, 6),
            Block.makeCuboidShape(8, 2, 4, 10, 4, 5)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static VoxelShape SHAPE_W = Stream.of(
            Block.makeCuboidShape(0, 0, 12, 1, 9, 13),
            Block.makeCuboidShape(15, 0, 12, 16, 9, 13),
            Block.makeCuboidShape(15, 0, 3, 16, 9, 4),
            Block.makeCuboidShape(0, 0, 3, 1, 9, 4),
            Block.makeCuboidShape(15, 8, 4, 16, 9, 12),
            Block.makeCuboidShape(0, 8, 4, 1, 9, 12),
            Block.makeCuboidShape(1, 8, 3, 15, 9, 4),
            Block.makeCuboidShape(1, 8, 12, 15, 9, 13),
            Block.makeCuboidShape(15, 0, 4, 16, 1, 12),
            Block.makeCuboidShape(0, 0, 4, 1, 1, 12),
            Block.makeCuboidShape(1, 0, 3, 15, 1, 4),
            Block.makeCuboidShape(1, 0, 12, 15, 1, 13),
            Block.makeCuboidShape(0, 5, 4, 1, 6, 12),
            Block.makeCuboidShape(15, 5, 4, 16, 6, 12),
            Block.makeCuboidShape(1, 5, 5, 15, 7, 11),
            Block.makeCuboidShape(1, 2, 5, 10, 5, 11),
            Block.makeCuboidShape(10, 2, 6, 11, 5, 10),
            Block.makeCuboidShape(11, 2, 8, 12, 4, 10)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static VoxelShape SHAPE_S = Stream.of(
            Block.makeCuboidShape(3, 0, 0, 4, 9, 1),
            Block.makeCuboidShape(3, 0, 15, 4, 9, 16),
            Block.makeCuboidShape(12, 0, 15, 13, 9, 16),
            Block.makeCuboidShape(12, 0, 0, 13, 9, 1),
            Block.makeCuboidShape(4, 8, 15, 12, 9, 16),
            Block.makeCuboidShape(4, 8, 0, 12, 9, 1),
            Block.makeCuboidShape(12, 8, 1, 13, 9, 15),
            Block.makeCuboidShape(3, 8, 1, 4, 9, 15),
            Block.makeCuboidShape(4, 0, 15, 12, 1, 16),
            Block.makeCuboidShape(4, 0, 0, 12, 1, 1),
            Block.makeCuboidShape(12, 0, 1, 13, 1, 15),
            Block.makeCuboidShape(3, 0, 1, 4, 1, 15),
            Block.makeCuboidShape(4, 5, 0, 12, 6, 1),
            Block.makeCuboidShape(4, 5, 15, 12, 6, 16),
            Block.makeCuboidShape(5, 5, 1, 11, 7, 15),
            Block.makeCuboidShape(5, 2, 1, 11, 5, 10),
            Block.makeCuboidShape(6, 2, 10, 10, 5, 11),
            Block.makeCuboidShape(6, 2, 11, 8, 4, 12)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static VoxelShape SHAPE_E = Stream.of(
            Block.makeCuboidShape(0, 0, 12, 1, 9, 13),
            Block.makeCuboidShape(15, 0, 12, 16, 9, 13),
            Block.makeCuboidShape(15, 0, 3, 16, 9, 4),
            Block.makeCuboidShape(0, 0, 3, 1, 9, 4),
            Block.makeCuboidShape(15, 8, 4, 16, 9, 12),
            Block.makeCuboidShape(0, 8, 4, 1, 9, 12),
            Block.makeCuboidShape(1, 8, 3, 15, 9, 4),
            Block.makeCuboidShape(1, 8, 12, 15, 9, 13),
            Block.makeCuboidShape(15, 0, 4, 16, 1, 12),
            Block.makeCuboidShape(0, 0, 4, 1, 1, 12),
            Block.makeCuboidShape(1, 0, 3, 15, 1, 4),
            Block.makeCuboidShape(1, 0, 12, 15, 1, 13),
            Block.makeCuboidShape(0, 5, 4, 1, 6, 12),
            Block.makeCuboidShape(15, 5, 4, 16, 6, 12),
            Block.makeCuboidShape(1, 5, 5, 15, 7, 11),
            Block.makeCuboidShape(1, 2, 5, 10, 5, 11),
            Block.makeCuboidShape(10, 2, 6, 11, 5, 10),
            Block.makeCuboidShape(11, 2, 8, 12, 4, 10)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();


    public CombustionGenerator() {
        super(Block.Properties.create(Material.IRON));
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(ACTIVE, Boolean.FALSE));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)){
            case NORTH: {
                    return SHAPE_N;
            }
            case WEST: {
                return SHAPE_W;
            }
            case EAST: {
                return SHAPE_E;
            }
            case SOUTH: {
                return SHAPE_S;
            }
            default: return SHAPE_N;
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (player.isCrouching()) return ActionResultType.FAIL;
        if (worldIn.isRemote)
            return ActionResultType.SUCCESS;
        CombustionGeneratorTileEntity te = (CombustionGeneratorTileEntity) worldIn.getTileEntity(pos);
        player.sendMessage(new StringTextComponent("Energy: " + te.energyStorage.getEnergyStored() + " Timeleft: " + te.timeleft));
        NetworkHooks.openGui((ServerPlayerEntity) player, te, pos);
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityType.combustion_generator.get().create();
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
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("Generates 5 RF/t by burning fuel"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
