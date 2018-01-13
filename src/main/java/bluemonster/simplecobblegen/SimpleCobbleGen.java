package bluemonster.simplecobblegen;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid = "simplecobblegen", name = "Simple Cobblestone Generator", version = "X.F.0")
@Mod.EventBusSubscriber
public class SimpleCobbleGen {
    public static Block cobblegen;
    public static Item cobblegen_item;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if (!Minecraft.getMinecraft().getVersion().contains("1.12"))
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cobblegen, 1),
                    "PPP",
                    "WCL",
                    "PPP",
                    'P', Items.IRON_PICKAXE,
                    'W', Items.WATER_BUCKET,
                    'L', Items.LAVA_BUCKET,
                    'C', "cobblestone"
            ));
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(cobblegen = new CobbleGenBlock());
        GameRegistry.registerTileEntity(TileEntityCobbleGen.class, "simplecobblegen:cobblegen");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(cobblegen_item = new ItemBlock(cobblegen).setRegistryName("simplecobblegen", "cobblegen"));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(cobblegen_item, 0, new ModelResourceLocation(cobblegen.getRegistryName(), "inventory"));
    }

    @Config(modid = "simplecobblegen")
    public static class Configs {
        @Config.RangeInt(min = 0)
        public static int RF_PER_BLOCK = 0;
    }
}
