package bluemonster.simplecobblegen;

import static bluemonster.simplecobblegen.SimpleCobbleGen.MODID;
import static bluemonster.simplecobblegen.SimpleCobbleGen.NAME;
import static bluemonster.simplecobblegen.SimpleCobbleGen.VERSION;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = MODID, name = NAME, version = VERSION)
@Mod.EventBusSubscriber
public class SimpleCobbleGen {

  public static final String MODID = "simplecobblegen";
  public static final String NAME = "Simple Cobblestone Generator";
  public static final String VERSION = "X.F.1";

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public static void onPlayerJoin(EntityJoinWorldEvent event) {
    Entity player = event.getEntity();
    if (player instanceof EntityPlayer) {
      ((EntityPlayer) player).unlockRecipes(ForgeRegistries.RECIPES.getKeys().stream().filter(key -> key.getResourceDomain().equals(MODID)).toArray(ResourceLocation[]::new));
    }
  }

  @SubscribeEvent
  public static void registerBlocks(RegistryEvent.Register<Block> event) {
    event.getRegistry().register(CobbleGen.BLOCK);
    TileEntity.register(CobbleGen.RS.toString(), CobbleGen.Tile.class);
  }

  @SubscribeEvent
  public static void registerItems(RegistryEvent.Register<Item> event) {
    event.getRegistry().register(CobbleGen.ITEM);
  }

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public static void registerModels(ModelRegistryEvent event) {
    ModelLoader.setCustomModelResourceLocation(CobbleGen.ITEM, 0, new ModelResourceLocation(CobbleGen.RS, "inventory"));
  }

}
