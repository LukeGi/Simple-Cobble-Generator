package dev.luhegi.mods.simplecobblegen.data;

import dev.luhegi.mods.simplecobblegen.SGC;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class StateProvider extends BlockStateProvider {
    public StateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        SGC.COBBLE_GENERATOR_BLOCK.ifPresent(it -> this.simpleBlockWithItem(it, cubeAll(it)));
    }
}
