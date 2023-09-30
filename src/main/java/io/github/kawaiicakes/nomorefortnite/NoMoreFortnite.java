package io.github.kawaiicakes.nomorefortnite;

import io.github.kawaiicakes.nomorefortnite.handlers.PvPHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static io.github.kawaiicakes.nomorefortnite.Config.CONFIG;

@Mod(NoMoreFortnite.MOD_ID)
public class NoMoreFortnite
{
    public static final String MOD_ID = "nomorefortnite";

    public static final DeferredRegister<MobEffect> EFFECT_DEFERRED_REGISTER;
    public static final DeferredRegister<SoundEvent> SOUND_EVENT_DEFERRED_REGISTER;

    public static final RegistryObject<MobEffect> COMBAT_LOG;
    public static final RegistryObject<SoundEvent> LIGMA_BALLS;

    public NoMoreFortnite()
    {
        EFFECT_DEFERRED_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CONFIG);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(PvPHandler.class);
    }

    static {
        EFFECT_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MOD_ID);
        SOUND_EVENT_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);

        COMBAT_LOG = EFFECT_DEFERRED_REGISTER.register("combat_logged", CombatLogEffect::new);
        LIGMA_BALLS = SOUND_EVENT_DEFERRED_REGISTER
                .register("level.player.death.ligma", () -> new SoundEvent(new ResourceLocation("death_by_ligma")));
    }
}
