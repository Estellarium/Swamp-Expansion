package com.farcr.swampexpansion.common.block;

import javax.annotation.Nullable;

import com.farcr.swampexpansion.common.tile.MudVaseTileEntity;
import com.farcr.swampexpansion.core.registry.SwampExTileEntities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class MudVaseBlock extends ContainerBlock implements IWaterLoggable {
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 9.0D, 11.0D);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

	public MudVaseBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, false));
	}

	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof MudVaseTileEntity) {
			MudVaseTileEntity mudvasetileentity = (MudVaseTileEntity)tileentity;
			ItemStack itemstack = player.getHeldItem(handIn);
			if (itemstack.isEmpty()) {
				if (mudvasetileentity.removeItem(player, handIn)) {
					return ActionResultType.SUCCESS;
				}
			} else if (mudvasetileentity.addItem(player.abilities.isCreativeMode ? itemstack.copy() : itemstack)) {
				worldIn.playSound(player, pos, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.NEUTRAL, 1.0F, 1.0F);
				return ActionResultType.SUCCESS;
			}
			

			return ActionResultType.CONSUME;
		}
		return ActionResultType.PASS;
	}
	
	@SuppressWarnings("deprecation")
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
	    	  TileEntity tileentity = worldIn.getTileEntity(pos);
	    	  if (tileentity instanceof MudVaseTileEntity) {
	    		  InventoryHelper.dropItems(worldIn, pos, ((MudVaseTileEntity)tileentity).getInventory());
	    	  }

	    	  super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}
	
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
	    return true;
	}
	
	@SuppressWarnings("deprecation")
    @Override
	public IFluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);	
	}

	@Override
	public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
	    return false;
	}
	
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING, WATERLOGGED);
	}
	
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IWorld iworld = context.getWorld();
		BlockPos blockpos = context.getPos();
		boolean flag = iworld.getFluidState(blockpos).getFluid() == Fluids.WATER;
		return this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(flag)).with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing());
	}
	
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof MudVaseTileEntity) {
				((MudVaseTileEntity)tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}

	public boolean hasComparatorInputOverride(BlockState state) {
		return true;
	}

	public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
		return MudVaseTileEntity.calcRedstone(worldIn.getTileEntity(pos));
	}
	
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(HORIZONTAL_FACING, rot.rotate(state.get(HORIZONTAL_FACING)));
	}

	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(HORIZONTAL_FACING)));
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return SwampExTileEntities.MUD_VASE.get().create();
	}
}
