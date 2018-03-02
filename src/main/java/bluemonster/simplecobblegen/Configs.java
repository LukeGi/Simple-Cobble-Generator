package bluemonster.simplecobblegen;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.RequiresWorldRestart;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = SimpleCobbleGen.MODID)
@EventBusSubscriber(modid = SimpleCobbleGen.MODID)
public class Configs {
    @Config.RangeInt(min = 0)
    @RequiresWorldRestart
    public static int RF_PER_BLOCK = 0;

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent event) {
        if (event.getModID().equals(SimpleCobbleGen.MODID)) {
            ConfigManager.sync(SimpleCobbleGen.MODID, Type.INSTANCE);
        }
    }
}
