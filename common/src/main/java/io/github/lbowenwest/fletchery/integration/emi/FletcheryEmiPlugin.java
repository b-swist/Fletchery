package io.github.lbowenwest.fletchery.integration.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import io.github.lbowenwest.fletchery.Fletchery;
import io.github.lbowenwest.fletchery.FletcheryIdentifier;
import io.github.lbowenwest.fletchery.registry.FletcheryRecipeType;
import io.github.lbowenwest.fletchery.recipe.FletchingTableRecipe;
import net.minecraft.world.item.Items;

@EmiEntrypoint
public class FletcheryEmiPlugin implements EmiPlugin {
    private static final EmiTexture FLETCHING_TABLE_ICON = new EmiTexture(
            FletcheryIdentifier.of("textures/gui/emi_icon.png"),
            0, 0,
            16, 16,
            16, 16,
            16, 16
    );

    public static final EmiRecipeCategory FLETCHING_TABLE = new EmiRecipeCategory(
            FletcheryIdentifier.of("fletching_table"),
            EmiStack.of(Items.FLETCHING_TABLE),
            FLETCHING_TABLE_ICON
    );

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(FLETCHING_TABLE);
        registry.addWorkstation(FLETCHING_TABLE, EmiStack.of(Items.FLETCHING_TABLE));
        registry.addRecipeHandler(Fletchery.FLETCHING_TABLE_MENU_HANDLER.get(), new FletchingTableEmiRecipeHandler());

        for (FletchingTableRecipe recipe : registry.getRecipeManager().getAllRecipesFor(FletcheryRecipeType.FLETCHING_TABLE.get())) {
            registry.addRecipe(new FletchingTableEmiRecipe(recipe));
        }
    }
}
