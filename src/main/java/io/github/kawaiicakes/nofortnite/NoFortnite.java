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
    private static final Logger LOGGER = LogUtils.getLogger();
    public NoFortnite()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
    }
}
