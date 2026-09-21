package io.github.lbowenwest.fletchery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.lbowenwest.fletchery.FletcheryIdentifier;
import io.github.lbowenwest.fletchery.recipe.FletchingTableRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class FletcheryRecipeSerializer {
    private static final Registrar<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
            .create(FletcheryIdentifier.MOD_ID, Registries.RECIPE_SERIALIZER)
            .getRegistrar();

    public static final RegistrySupplier<RecipeSerializer<FletchingTableRecipe>> FLETCHING_TABLE = RECIPE_SERIALIZERS.register(
            FletcheryIdentifier.of("fletching"),
            FletchingTableRecipe.Serializer::new
    );

    public static void init() {
    }
}
