package io.github.lbowenwest.fletchery;

import io.github.lbowenwest.fletchery.registry.FletcheryRecipeSerializer;
import io.github.lbowenwest.fletchery.registry.FletcheryRecipeType;

public class Fletchery {
    public static void onInitialize() {
        FletcheryRecipeType.init();
        FletcheryRecipeSerializer.init();
    }
}
