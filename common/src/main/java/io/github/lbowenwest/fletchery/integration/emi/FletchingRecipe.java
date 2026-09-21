package io.github.lbowenwest.fletchery.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FletchingRecipe implements EmiRecipe {
    public static final int WIDTH = 82;
    public static final int HEIGHT = 54;
    private final io.github.lbowenwest.fletchery.recipe.FletchingRecipe recipe;
    private final List<EmiIngredient> inputs;
    private final EmiStack output;

    public FletchingRecipe(io.github.lbowenwest.fletchery.recipe.FletchingRecipe recipe) {
        this.recipe = recipe;

        this.inputs = recipe.getIngredients()
                .stream()
                .map(EmiIngredient::of)
                .toList();

        this.output = EmiStack.of(recipe.getResultItem(RegistryAccess.EMPTY));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return FletcheryEmiPlugin.FLETCHING;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return recipe.getId();
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return inputs;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(output);
    }

    @Override
    public int getDisplayWidth() {
        return WIDTH;
    }

    @Override
    public int getDisplayHeight() {
        return HEIGHT;
    }

    @Override
    public void addWidgets(WidgetHolder widgetHolder) {
        widgetHolder.addSlot(inputs.get(0), 0, 0);
        widgetHolder.addSlot(inputs.get(1), 0, 18);
        widgetHolder.addSlot(inputs.get(2), 0, 36);

        widgetHolder.addTexture(EmiTexture.EMPTY_ARROW, 24, 18);

        widgetHolder.addSlot(output, 56, 14)
                .recipeContext(this)
                .large(true);
    }
}
