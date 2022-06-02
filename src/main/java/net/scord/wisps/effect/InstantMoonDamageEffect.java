package net.scord.wisps.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import org.jetbrains.annotations.Nullable;

/*
This class exists to deal magic damage with more flexibility than Instant Damage is in base minecraft.
This should never be brewable, as its too weak to fit with the two amplifier tier system of vanilla. Might be
a nice treasure potion though.
 */
public class InstantMoonDamageEffect extends InstantStatusEffect {

    public InstantMoonDamageEffect(StatusEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (this == ModEffects.MOON_DAMAGE) {
            entity.damage(DamageSource.MAGIC, 1 + amplifier);
        }
    }
}
