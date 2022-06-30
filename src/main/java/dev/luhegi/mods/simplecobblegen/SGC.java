package dev.luhegi.mods.simplecobblegen;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SGC.ID)
public class SGC {

  public static final String ID = "simplecobblegen";
  private static final Logger LOG = LogManager.getLogger();
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ID);
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ID);
  public static final DeferredRegister<TileEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ID);
  public static final RegistryObject<Block> COBBLE_GENERATOR_BLOCK = BLOCKS.register("cobble_generator", CobbleGenBlock::new);
  public static final RegistryObject<Item> COBBLE_GENERATOR_ITEM = ITEMS.register("cobble_generator", () -> new BlockItem(COBBLE_GENERATOR_BLOCK.get(), new Item.Properties().tab(ItemGroup.TAB_MISC)));
  public static final RegistryObject<TileEntityType<CobbleGenEntity>> COBBLE_GENERATOR_ENTITY = BLOCK_ENTITIES.register("cobble_generator", () -> TileEntityType.Builder.of(CobbleGenEntity::new, COBBLE_GENERATOR_BLOCK.get()).build(null));

  public SGC() {
    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    BLOCKS.register(modEventBus);
    ITEMS.register(modEventBus);
    BLOCK_ENTITIES.register(modEventBus);
    ModLoadingContext.get().registerConfig(Type.SERVER, Config.SERVER);
  }
}
