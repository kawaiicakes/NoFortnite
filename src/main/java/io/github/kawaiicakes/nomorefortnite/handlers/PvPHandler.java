package io.github.kawaiicakes.nomorefortnite.handlers;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import static com.teampotato.moderninhibited.ModernInhibited.INHIBITED;
import static io.github.kawaiicakes.nomorefortnite.Config.*;

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
            if (event.getSource().getEntity() instanceof ServerPlayer source &&
                    event.getEntity() instanceof ServerPlayer target) {

                inhibitAttacker(source);
                inhibitTarget(target);
            }
        }
    }

    private static void inhibitAttacker(@NotNull ServerPlayer attacker) {
        if (INHIBIT_ATTACKER.get() && !(attacker.gameMode.isCreative())) {
            final double tps = 20;

            attacker.addEffect(new MobEffectInstance(INHIBITED.get(), (int) Math.ceil(TIME_INHIBIT_ATTACKER.get() * tps), 0,
                    false, false, true));

            if (NOTIFY_ATTACKER.get()) {
                attacker.sendSystemMessage(Component.translatable("anti_fortnite").withStyle(ChatFormatting.RED), true);
            }
        }
    }

    private static void inhibitTarget(@NotNull ServerPlayer target) {
        if (INHIBIT_TARGET.get() && !(target.gameMode.isCreative())) {
            final double tps = 20;

            target.addEffect(new MobEffectInstance(INHIBITED.get(), (int) Math.ceil(TIME_INHIBIT_TARGET.get() * tps), 0,
                    false, false, true));

            if (NOTIFY_TARGET.get()) {
                target.sendSystemMessage(Component.translatable("anti_fortnite").withStyle(ChatFormatting.RED), true);
            }
        }
    }

    /* javadoc
     * Stops players from removing Inhibited if the method used fires on the <code>MobEffectEvent.Remove</code> event.
     * @param event the <code>MobEffectEvent.Remove</code> event being listened to for the matching arguments.
     */
    /* FIXME: This poses a problem if someone gets the effect infinitely. Make non-pvp MobEffectInstances removable still?
    @SubscribeEvent
    public static void effectRemovalEvent(MobEffectEvent.Remove event) {
        if (!event.getEntity().getLevel().isClientSide() && !CONFIG.INHIBITION_IS_REMOVABLE) {
            if ((event.getEffect() == INHIBITED.get())
                    && event.getEntity() instanceof ServerPlayer player) {
                event.setCanceled(true);
                player.sendSystemMessage(Component.translatable("no_removal_allowed").withStyle(ChatFormatting.RED), true);
            }
        }
    }
     */
}
