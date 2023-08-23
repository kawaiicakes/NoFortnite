package io.github.kawaiicakes.nofortnite;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(NoFortnite.MODID)
public class NoFortnite
{
    public static final String MODID = "nofortnite";

    public NoFortnite()
    {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(CombatEventHandler.class);
    }
}
