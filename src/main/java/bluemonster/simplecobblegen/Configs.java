package bluemonster.simplecobblegen;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.Config.RequiresWorldRestart;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = SimpleCobbleGen.MODID)
@EventBusSubscriber(modid = SimpleCobbleGen.MODID)
public class Configs {

  @RangeInt(min = 0)
  @RequiresWorldRestart
  public static int RF_PER_BLOCK = 0;

  @RangeInt(min = -1)
  @Comment("Set this to any positive value to limit the number of cobble, or -1 to make it infinite.")
  public static int MAX_COBBLE_PER_TICK = -1;

  @SubscribeEvent
  public static void onConfigChanged(ConfigChangedEvent event) {
    if (event.getModID().equals(SimpleCobbleGen.MODID)) {
      ConfigManager.sync(SimpleCobbleGen.MODID, Type.INSTANCE);
    }
  }
}
