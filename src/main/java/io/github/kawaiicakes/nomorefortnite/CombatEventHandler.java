package io.github.kawaiicakes.nomorefortnite;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.teampotato.moderninhibited.ModernInhibited.INHIBITED;
import static io.github.kawaiicakes.nomorefortnite.NoMoreFortnite.CONFIG;

public class CombatEventHandler {
    @SubscribeEvent
    public static void livingDamageEvent(LivingDamageEvent event) {
        if (!(event.getEntity().level.isClientSide())) {

            //TODO: configurable option to toggle ability to remove inhibited effect (i.e. w/ milk)
            if (event.getSource().getEntity() instanceof ServerPlayer source &&
                    event.getEntity() instanceof ServerPlayer target) {
                // I hate magic numbers; even trivial ones like this!
                // Number of ticks per second.
                final int TPS = 20;

                if (CONFIG.NOTIFY_ATTACKER && !source.hasEffect(INHIBITED.get()))
                    source.sendSystemMessage(Component.translatable("anti_fortnite").withStyle(ChatFormatting.RED), true);
                if (CONFIG.NOTIFY_TARGET && !target.hasEffect(INHIBITED.get()))
                    target.sendSystemMessage(Component.translatable("anti_fortnite").withStyle(ChatFormatting.RED), true);

                if (CONFIG.INHIBIT_ATTACKER) source.addEffect(new MobEffectInstance(INHIBITED.get(), CONFIG.INHIBIT_TIMER_ATTACKER * TPS, 0, false, false, true));
                if (CONFIG.INHIBIT_TARGET) target.addEffect(new MobEffectInstance(INHIBITED.get(), CONFIG.INHIBIT_TIMER_TARGET * TPS, 0, false, false, true));
            }
        }
    }
}
