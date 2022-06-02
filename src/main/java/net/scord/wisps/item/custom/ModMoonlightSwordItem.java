package net.scord.wisps.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.world.World;
import net.scord.wisps.effect.ModEffects;

public class ModMoonlightSwordItem extends SwordItem {

    private boolean moonlight = false;

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity) {
            moonlight = ((PlayerEntity) entity).hasStatusEffect(ModEffects.MOONLIGHT);
        }

    }

    public ModMoonlightSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        MoonlightToolUtil.applyMoonlightDamage(target, attacker, 25);

        return super.postHit(stack, target, attacker);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        //checking if the float is greater than 1 from this.
        float supe = super.getMiningSpeedMultiplier(stack, state);

        if (supe > 1f) {
            return supe + MoonlightToolUtil.getMoonlightMiningSpeed(moonlight);
        }
        return supe;


    }


}
