package net.scord.wisps.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.world.World;
import net.scord.wisps.effect.ModEffects;
import net.scord.wisps.item.ModItemsMaterials;

public class ModMoonlightAxeItem extends AxeItem  {
    private TagKey<Block> effectiveBlocks = BlockTags.AXE_MINEABLE;
    private boolean moonlight = false;

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity) {
            moonlight = ((PlayerEntity) entity).hasStatusEffect(ModEffects.MOONLIGHT);
        }

    }


    public ModMoonlightAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        MoonlightToolUtil.applyMoonlightDamage(target, attacker, 1);

        return super.postHit(stack, target, attacker);
    }


    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return super.getMiningSpeedMultiplier(stack, state) + MoonlightToolUtil.getMoonlightMiningSpeed(moonlight, state, this.effectiveBlocks);

    }


}
