package dev.luhegi.mods.simplecobblegen.data;

import dev.luhegi.mods.simplecobblegen.SGC;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class RecipesProvider extends RecipeProvider {
    public RecipesProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        SGC.COBBLE_GENERATOR_ITEM.ifPresent(it -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, it)
                .pattern("PPP")
                .pattern("LCW")
                .pattern("PPP")
                .define('P', Items.IRON_PICKAXE)
                .define('C', Items.COBBLESTONE)
                .define('L', Items.LAVA_BUCKET)
                .define('W', Items.WATER_BUCKET)
                .unlockedBy("has_cobble_generator", TriggerInstance.hasItems(Items.COBBLESTONE))
                .save(consumer)
        );
    }
}
