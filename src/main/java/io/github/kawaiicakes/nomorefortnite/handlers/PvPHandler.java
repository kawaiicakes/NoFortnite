package io.github.kawaiicakes.nomorefortnite.handlers;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
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
    // FIXME incompatibility with Incapacitated; see Incapacitated's PlayerCapabilityManager L129
    // TODO extract to new subclass
    public static final DamageSource LIGMA = new DamageSource("outOfWorld") {
        @Override
        public @NotNull Component getLocalizedDeathMessage(@NotNull LivingEntity pLivingEntity) {
            return Component.translatable("chat.player.death_by_ligma", pLivingEntity.getDisplayName());
        }
    }.bypassArmor().bypassInvul();

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

                applyDebuffsToPlayer(attacker, INHIBIT_ATTACKER.get(), TIME_INHIBIT_ATTACKER.get(), COMBATLOG_ATTACKER.get(), TIME_COMBATLOG_ATTACKER.get(), NOTIFY_ATTACKER.get());
                applyDebuffsToPlayer(target, INHIBIT_TARGET.get(), TIME_INHIBIT_TARGET.get(), COMBATLOG_TARGET.get(), TIME_COMBATLOG_TARGET.get(), NOTIFY_TARGET.get());
            }
        }
    }

    // FIXME crash on reconnect with Music Triggers (The_Computerizer has been contacted)
    // FIXME sound does not play
    @SubscribeEvent
    public static void onDisconnectEvent(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && !(player.level.isClientSide())) {
            if (player.hasEffect(COMBAT_LOG.get())) {
                player.level.playSound(player, player.getX(), player.getY(), player.getZ(), LIGMA_BALLS.get(), SoundSource.PLAYERS, 2.5F, 1.0F);
                player.getCombatTracker().recordDamage(LIGMA, player.getHealth(), Float.MAX_VALUE);
                player.hurt(LIGMA, Float.MAX_VALUE);
            }
        }
    }

    private static void applyDebuffsToPlayer(ServerPlayer player, boolean inhibitPlayer, double inhibitTime,
                                             boolean combatlogPlayer, double combatlogTime, boolean notifyPlayer) {
        if (!(player.gameMode.isCreative())) {
            if (notifyPlayer) notifyPlayer(player, inhibitPlayer, inhibitTime, combatlogPlayer, combatlogTime);
            if (inhibitPlayer) inhibitPlayer(player, timeInTicks(inhibitTime));
            if (combatlogPlayer) combatlogPlayer(player, timeInTicks(combatlogTime));
        }
    }

    private static void inhibitPlayer(@NotNull ServerPlayer player, int time) {
        player.addEffect(new MobEffectInstance(INHIBITED.get(), time, 0,
                false, false, true));
    }

    private static void combatlogPlayer(@NotNull ServerPlayer player, int time) {
        player.addEffect(new MobEffectInstance(COMBAT_LOG.get(), time, 0,
                false, false, true));
    }

    // FIXME: doesn't work
    private static void notifyPlayer(@NotNull ServerPlayer player, boolean inhibitPlayer, double inhibitTime,
                                     boolean combatlogPlayer, double combatlogTime) {
        if (!(player.hasEffect(INHIBITED.get()) || !(player.hasEffect(COMBAT_LOG.get())))) {
            if (inhibitPlayer && !combatlogPlayer) {
                player.sendSystemMessage(
                        Component.translatable("chat.nomorefortnite.inhibited", inhibitTime).withStyle(ChatFormatting.RED), true);
            } else if (!inhibitPlayer && combatlogPlayer) {
                player.sendSystemMessage(
                        Component.translatable("chat.nomorefortnite.combatlog", combatlogTime).withStyle(ChatFormatting.RED), true);
            } else {
                player.sendSystemMessage(
                        Component.translatable("chat.nomorefortnite.debuffed", inhibitTime, combatlogTime).withStyle(ChatFormatting.RED), true);
            }
        }
    }

    private static int timeInTicks(double time) {
        return (int) Math.ceil(time * 20);
    }
}
