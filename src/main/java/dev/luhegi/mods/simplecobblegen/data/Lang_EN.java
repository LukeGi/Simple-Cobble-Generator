package dev.luhegi.mods.simplecobblegen.data;

import dev.luhegi.mods.simplecobblegen.Config;
import dev.luhegi.mods.simplecobblegen.SGC;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class Lang_EN extends LanguageProvider {
    public Lang_EN(PackOutput output, String modid) {
        super(output, modid, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addBlock(SGC.COBBLE_GENERATOR_BLOCK, "Cobblestone Generator");
        add("config.%s.%s.rfperblock".formatted(SGC.ID, Config.CobbleGenConfig.NAME),
                "The RF required to generate a single cobble block.");
        add("config.%s.%s.maxcobblepertick".formatted(SGC.ID, Config.CobbleGenConfig.NAME),
                "The maximum number of cobble blocks that can be generated per tick.");
    }
}
