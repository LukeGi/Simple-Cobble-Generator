package dev.luhegi.mods.simplecobblegen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CobbleGenEntity extends BlockEntity implements IItemHandler {

  EnergyStorage energy = null;
  LazyOptional<EnergyStorage> energyHolder = null;
  LazyOptional<IItemHandler> invHolder;

  public CobbleGenEntity(BlockPos pos, BlockState state) {
    super(SGC.COBBLE_GENERATOR_ENTITY.get(), pos, state);
    initEnergy();
    invHolder = LazyOptional.of(() -> this);
  }

  private void initEnergy() {
    if (energy == null) {
      if (Config.COBBLE_GEN.rf_per_block() > 0) {
        energy = new EnergyStorage(Math.min(Config.COBBLE_GEN.rf_per_block(), Integer.MAX_VALUE / 2000) * 1000, Config.COBBLE_GEN.rf_per_block());
        energyHolder = LazyOptional.of(() -> energy);
      }
    }
  }

  @Override
  public void invalidateCaps() {
    super.invalidateCaps();
    if (energyHolder != null) {
      energyHolder.invalidate();
    }
  }

  @Override
  public void load(CompoundTag tag) {
    super.load(tag);
    if (tag.contains("energy")) {
      initEnergy();
      energy.deserializeNBT(tag.getCompound("energy"));
    }
  }

  @Override
  protected void saveAdditional(CompoundTag tag) {
    super.saveAdditional(tag);
    if (energy != null) {
      tag.put("energy", energy.serializeNBT());
    }
  }

  @Override
  public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
    if (CapabilityEnergy.ENERGY.equals(cap)) {
      return energyHolder.cast();
    } else if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(cap)) {
      return invHolder.cast();
    } else {
      return super.getCapability(cap, side);
    }
  }

  @Override
  public int getSlots() {
    return 1;
  }

  @Override
  public @NotNull ItemStack getStackInSlot(int slot) {
    return new ItemStack(Blocks.COBBLESTONE, getMaxCobble());
  }

  public int getMaxCobble() {
    int cobble = energy != null ? energy.getEnergyStored() / Config.COBBLE_GEN.rf_per_block() : Integer.MAX_VALUE;
    return Config.COBBLE_GEN.max_cobble_per_tick() == -1 ? cobble : Math.min(cobble, Config.COBBLE_GEN.max_cobble_per_tick());
  }

  @Override
  public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
    return stack;
  }

  @Override
  public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
    int amt = Math.min(getMaxCobble(), amount);
    if (energy != null) {
      energy.extractEnergy(amt * Config.COBBLE_GEN.rf_per_block(), simulate);
    }
    return new ItemStack(Blocks.COBBLESTONE, amt);
  }

  @Override
  public int getSlotLimit(int slot) {
    return Integer.MAX_VALUE;
  }

  @Override
  public boolean isItemValid(int slot, @NotNull ItemStack stack) {
    return stack.is(Blocks.COBBLESTONE.asItem());
  }
}
