package io.github.lbowenwest.fletchery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.lbowenwest.fletchery.FletcheryIdentifier;
import io.github.lbowenwest.fletchery.recipe.FletchingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;

public class FletcheryRecipeType {
    private static final Registrar<RecipeType<?>> RECIPE_TYPES = DeferredRegister
            .create(FletcheryIdentifier.MOD_ID, Registries.RECIPE_TYPE)
            .getRegistrar();

    public static final RegistrySupplier<RecipeType<FletchingRecipe>> FLETCHING = RECIPE_TYPES.register(
            FletcheryIdentifier.of("fletching"),
            () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "fletching";
                }
            }
    );

    public static void init() {
    }
}
