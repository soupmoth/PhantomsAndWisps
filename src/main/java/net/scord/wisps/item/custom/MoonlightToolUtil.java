package net.scord.wisps.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.TagKey;
import net.scord.wisps.WispsMod;
import net.scord.wisps.effect.ModEffects;

public class MoonlightToolUtil {

    public static final float MOONLIGHT_BONUS = 5f;

    /**This method applies Moonlight damage to an enemy.
     * Moonlight damage is Magical, and the only way (i know of) to seperate these
     * two instances of damage is through the application of an instant
     * status effect.
     *
     * @param target
     * @param attacker
     * @param bonus
     * @return
     */
    public static void applyMoonlightDamage (LivingEntity target, LivingEntity attacker, int bonus){
        if (target.world.isNight() || attacker.hasStatusEffect(ModEffects.MOONLIGHT)) {
            target.addStatusEffect(new StatusEffectInstance(ModEffects.MOON_DAMAGE, 1, bonus), attacker);
            if (attacker instanceof PlayerEntity) {
                target.world.playSound((PlayerEntity) attacker, target.getBlockPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.NEUTRAL, 1f, 1f);
            }

        }
    }



    /*
    This method determines if a bonus should be applied to the mining speed.
    For reference, GOLD is 12f, Diamond is 8f, and Netherite is 9f
    As Moonlight tools will be 7f, the ideal is to lay between Netherite and Gold in terms
    of effectiveness due to the limitations of the tool.
     */
    public static float getMoonlightMiningSpeed(Boolean moonlight, BlockState state, TagKey<Block> effectiveBlocks) {
        if (state.isIn(effectiveBlocks)) {
            return getMoonlightMiningSpeed(moonlight);
        }
        return 0f;
    }

    public static float getMoonlightMiningSpeed(Boolean moonlight) {
        if (moonlight) {
            return MOONLIGHT_BONUS;
        }
        return 0f;
    }



}
