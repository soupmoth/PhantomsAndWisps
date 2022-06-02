package net.scord.wisps.effect;

import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.scord.wisps.WispsMod;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public class HuntEffect extends StatusEffect {

    protected final int END_OF_DAY = 13000;
    protected final int END_OF_NIGHT = 23000;
    protected final int TIME_IN_DAY = 24000;

    public HuntEffect(StatusEffectCategory statusEffectCategory, int colour) {
        super(statusEffectCategory, colour);
    }

    /*
    As Hunt as no real effects per tick, this simply is used to determine duration behaviour.
    Hunt effects are intended to only be granted at night, so this method ensures this.
     */
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        //Effect is immediately cleared if it is considered day.
        if (entity.world.getTimeOfDay() % TIME_IN_DAY < END_OF_DAY || entity.world.getTimeOfDay() % TIME_IN_DAY > END_OF_NIGHT) {
            WispsMod.LOGGER.info(entity.getEntityName() + " lost Hunt effect from Day Time");
            entity.removeStatusEffect(ModEffects.HUNT);
            return;
        }

        //store the current status effect temporarily and its parameters.
        StatusEffectInstance hunt = entity.getStatusEffect(ModEffects.HUNT);
        int huntDuration = hunt.getDuration();
        int huntAmplifier = hunt.getAmplifier();
        long targetTime = (entity.world.getTimeOfDay() % TIME_IN_DAY) + huntDuration;

        //If our duration is extending beyond the end of night time, then reduce the duration.
        if (targetTime >= END_OF_NIGHT) {
            while (targetTime >= END_OF_NIGHT) {
                huntDuration--;
                targetTime = (entity.world.getTimeOfDay() % TIME_IN_DAY) + huntDuration;
            }

            entity.removeStatusEffect(ModEffects.HUNT);
            entity.addStatusEffect(new StatusEffectInstance(ModEffects.HUNT, huntDuration, huntAmplifier));
        }

    }

    //here in case we want to do anything with this.
    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);
    }

    //here in case we want to do anything with this.
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onApplied(entity, attributes, amplifier);
        WispsMod.LOGGER.info(entity.getEntityName() + " received Hunt.");

    }

    //@Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) { return true; };

}
