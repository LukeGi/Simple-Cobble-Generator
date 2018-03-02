package bluemonster.simplecobblegen;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.util.ResourceLocation;

public class WorldLoadTrigger implements ICriterionTrigger{

    private static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(SimpleCobbleGen.MODID, "world_load");

    @Override
    public ResourceLocation getId() {
        return null;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, Listener listener) {

    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener listener) {

    }

    @Override
    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {

    }

    /**
     * Deserialize a ICriterionInstance of this trigger from the data in the JSON.
     *
     * @param json
     * @param context
     */
    @Override
    public ICriterionInstance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        return null;
    }
}
