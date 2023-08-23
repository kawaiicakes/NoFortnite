package io.github.kawaiicakes.nofortnite;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.teampotato.moderninhibited.ModernInhibited.INHIBITED;

public class CombatEventHandler {
    @SubscribeEvent
    public static void livingDamageEvent(LivingDamageEvent event) {
        if (!(event.getEntity().level.isClientSide())) {

            //TODO: config
            if (event.getSource().getEntity() instanceof ServerPlayer source &&
                    event.getEntity() instanceof ServerPlayer target) {
                target.addEffect(new MobEffectInstance(INHIBITED.get(), 1200, 1));
                source.addEffect(new MobEffectInstance(INHIBITED.get(), 1200, 1));

                target.sendSystemMessage(Component.translatable("anti_fortnite"), true);
                source.sendSystemMessage(Component.translatable("anti_fortnite"), true);
            }
        }
    }
}
