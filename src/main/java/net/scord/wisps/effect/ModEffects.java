package net.scord.wisps.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.scord.wisps.WispsMod;

public class ModEffects {

    public static StatusEffect HUNT;
    public static StatusEffect MOON_DAMAGE;
    public static StatusEffect MOONLIGHT;

    public static StatusEffect registerNightStatusEffect(String name) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(WispsMod.MOD_ID, name),
                new HuntEffect(StatusEffectCategory.HARMFUL, 11141120));
    }

    public static StatusEffect registerStatusEffect(String name) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(WispsMod.MOD_ID, name),
                new MoonlightEffect(StatusEffectCategory.BENEFICIAL, 11141120));
    }

    public static StatusEffect registerMoonDamageStatusEffect(String name) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(WispsMod.MOD_ID, name),
                new InstantMoonDamageEffect(StatusEffectCategory.BENEFICIAL, 11141120));
    }

    public static void registerEffects() {
        WispsMod.LOGGER.info("Trying Effects...");
        HUNT = registerNightStatusEffect("hunt");
        MOON_DAMAGE = registerMoonDamageStatusEffect("moon_damage");
        MOONLIGHT = registerStatusEffect("moonlight");
        WispsMod.LOGGER.info("Registered Effects");
    }

}
