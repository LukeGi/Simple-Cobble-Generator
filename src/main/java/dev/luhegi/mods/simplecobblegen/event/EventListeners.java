package dev.luhegi.mods.simplecobblegen.event;

import dev.luhegi.mods.simplecobblegen.SGC;
import dev.luhegi.mods.simplecobblegen.data.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataProvider.Factory;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Set;

public final class EventListeners {
    private EventListeners() {
    }

    @Mod.EventBusSubscriber(modid = SGC.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {
        @SubscribeEvent
        public static void onGatherData(GatherDataEvent event) {
            DataGenerator master = event.getGenerator();
            ExistingFileHelper xfh = event.getExistingFileHelper();
            boolean includeServerData = event.includeServer();
            boolean includeClientData = event.includeClient();
            master.addProvider(includeClientData, (Factory<DataProvider>) output -> new StateProvider(output, SGC.ID, xfh));
            master.addProvider(includeClientData, (Factory<DataProvider>) output -> new Lang_EN(output, SGC.ID));

            master.addProvider(includeServerData, (Factory<DataProvider>) output -> new LootTableProvider(
                    output,
                    Set.of(),
                    List.of(new LootTableProvider.SubProviderEntry(BlockLoot::new, LootContextParamSets.BLOCK))
            ));
            master.addProvider(includeServerData, (Factory<DataProvider>) output -> new BlockTagProvider(
                    output,
                    event.getLookupProvider(),
                    SGC.ID,
                    xfh
            ));
            master.addProvider(includeServerData, (Factory<DataProvider>) RecipesProvider::new);
        }

        @SubscribeEvent
        public static void onBuildContents(BuildCreativeModeTabContentsEvent event) {
            ResourceKey<CreativeModeTab> tab = event.getTabKey();
            if (CreativeModeTabs.FUNCTIONAL_BLOCKS == tab) {
                event.accept(SGC.COBBLE_GENERATOR_BLOCK);
            }
        }
    }
}
