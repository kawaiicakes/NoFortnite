package io.github.kawaiicakes.nomorefortnite;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.TranslatableComponent;
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
        if (event.getEntity().level.isClientSide()) return;
        if (event.getSource() == null) return;

        if (!(event.getSource().getEntity() instanceof ServerPlayer attacker) || !(event.getEntity() instanceof ServerPlayer target)) return;
        applyDebuffsToPlayer(attacker, INHIBIT_ATTACKER.get(), TIME_INHIBIT_ATTACKER.get(), NOTIFY_ATTACKER.get());
        applyDebuffsToPlayer(target, INHIBIT_TARGET.get(), TIME_INHIBIT_TARGET.get(), NOTIFY_TARGET.get());
    }

    private static void applyDebuffsToPlayer(ServerPlayer player, boolean inhibitPlayer, double inhibitTime, boolean notifyPlayer) {
        if (player.gameMode.isCreative()) return;
        if (notifyPlayer) notifyPlayer(player, inhibitTime);
        if (inhibitPlayer) inhibitPlayer(player, timeInTicks(inhibitTime));
    }

    private static void inhibitPlayer(@NotNull ServerPlayer player, int time) {
        player.addEffect(new MobEffectInstance(INHIBITED.get(), time, 0,
                false, false, true));
    }

    private static void notifyPlayer(@NotNull ServerPlayer player, double inhibitTime) {
        if ((player.hasEffect(INHIBITED.get()))) return;
        player.sendMessage(
                new TranslatableComponent("chat.nomorefortnite.inhibited", inhibitTime).withStyle(ChatFormatting.RED), Util.NIL_UUID);
    }

    private static int timeInTicks(double time) {
        return (int) Math.ceil(time * 20);
    }
}
