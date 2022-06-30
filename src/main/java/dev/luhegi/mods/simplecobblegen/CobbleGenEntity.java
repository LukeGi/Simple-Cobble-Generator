package dev.luhegi.mods.simplecobblegen;

import javax.annotation.Nonnull;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class CobbleGenEntity extends TileEntity implements IItemHandler {

  EnergyStorage energy = null;
  LazyOptional<EnergyStorage> energyHolder = null;
  LazyOptional<IItemHandler> invHolder;

  public CobbleGenEntity() {
    super(SGC.COBBLE_GENERATOR_ENTITY.get());
    initEnergy(-1);
    invHolder = LazyOptional.of(() -> this);
  }

  private void initEnergy(int amount) {
    if (energy == null || amount != -1) {
      if (Config.COBBLE_GEN.rf_per_block() > 0) {
        energy = new EnergyStorage(Math.min(Config.COBBLE_GEN.rf_per_block(), Integer.MAX_VALUE / 2000) * 1000, Config.COBBLE_GEN.rf_per_block(), amount == -1 ? 0 : amount);
        if (energyHolder != null) {
          energyHolder.invalidate();
        }
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
  public void load(BlockState state, CompoundNBT tag) {
    super.load(state, tag);
    if (tag.contains("energy")) {
      initEnergy(tag.getInt("energy"));
    }
  }

  @Override
  public CompoundNBT save(CompoundNBT tag) {
    if (energy != null) {
      tag.putInt("energy", energy.getEnergyStored());
    }
    return super.save(tag);
  }

  @Override
  public <T> LazyOptional<T> getCapability( Capability<T> cap, Direction side) {
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
  public ItemStack getStackInSlot(int slot) {
    return new ItemStack(Blocks.COBBLESTONE, getMaxCobble());
  }

  @Nonnull
  @Override
  public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
    return stack;
  }

  public int getMaxCobble() {
    int cobble = energy != null ? energy.getEnergyStored() / Config.COBBLE_GEN.rf_per_block() : Integer.MAX_VALUE;
    return Config.COBBLE_GEN.max_cobble_per_tick() == -1 ? cobble : Math.min(cobble, Config.COBBLE_GEN.max_cobble_per_tick());
  }

  @Override
  public ItemStack extractItem(int slot, int amount, boolean simulate) {
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
  public boolean isItemValid(int slot, ItemStack stack) {
    return stack.getItem() == Blocks.COBBLESTONE.asItem();
  }
}
