package io.github.lbowenwest.fletchery.forge;

import dev.architectury.platform.forge.EventBuses;
import io.github.lbowenwest.fletchery.Fletchery;
import io.github.lbowenwest.fletchery.FletcheryIdentifier;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FletcheryIdentifier.MOD_ID)
public class FletcheryForge {
    public FletcheryForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(FletcheryIdentifier.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Fletchery.init();
    }
}
