package bluemonster.simplecobblegen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.recipebook.RecipeBookPage;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.stats.RecipeBook;
import net.minecraft.stats.RecipeBookServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nullable;

@Mod(modid = SimpleCobbleGen.MODID, name = "Simple Cobblestone Generator", version = "X.F.0")
@Mod.EventBusSubscriber
public class SimpleCobbleGen {
    public static Block cobblegen;
    public static Item cobblegen_item;
    public static final String MODID = "simplecobblegen";
    private static final ResourceLocation RS = new ResourceLocation(MODID, "cobblegen");

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        event.getRegistry().register(
                new ShapedOreRecipe(RS, new ItemStack(SimpleCobbleGen.cobblegen, 1), CraftingHelper.parseShaped(
                        "PPP",
                        "WCL",
                        "PPP",
                        'P', Items.IRON_PICKAXE,
                        'W', Items.WATER_BUCKET,
                        'L', Items.LAVA_BUCKET,
                        'C', "cobblestone"
                )).setRegistryName(RS));
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(cobblegen = new BlockContainer(Material.ROCK) {
            @Nullable
            @Override
            public TileEntity createNewTileEntity(World worldIn, int meta) {
                return new TileEntityCobbleGen();
            }
        }.setHardness(4).setResistance(4).setRegistryName(RS).setUnlocalizedName(RS.toString()));
        GameRegistry.registerTileEntity(TileEntityCobbleGen.class, RS.toString());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(cobblegen_item = new ItemBlock(cobblegen).setRegistryName(RS));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(cobblegen_item, 0, new ModelResourceLocation(RS, "inventory"));
    }

    @Config(modid = "simplecobblegen")
    public static class Configs {
        @Config.RangeInt(min = 0)
        public static int RF_PER_BLOCK = 0;
    }
}
