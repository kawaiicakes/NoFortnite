package io.github.kawaiicakes.nomorefortnite;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import static io.github.kawaiicakes.nomorefortnite.Config.CONFIG;

@Mod(NoMoreFortnite.MOD_ID)
public class NoMoreFortnite
{
    public static final String MOD_ID = "nomorefortnite";

    public NoMoreFortnite()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CONFIG);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(PvPHandler.class);
    }
}
