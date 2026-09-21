package io.github.lbowenwest.fletchery.integration.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import io.github.lbowenwest.fletchery.FletcheryIdentifier;
import io.github.lbowenwest.fletchery.recipe.FletchingRecipe;
import io.github.lbowenwest.fletchery.registry.FletcheryMenu;
import io.github.lbowenwest.fletchery.registry.FletcheryRecipeType;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@EmiEntrypoint
public class FletcheryEmiPlugin implements EmiPlugin {
    private static final EmiTexture FLETCHING_ICON = new EmiTexture(
            FletcheryIdentifier.of("textures/gui/emi_icon.png"),
            0, 0,
            16, 16,
            16, 16,
            16, 16
    );

    public static final EmiRecipeCategory FLETCHING = new EmiRecipeCategory(
            FletcheryIdentifier.of("fletching_table"),
            EmiStack.of(Items.FLETCHING_TABLE),
            FLETCHING_ICON
    );

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(FLETCHING);
        registry.addWorkstation(FLETCHING, EmiStack.of(Items.FLETCHING_TABLE));
        registry.addRecipeHandler(FletcheryMenu.FLETCHING_TABLE.get(), new EmiFletchingRecipeHandler());

        RecipeManager manager = registry.getRecipeManager();
        List<FletchingRecipe> recipes = manager.getAllRecipesFor(FletcheryRecipeType.FLETCHING.get());
        for (FletchingRecipe recipe : recipes) {
            List<Ingredient> ingredients = recipe.getIngredients();
            ItemStack result = recipe.getResultItem(RegistryAccess.EMPTY);

            int potion_index = IntStream.range(0, ingredients.size())
                    .filter(i -> ingredients.get(i).test(new ItemStack(Items.LINGERING_POTION)))
                    .findFirst()
                    .orElse(-1);

            if (potion_index != -1 && result.is(Items.TIPPED_ARROW)) {
                BuiltInRegistries.POTION.stream()
                        .filter(p -> p != Potions.EMPTY)
                        .forEach(p -> {
                            Ingredient potion = Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), p));
                            System.out.println(BuiltInRegistries.POTION.getKey(p));

                            ItemStack arrow = PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), p);
                            List<Ingredient> input = new ArrayList<>(ingredients);
                            input.set(potion_index, potion);
                            ResourceLocation id = recipe.getId()
                                    .withPrefix("/")
                                    .withSuffix("/" + BuiltInRegistries.POTION.getKey(p).getPath());

                            registry.addRecipe(new EmiFletchingRecipe(input, arrow, id));
                        });
            } else {
                registry.addRecipe(new EmiFletchingRecipe(ingredients, result, recipe.getId()));
            }
        }
    }
}
