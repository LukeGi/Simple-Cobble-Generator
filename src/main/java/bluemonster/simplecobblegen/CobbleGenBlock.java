package bluemonster.simplecobblegen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CobbleGenBlock extends Block implements ITileEntityProvider {

	protected CobbleGenBlock() {
		super(Material.ROCK);
		setHardness(4);
		setResistance(4);
		setRegistryName("simplecobblegen:cobblegen");
		setUnlocalizedName("simplecobblegen:cobblegen");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCobbleGen();
	}

}
