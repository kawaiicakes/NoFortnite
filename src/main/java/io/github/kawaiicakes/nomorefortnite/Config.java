package io.github.kawaiicakes.nomorefortnite;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static ForgeConfigSpec CONFIG;

    public static ForgeConfigSpec.DoubleValue TIME_INHIBIT_ATTACKER, TIME_INHIBIT_TARGET, TIME_COMBATLOG_ATTACKER,
            TIME_COMBATLOG_TARGET;
    public static ForgeConfigSpec.BooleanValue INHIBIT_ATTACKER, INHIBIT_TARGET, COMBATLOG_ATTACKER, COMBATLOG_TARGET,
            NOTIFY_ATTACKER, NOTIFY_TARGET;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("No more Fortnite! No more $19 cards!").push("Anti-Fortnite");

        INHIBIT_ATTACKER = builder.define("attacker should be inhibited in pvp", true);
        INHIBIT_TARGET = builder.define("target should be inhibited in pvp", true);
        TIME_INHIBIT_ATTACKER = builder
                .comment("The time in seconds to inhibit the attacker when they enter PvP.")
                .defineInRange("attacker inhibition timer", 60.00, 1.00, Double.MAX_VALUE);
        TIME_INHIBIT_TARGET = builder
                .comment("The time in seconds to inhibit the target when they enter PvP.")
                .defineInRange("target inhibition timer", 60.00, 1.00, Double.MAX_VALUE);

        builder.pop();
        builder.push("Combatlogging");

        COMBATLOG_ATTACKER = builder.define("attacker should not be able to combatlog", true);
        COMBATLOG_TARGET = builder.define("target should not be able to combatlog", true);
        TIME_COMBATLOG_ATTACKER = builder
                .comment("The time in seconds, for the attacker, to no longer be combatlogged.")
                .defineInRange("attacker combatlog timer", 60.00, 1.00, Double.MAX_VALUE);
        TIME_COMBATLOG_TARGET = builder
                .comment("The time in seconds, for the target, to no longer be combatlogged.")
                .defineInRange("target combatlog timer", 60.00, 1.00, Double.MAX_VALUE);

        builder.pop();
        builder.comment("Any mod that makes a message appear when you enter PvP is likely to break this mod's notifications.").push("Notification");

        NOTIFY_ATTACKER = builder
                .comment("Whether the attacker should be notified that they are in PvP.")
                .define("notify attacker", true);
        NOTIFY_TARGET = builder
                .comment("Whether the target should be notified that they are in PvP.")
                .define("notify target", true);

        CONFIG = builder.build();
    }
}
