package io.github.kawaiicakes.nomorefortnite;

import io.github.kawaiicakes.nomorefortnite.handlers.PvPHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(NoMoreFortnite.MOD_ID)
public class NoMoreFortnite
{
    public static final String MOD_ID = "nomorefortnite";

    public static final DeferredRegister<MobEffect> EFFECT_DEFERRED_REGISTER;

    public static final RegistryObject<MobEffect> COMBAT_LOG;

    public static Config.ConfigEntries CONFIG = Config.loadConfig();

    public NoMoreFortnite()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(PvPHandler.class);
    }

    private void setup(final FMLCommonSetupEvent event) {
        CONFIG = Config.loadConfig();
    }

    static {
        EFFECT_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MOD_ID);
        COMBAT_LOG = EFFECT_DEFERRED_REGISTER.register("combat_logged", CombatLogEffect::new);
    }
}
