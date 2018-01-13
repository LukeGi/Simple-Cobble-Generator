package bluemonster.simplecobblegen;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CobbleGenBlock extends Block implements ITileEntityProvider {

    public CobbleGenBlock() {
        super(Material.ROCK);
        setHardness(3);
        setResistance(4);
        setHarvestLevel("pickaxe", 0);
        setRegistryName("simplecobblegen", "cobblegen");
        setUnlocalizedName(getRegistryName().getResourcePath());
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCobbleGen();
    }
}
