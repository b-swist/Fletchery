package io.github.lbowenwest.fletchery.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import io.github.lbowenwest.fletchery.client.gui.handler.FletchingTableContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FletchingTableEmiRecipeHandler implements StandardRecipeHandler<FletchingTableContainerMenu> {
    @Override
    public List<Slot> getInputSources(FletchingTableContainerMenu handler) {
        List<Slot> list = new ArrayList<>();

        for (int i = 1; i < 40; i++) {
            list.add(handler.getSlot(i));
        }

        return list;
    }

    @Override
    public List<Slot> getCraftingSlots(FletchingTableContainerMenu handler) {
        List<Slot> list = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            list.add(handler.getSlot(i));
        }
        return list;
    }

    @Override
    public @Nullable Slot getOutputSlot(FletchingTableContainerMenu handler) {
        return handler.getSlot(0);
    }

    @Override
    public boolean supportsRecipe(EmiRecipe recipe) {
        return recipe.getCategory() == FletcheryEmiPlugin.FLETCHING;
    }
}
