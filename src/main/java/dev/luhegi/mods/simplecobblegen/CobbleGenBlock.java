package dev.luhegi.mods.simplecobblegen;

import javax.annotation.Nullable;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class CobbleGenBlock extends ContainerBlock {

  public CobbleGenBlock() {
    super(Properties.copy(Blocks.COBBLESTONE)
        .strength(2f, 15f));
  }

  public BlockRenderType getRenderShape(BlockState p_149645_1_) {
    return BlockRenderType.MODEL;
  }

  @Nullable
  @Override
  public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
    return new CobbleGenEntity();
  }
}