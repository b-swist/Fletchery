package io.github.lbowenwest.fletchery.integration.jei;

import io.github.lbowenwest.fletchery.FletcheryIdentifier;
import io.github.lbowenwest.fletchery.client.gui.FletchingTableScreen;
import io.github.lbowenwest.fletchery.recipe.FletchingTableRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class FletchingTableCategory implements IRecipeCategory<FletchingTableRecipe> {
    public static final int WIDTH = 98;
    public static final int HEIGHT = 54;
    private final IDrawable background;
    private final IDrawable icon;
    private final Component name;

    public static final RecipeType<FletchingTableRecipe> FLETCHING_TABLE = new RecipeType<>(
            new FletcheryIdentifier("fletching_table"),
            FletchingTableRecipe.class
    );

    public FletchingTableCategory(IGuiHelper helper) {
        this.name = Component.translatable("jei.fletchery.fletching_table_category");
        this.background = helper.createDrawable(FletchingTableScreen.BACKGROUND, 47, 16, WIDTH, HEIGHT);
        this.icon = helper.createDrawableItemStack(Items.FLETCHING_TABLE.getDefaultInstance());
    }

    @Override
    public RecipeType<FletchingTableRecipe> getRecipeType() {
        return FLETCHING_TABLE;
    }

    @Override
    public Component getTitle() {
        return this.name;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FletchingTableRecipe recipe, IFocusGroup focuses) {
        NonNullList<Ingredient> ingredients = recipe.getIngredients();

        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(ingredients.get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 19).addIngredients(ingredients.get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 37).addIngredients(ingredients.get(2));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 77, 19).addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));

    }
}
