package dev.luhegi.mods.simplecobblegen;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class Config {

  public static final ForgeConfigSpec SERVER;
  public static final CobbleGenConfig COBBLE_GEN;

  static {
    Builder serverSpec = new Builder();
    COBBLE_GEN = new CobbleGenConfig(serverSpec);
    SERVER = serverSpec.build();
  }

  public static class CobbleGenConfig {

    public static final String NAME = "cobble_generator";
    private final IntValue rf_per_block;
    private final IntValue max_cobble_per_tick;

    public CobbleGenConfig(Builder builder) {
      builder.push(NAME);
      this.rf_per_block = builder
          .comment("The RF required to generate a single cobble block.",
              "If set to 0, cobble generation will not cost energy at all.")
          .translation(String.format("config.%s.%s.rfperblock", SGC.ID, NAME))
          .worldRestart()
          .defineInRange("rf_per_block", 0, 0, Integer.MAX_VALUE);
      this.max_cobble_per_tick = builder
          .comment("The maximum number of cobble blocks that can be generated per tick.",
              "If set to -1, cobble generation will not be limited.",
              "BEWARE! If set to 0, no cobble will generate.")
          .translation(String.format("config.%s.%s.maxcobblepertick", SGC.ID, NAME))
          .defineInRange("max_cobble_per_tick", -1, -1, Integer.MAX_VALUE);
      builder.pop();
    }

    public int rf_per_block() {
      return rf_per_block.get();
    }

    public int max_cobble_per_tick() {
      return max_cobble_per_tick.get();
    }
  }
}
