package net.scord.wisps.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.world.World;
import net.scord.wisps.WispsMod;
import net.scord.wisps.effect.ModEffects;

public class ModMoonlightPickaxeItem extends PickaxeItem {
    private TagKey<Block> effectiveBlocks = BlockTags.PICKAXE_MINEABLE;
    private boolean moonlight = false;

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity) {
            moonlight = ((PlayerEntity) entity).hasStatusEffect(ModEffects.MOONLIGHT);
        }

    }

    public ModMoonlightPickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        MoonlightToolUtil.applyMoonlightDamage(target, attacker, 0);

        return super.postHit(stack, target, attacker);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return (super.getMiningSpeedMultiplier(stack, state) + MoonlightToolUtil.getMoonlightMiningSpeed(moonlight, state, this.effectiveBlocks));

    }
}
