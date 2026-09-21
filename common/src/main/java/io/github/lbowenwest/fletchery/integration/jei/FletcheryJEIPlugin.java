package io.github.lbowenwest.fletchery.integration.jei;

import io.github.lbowenwest.fletchery.FletcheryIdentifier;
import io.github.lbowenwest.fletchery.client.gui.FletchingScreen;
import io.github.lbowenwest.fletchery.client.gui.FletchingMenu;
import io.github.lbowenwest.fletchery.registry.FletcheryMenu;
import io.github.lbowenwest.fletchery.registry.FletcheryRecipeType;
import io.github.lbowenwest.fletchery.recipe.FletchingTableRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class FletcheryJEIPlugin implements IModPlugin {
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new FletchingTableCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<FletchingTableRecipe> fletchingRecipes = rm.getAllRecipesFor(FletcheryRecipeType.FLETCHING_TABLE.get());
        registration.addRecipes(FletchingTableCategory.FLETCHING_TABLE, fletchingRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(Items.FLETCHING_TABLE.getDefaultInstance(), FletchingTableCategory.FLETCHING_TABLE);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(
                FletchingMenu.class,
                FletcheryMenu.FLETCHING_TABLE.get(),
                FletchingTableCategory.FLETCHING_TABLE,
                1,
                3,
                4,
                36);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(FletchingScreen.class,
                75,
                32,
                28,
                23,
                FletchingTableCategory.FLETCHING_TABLE);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return FletcheryIdentifier.of("jei_plugin");
    }
}
