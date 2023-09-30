package io.github.kawaiicakes.nomorefortnite.handlers;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import static com.teampotato.moderninhibited.ModernInhibited.INHIBITED;
import static io.github.kawaiicakes.nomorefortnite.Config.*;
import static io.github.kawaiicakes.nomorefortnite.NoMoreFortnite.COMBAT_LOG;
import static io.github.kawaiicakes.nomorefortnite.NoMoreFortnite.LIGMA_BALLS;

/**
 * Contains event listeners which create mod functionality.
 */
public class PvPHandler {
    /**
     * <code>LivingDamageEvent</code> is used as opposed to <code>PlayerEvent.AttackEntityEvent</code> to hopefully
     * catch all cases where a player damages another player. Theoretically works so long as mods implementing weapons
     * correctly assign a <code>DamageSource</code> with source and target.
     * @param event the <code>LivingDamageEvent</code> being checked for a player-vs-player interaction.
     */
    @SubscribeEvent
    public static void livingDamageEvent(LivingDamageEvent event) {
        if (!(event.getEntity().level.isClientSide())) {
            //TODO: configurable option to toggle ability to remove inhibited effect (i.e. w/ milk)
            //TODO: allow entity target types to be configured? (shooting at a tank would not put the shooter in combat by default)
            if (event.getSource().getEntity() instanceof ServerPlayer attacker &&
                    event.getEntity() instanceof ServerPlayer target) {

                if (INHIBIT_ATTACKER.get()) inhibitPlayer(attacker, TIME_INHIBIT_ATTACKER.get(), NOTIFY_ATTACKER.get());
                if (INHIBIT_TARGET.get()) inhibitPlayer(target, TIME_INHIBIT_TARGET.get(), NOTIFY_TARGET.get());
            }
        }
    }

    @SubscribeEvent
    public static void onDisconnectEvent(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) killPlayerIfInCombat(player);
    }

    private static void killPlayerIfInCombat(ServerPlayer player) {
        if (!player.level.isClientSide()) {
            if (player.hasEffect(COMBAT_LOG.get())) {
                player.level.playLocalSound(player.getX(), player.getY(), player.getZ(), LIGMA_BALLS.get(), player.getSoundSource(), 5.0F, 1.0F, false);
                player.kill();
            }
        }
    }

    private static void inhibitPlayer(@NotNull ServerPlayer player, double time, boolean notify) {
        if (!(player.gameMode.isCreative())) {
            final double tps = 20;

            player.addEffect(new MobEffectInstance(INHIBITED.get(), (int) Math.ceil(time * tps), 0,
                    false, false, true));

            if (notify && !(player.hasEffect(INHIBITED.get()))) {
                player.sendSystemMessage(Component.translatable("chat.nomorefortnite.inhibited").withStyle(ChatFormatting.RED), true);
            }
        }
    }
}
