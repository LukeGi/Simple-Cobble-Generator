package bluemonster.simplecobblegen;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityCobbleGen extends TileEntity implements IItemHandler {
    EnergyStorage storage;

    public TileEntityCobbleGen() {
        if (SimpleCobbleGen.Configs.RF_PER_BLOCK > 0) {
            storage = createBattery();
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (CapabilityEnergy.ENERGY.equals(capability) && storage != null) {
            return true;
        } else if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
            return true;
        } else {
            return super.hasCapability(capability, facing);
        }
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (CapabilityEnergy.ENERGY.equals(capability) && storage != null) {
            return CapabilityEnergy.ENERGY.cast(storage);
        } else if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this);
        } else {
            return super.getCapability(capability, facing);
        }
    }

    private EnergyStorage createBattery() {
        return new EnergyStorage(Math.min(1000, SimpleCobbleGen.Configs.RF_PER_BLOCK));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if (storage != null) {
            compound.setTag("EnergyStorage", CapabilityEnergy.ENERGY.writeNBT(storage, null));
        }
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("EnergyStorage") && storage != null) {
            CapabilityEnergy.ENERGY.readNBT(storage, null, compound.getTag("EnergyStorage"));
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

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return stack;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        int amt = Math.min(getMaxCobble(), amount);
        if (storage != null)
            storage.extractEnergy(amt * SimpleCobbleGen.Configs.RF_PER_BLOCK, simulate);
        return new ItemStack(Blocks.COBBLESTONE, amt);
    }

    public int getMaxCobble() {
        return storage != null ? storage.getEnergyStored() / SimpleCobbleGen.Configs.RF_PER_BLOCK : Integer.MAX_VALUE;
    }
}
