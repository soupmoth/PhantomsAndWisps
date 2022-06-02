package net.scord.wisps.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.scord.wisps.WispsMod;

public class ModEffects {

    //Hunt attracts Phantoms
    public static StatusEffect HUNT;

    //Moon Damage is for magic damage from specific sources.
    public static StatusEffect MOON_DAMAGE;

    //Moonlight is the status effect that is applied to someone if its Night and they are
    //carrying any Moonlight tier item, or can be brewed, and it increases the proficiency of
    //those items.
    public static StatusEffect MOONLIGHT;

    public static StatusEffect registerHuntStatusEffect(String name) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(WispsMod.MOD_ID, name),
                new HuntEffect(StatusEffectCategory.HARMFUL, 11141120));
    }

    public static StatusEffect registerStatusEffect(String name) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(WispsMod.MOD_ID, name),
                new MoonlightEffect(StatusEffectCategory.BENEFICIAL, 11141120));
    }

    public static StatusEffect registerMoonDamageStatusEffect(String name) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(WispsMod.MOD_ID, name),
                new InstantMoonDamageEffect(StatusEffectCategory.HARMFUL, 11141120));
    }

    public static void registerEffects() {
        WispsMod.LOGGER.info("Trying Effects...");
        HUNT = registerHuntStatusEffect("hunt");
        MOON_DAMAGE = registerMoonDamageStatusEffect("moon_damage");
        MOONLIGHT = registerStatusEffect("moonlight");
        WispsMod.LOGGER.info("Registered Effects");
    }

}
