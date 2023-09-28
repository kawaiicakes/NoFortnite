package io.github.kawaiicakes.nomorefortnite;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class CombatLogEffect extends MobEffect {
        protected CombatLogEffect() {
            super(MobEffectCategory.HARMFUL, 1116526);
        }

        public boolean isBeneficial() {
            return false;
        }
}
