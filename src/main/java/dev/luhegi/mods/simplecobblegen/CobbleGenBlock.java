package dev.luhegi.mods.simplecobblegen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CobbleGenBlock extends BaseEntityBlock {

  public CobbleGenBlock() {
    super(Properties.copy(Blocks.COBBLESTONE)
        .strength(2f, 15f));
  }

  @Override
  public RenderShape getRenderShape(BlockState p_49232_) {
    return RenderShape.MODEL;
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
    return new CobbleGenEntity(p_153215_, p_153216_);
  }
}