package io.github.lbowenwest.fletchery;

import net.minecraft.resources.ResourceLocation;

public class FletcheryIdentifier {
    public static final String MOD_ID = "fletchery";

    public static ResourceLocation of(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
