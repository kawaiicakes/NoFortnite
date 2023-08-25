package io.github.kawaiicakes.nomorefortnite;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(NoMoreFortnite.MODID)
public class NoMoreFortnite
{
    public static final String MODID = "nomorefortnite";
    public static Config.ConfigEntries CONFIG = Config.loadConfig();

    public NoMoreFortnite()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(CombatEventHandler.class);
    }

    private void setup(final FMLCommonSetupEvent event) {
        CONFIG = Config.loadConfig();
    }
}
