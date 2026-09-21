package io.github.lbowenwest.fletchery.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.github.lbowenwest.fletchery.registry.FletcheryRecipeSerializer;
import io.github.lbowenwest.fletchery.registry.FletcheryRecipeType;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class FletchingRecipe implements Recipe<Container> {

    private final ResourceLocation identifier;
    private final NonNullList<Ingredient> input;
    private final ItemStack output;

    public FletchingRecipe(ResourceLocation identifier, NonNullList<Ingredient> input, ItemStack output) {
        this.identifier = identifier;
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(Container container, Level level) {
        for (int i = 0; i < 3; ++i) {
            ItemStack itemStack = container.getItem(i);
            Ingredient expected = this.input.get(i);
            if (!expected.test(itemStack)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        ItemStack result = this.getResultItem(registryAccess).copy();
        if (result.is(Items.TIPPED_ARROW)) {
            ItemStack inputPotion = getInputPotionStack(container);
            if (!inputPotion.isEmpty()) {
                PotionUtils.setPotion(result, PotionUtils.getPotion(inputPotion));
                PotionUtils.setCustomEffects(result, PotionUtils.getCustomEffects(inputPotion));
            }
        }
        return result;
    }

    private ItemStack getInputPotionStack(Container container) {
        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemStack = container.getItem(i);
            if (PotionUtils.getPotion(itemStack) != Potions.EMPTY) {
                return itemStack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.input;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return this.identifier;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FletcheryRecipeSerializer.FLETCHING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return FletcheryRecipeType.FLETCHING.get();
    }

    public static class Serializer implements RecipeSerializer<FletchingRecipe> {

        @Override
        public FletchingRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            JsonArray ingredients = GsonHelper.getAsJsonArray(jsonObject, "ingredients");
            NonNullList<Ingredient> input = NonNullList.create();
            for (int i = 0; i < jsonObject.size(); i++) {
                Ingredient ingredient = Ingredient.fromJson(ingredients.get(i));
                if (!ingredient.isEmpty()) {
                    input.add(ingredient);
                }
            }

            if (input.isEmpty()) {
                throw new JsonParseException("No ingredients for fletching table");
            } else if (input.size() > 3) {
                throw new JsonParseException("Too many ingredients for fletching table");
            }

            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
            return new FletchingRecipe(resourceLocation, input, output);
        }

        @Override
        public FletchingRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            final var ingredients = NonNullList.withSize(friendlyByteBuf.readVarInt(), Ingredient.EMPTY);
            ingredients.replaceAll(ignored -> Ingredient.fromNetwork(friendlyByteBuf));
            return new FletchingRecipe(resourceLocation, ingredients, friendlyByteBuf.readItem());
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, FletchingRecipe recipe) {
            friendlyByteBuf.writeVarInt(recipe.input.size());
            for (Ingredient ingredient : recipe.input) {
                ingredient.toNetwork(friendlyByteBuf);
            }
            friendlyByteBuf.writeItem(recipe.output);
        }
    }
}
