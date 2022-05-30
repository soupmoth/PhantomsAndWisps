package net.scord.wisps.mixin;

import com.eliotlash.mclib.math.functions.classic.Mod;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.server.dedicated.gui.PlayerStatsGui;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.scord.wisps.WispsMod;
import net.scord.wisps.effect.HuntEffect;
import net.scord.wisps.effect.ModEffects;
import net.scord.wisps.entity.ai.goal.PhantomHuntTarget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**Class that modifies the PhantomEntity FindTargetGoal to change the behaviour
 * so that the Phantom prioritises any Player with the Hunt status condition.
 */
@Mixin(PhantomEntity.class)
abstract class PhantomMixin extends MobEntity {

    @Shadow public abstract int getPhantomSize();

    @Shadow @Final private static TrackedData<Integer> SIZE;
    protected final int BASE_PHANTOM_DAMAGE = 4;
    protected final int BASE_PHANTOM_HEALTH = 10;
    protected final int BASE_PHANTOM_EXP = 10;


    protected PhantomMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }



    @ModifyArg(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V", ordinal = 3), index = 1)
    protected Goal addCustomGoal(Goal goal) {
        return new PhantomHuntTarget<PlayerEntity>((PhantomEntity)(Object)this, PlayerEntity.class);
    }

    @Inject(method = "onSizeChanged", at = @At(value = "TAIL"))
    protected void injectSizeDifferences(CallbackInfo ci) {
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(BASE_PHANTOM_DAMAGE + this.getPhantomSize());
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addPersistentModifier(new EntityAttributeModifier(
                "Size Health Bonus", 2*this.getPhantomSize() - BASE_PHANTOM_HEALTH, EntityAttributeModifier.Operation.ADDITION));
        this.setHealth(getMaxHealth());
    }

    /**
     * Override of isInvisibleTo. This adds the functionality where Phantoms are invisible to all players that are alert or not being hunted
     * @param player
     * @return
     */

    @Override
    public boolean isInvisibleTo(PlayerEntity player) {

            int timeSinceLastRest = MathHelper.clamp(((ClientPlayerEntity) player).getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
            if (player.hasStatusEffect(ModEffects.HUNT) || timeSinceLastRest > 3 * 24000) {
                return false;
            }

       return super.isInvisibleTo(player);
    }

    @Override
    public boolean isInvisible() {
        return true;
    }

    @Override
    protected int getXpToDrop(PlayerEntity player) {
        int i = BASE_PHANTOM_EXP;
        i+= this.getPhantomSize()-1;

        if (player.hasStatusEffect(ModEffects.HUNT)) {
            i += 3 * (1+player.getStatusEffect(ModEffects.HUNT).getAmplifier());
        }
        return i;
    }

    @Override
    protected void dropLoot(DamageSource source, boolean causedByPlayer) {
        super.dropLoot(source, causedByPlayer);
        if (causedByPlayer) {

            if (source.getAttacker() instanceof PlayerEntity) {
                if (((PlayerEntity) source.getAttacker()).getStatusEffect(ModEffects.HUNT).getAmplifier() >= 9) {
                    int chance = this.random.nextInt(100);
                    int i = EnchantmentHelper.getLooting((LivingEntity)source.getAttacker());
                    int j = 0;
                    if (source.isMagic()) j = 2;

                    if (chance + ((i+j) * 2) > 90) {
                        dropItem(Items.DIAMOND);
                    }
                }


            }


        }
    }










}

