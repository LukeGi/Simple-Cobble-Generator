package bluemonster.simplecobblegen;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CobbleGen extends Block implements ITileEntityProvider {

    static final ResourceLocation RS = new ResourceLocation(SimpleCobbleGen.MODID, "cobblegen");
    static final CobbleGen BLOCK = new CobbleGen();
    static final Item ITEM = new Item();

    public CobbleGen() {
        super(Material.ROCK);
        this.setResistance(15f);
        this.setHardness(5f);
        this.setHarvestLevel("pickaxe", 0);
        this.setRegistryName(RS);
        this.setUnlocalizedName(RS.toString());
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     *
     * @param worldIn
     * @param meta
     */
    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new Tile();
    }

    public static class Item extends ItemBlock {

        public Item() {
            super(BLOCK);
            setRegistryName(RS);
        }
    }

    public static class Tile extends TileEntity implements IItemHandler {

        EnergyStorage storage;

        public Tile() {
            if (Configs.RF_PER_BLOCK > 0) {
                storage = new EnergyStorage(Configs.RF_PER_BLOCK);
            }
        }

        /**
         * Uses configs and any stored energy to figure out the maximum amount of cobble that
         * this block can produce right now.
         * @return int value representing the highest number of cobble this machine can create right now.
         */
        public int getMaxCobble() {
            return storage != null ? storage.getEnergyStored() / Configs.RF_PER_BLOCK : Integer.MAX_VALUE;
        }

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            if (CapabilityEnergy.ENERGY.equals(capability) && storage != null) {
                return true;
            } else if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
                return true;
            } else {
                return super.hasCapability(capability, facing);
            }
        }

        @Nullable
        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
            if (CapabilityEnergy.ENERGY.equals(capability) && storage != null) {
                return CapabilityEnergy.ENERGY.cast(storage);
            } else if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability)) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this);
            } else {
                return super.getCapability(capability, facing);
            }
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

        /**
         * Returns the number of slots available
         *
         * @return The number of slots available
         **/
        @Override
        public int getSlots() {
            return 1;
        }

        /**
         * Returns the ItemStack in a given slot.
         * <p>
         * The result's stack size may be greater than the itemstacks max size.
         * <p>
         * If the result is empty, then the slot is empty.
         * <p>
         * <p/>
         * IMPORTANT: This ItemStack MUST NOT be modified. This method is not for
         * altering an inventories contents. Any implementers who are able to detect
         * modification through this method should throw an exception.
         * <p/>
         * SERIOUSLY: DO NOT MODIFY THE RETURNED ITEMSTACK
         *
         * @param slot Slot to query
         * @return ItemStack in given slot. Empty Itemstack if the slot is empty.
         **/
        @Nonnull
        @Override
        public ItemStack getStackInSlot(int slot) {
            return new ItemStack(Blocks.COBBLESTONE, getMaxCobble());
        }

        /**
         * Inserts an ItemStack into the given slot and return the remainder.
         * The ItemStack should not be modified in this function!
         * Note: This behaviour is subtly different from IFluidHandlers.fill()
         *
         * @param slot     Slot to insert into.
         * @param stack    ItemStack to insert. This must not be modified by the item handler.
         * @param simulate If true, the insertion is only simulated
         * @return The remaining ItemStack that was not inserted (if the entire stack is accepted, then return an empty ItemStack).
         * May be the same as the input ItemStack if unchanged, otherwise a new ItemStack.
         * The returned ItemStack can be safely modified after.
         **/
        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            return stack;
        }

        /**
         * Extracts an ItemStack from the given slot.
         * The returned value must be empty if nothing is extracted,
         * otherwise it's stack size must less than than amount and {@link ItemStack#getMaxStackSize()}.
         *
         * @param slot     Slot to extract from.
         * @param amount   Amount to extract (may be greater than the current stacks max limit)
         * @param simulate If true, the extraction is only simulated
         * @return ItemStack extracted from the slot, must be empty if nothing can be extracted.
         * The returned ItemStack can be safely modified after, so item handlers should return a new or copied stack.
         **/
        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            int amt = Math.min(getMaxCobble(), amount);
            if (storage != null)
                storage.extractEnergy(amt * Configs.RF_PER_BLOCK, simulate);
            return new ItemStack(Blocks.COBBLESTONE, amt);
        }

        /**
         * Retrieves the maximum stack size allowed to exist in the given slot.
         *
         * @param slot Slot to query.
         * @return The maximum stack size allowed in the slot.
         */
        @Override
        public int getSlotLimit(int slot) {
            return Integer.MAX_VALUE;
        }
    }
}
