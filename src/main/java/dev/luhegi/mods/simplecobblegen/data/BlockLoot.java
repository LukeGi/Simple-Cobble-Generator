package dev.luhegi.mods.simplecobblegen.data;

import dev.luhegi.mods.simplecobblegen.SGC;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class BlockLoot extends BlockLootSubProvider {
    public BlockLoot() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        SGC.COBBLE_GENERATOR_BLOCK.ifPresent(this::dropSelf);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return SGC.BLOCKS.getEntries()
                .stream()
                .flatMap(RegistryObject::stream)
                ::iterator;
    }
}
