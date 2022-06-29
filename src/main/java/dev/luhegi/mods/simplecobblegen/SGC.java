package dev.luhegi.mods.simplecobblegen;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SGC.ID)
public class SGC {

  public static final String ID = "simplecobblegen";
  private static final Logger LOG = LoggerFactory.getLogger(ID);
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ID);
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ID);
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ID);
  public static final RegistryObject<Block> COBBLE_GENERATOR_BLOCK = BLOCKS.register("cobble_generator", CobbleGenBlock::new);
  public static final RegistryObject<Item> COBBLE_GENERATOR_ITEM = ITEMS.register("cobble_generator", () -> new BlockItem(COBBLE_GENERATOR_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
  public static final RegistryObject<BlockEntityType<CobbleGenEntity>> COBBLE_GENERATOR_ENTITY = BLOCK_ENTITIES.register("cobble_generator", () -> BlockEntityType.Builder.of(CobbleGenEntity::new, COBBLE_GENERATOR_BLOCK.get()).build(null));

  public SGC() {
    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    BLOCKS.register(modEventBus);
    ITEMS.register(modEventBus);
    BLOCK_ENTITIES.register(modEventBus);
    ModLoadingContext.get().registerConfig(Type.SERVER, Config.SERVER);
  }
}
