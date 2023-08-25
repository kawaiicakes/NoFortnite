package io.github.kawaiicakes.nofortnite;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(NoFortnite.MODID)
public class NoFortnite
{
    public static final String MODID = "nofortnite";
    public static Config.ConfigEntries CONFIG = Config.loadConfig();

    public NoFortnite()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(CombatEventHandler.class);
    }

    private void setup(final FMLCommonSetupEvent event) {
        CONFIG = Config.loadConfig();
    }
}
