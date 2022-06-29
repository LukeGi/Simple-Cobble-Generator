package dev.luhegi.mods.simplecobblegen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import dev.luhegi.mods.simplecobblegen.Config.CobbleGenConfig;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = SGC.ID, bus = Bus.MOD)
public class DataGen {

  @SubscribeEvent
  public static void onGatherData(GatherDataEvent event) {
    DataGenerator gen = event.getGenerator();
    ExistingFileHelper xfh = event.getExistingFileHelper();

    if (event.includeClient()) {
      gen.addProvider(new States(gen, xfh));
      gen.addProvider(new ItemModels(gen, xfh));
      gen.addProvider(new Lang(gen));
    }
    if (event.includeServer()) {
      gen.addProvider(new Recipes(gen));
      gen.addProvider(new AllLoots(gen));
    }
  }

  public static class AllLoots extends LootTableProvider {

    public AllLoots(DataGenerator p_124437_) {
      super(p_124437_);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables() {
      return ImmutableList.of(Pair.of(BlockLoots::new, LootContextParamSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, final ValidationContext validationtracker) {
      map.forEach((id, table) -> LootTables.validate(validationtracker, id, table));
    }
  }

  public static class BlockLoots extends BlockLoot {

    @Override
    protected void addTables() {
      dropSelf(SGC.COBBLE_GENERATOR_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
      return ImmutableSet.<RegistryObject<Block>>builder()
          .add(SGC.COBBLE_GENERATOR_BLOCK)
          .build().stream().map(RegistryObject::get).toList();
    }
  }

  public static class ItemModels extends ItemModelProvider {

    public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
      super(generator, SGC.ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
      withExistingParent("cobble_generator", modLoc("block/cobble_generator"));
    }
  }

  public static class States extends BlockStateProvider {

    public States(DataGenerator gen, ExistingFileHelper exFileHelper) {
      super(gen, SGC.ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
      simpleBlock(SGC.COBBLE_GENERATOR_BLOCK.get());
    }
  }

  public static class Lang extends LanguageProvider {

    public Lang(DataGenerator gen) {
      super(gen, SGC.ID, "en_us");
    }

    @Override
    protected void addTranslations() {
      addBlock(SGC.COBBLE_GENERATOR_BLOCK, "Cobblestone Genreator");
      add(String.format("config.%s.%s.rfperblock", SGC.ID, CobbleGenConfig.NAME), "The RF required to generate a single cobble block.");
      add(String.format("config.%s.%s.maxcobblepertick", SGC.ID, CobbleGenConfig.NAME), "The maximum number of cobble blocks that can be generated per tick.");
    }
  }

  public static class Recipes extends RecipeProvider {

    public Recipes(DataGenerator generator) {
      super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
      ShapedRecipeBuilder.shaped(SGC.COBBLE_GENERATOR_ITEM.get())
          .pattern("PPP")
          .pattern("LCW")
          .pattern("PPP")
          .define('P', Items.IRON_PICKAXE)
          .define('C', Items.COBBLESTONE)
          .define('L', Items.LAVA_BUCKET)
          .define('W', Items.WATER_BUCKET)
          .unlockedBy("has_cobble_generator", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COBBLESTONE))
          .save(consumer);
    }
  }
}
