package io.github.lbowenwest.fletchery.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmiFletchingRecipe implements EmiRecipe {
    public static final int WIDTH = 82;
    public static final int HEIGHT = 54;
    private final List<EmiIngredient> input;
    private final EmiStack output;
    private final ResourceLocation identifier;

    public EmiFletchingRecipe(List<Ingredient> input, ItemStack output, ResourceLocation identifier) {
        this.identifier = identifier;

        this.input = input.stream()
                .map(EmiIngredient::of)
                .toList();

        this.output = EmiStack.of(output);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return FletcheryEmiPlugin.FLETCHING;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return identifier;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return input;
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
        widgetHolder.addSlot(input.get(0), 0, 0);
        widgetHolder.addSlot(input.get(1), 0, 18);
        widgetHolder.addSlot(input.get(2), 0, 36);

        widgetHolder.addTexture(EmiTexture.EMPTY_ARROW, 24, 18);

        widgetHolder.addSlot(output, 56, 14)
                .recipeContext(this)
                .large(true);
    }
}
