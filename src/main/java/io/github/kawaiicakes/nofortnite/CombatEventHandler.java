package io.github.kawaiicakes.nofortnite;

import com.teampotato.moderninhibited.InhibitedEffect;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.teampotato.moderninhibited.ModernInhibited.INHIBITED;

public class CombatEventHandler {
    @SubscribeEvent
    public static void livingDamageEvent(LivingDamageEvent event) {
        if (!(event.getEntity().level.isClientSide())) {

            //TODO: config, translation key
            if (event.getSource().getEntity() instanceof ServerPlayer && event.getEntity() instanceof ServerPlayer target) {
                target.addEffect(new MobEffectInstance(INHIBITED.get(), 1200, 1));
                target.sendSystemMessage(Component.translatable("combat.blocked"), true);
            }
        }
    }
}
