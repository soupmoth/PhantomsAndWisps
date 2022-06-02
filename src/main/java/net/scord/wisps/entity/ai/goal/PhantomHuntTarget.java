package net.scord.wisps.entity.ai.goal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.scord.wisps.effect.ModEffects;

import java.util.Comparator;
import java.util.List;

/*
This class is literally just a copy of the PhantomTarget inner class that we mixin for custom behaviour.
The intention is to have our Phantoms only attack targets that have the Hunt status effect.
 */
public class PhantomHuntTarget<T extends LivingEntity>
        extends Goal {

    private final TargetPredicate PLAYERS_IN_RANGE_PREDICATE = TargetPredicate.createAttackable().setBaseMaxDistance(64.0);
    private int delay = toGoalTicks(20);
    protected final MobEntity mob;
    protected final Class<T> targetClass;

    public PhantomHuntTarget(MobEntity mob, Class<T> targetClass) {
        this.mob = mob;
        this.targetClass = targetClass;

    }

    @Override
    public boolean canStart() {
        if (this.delay > 0) {
            --this.delay;
            return false;
        }
        this.delay = toGoalTicks(60);
        List<PlayerEntity> list = mob.world.getPlayers(this.PLAYERS_IN_RANGE_PREDICATE, mob, mob.getBoundingBox().expand(24.0, 64.0, 24.0));
        if (!list.isEmpty()) {
            list.sort(Comparator.comparing(Entity::getY).reversed());
            for (PlayerEntity playerEntity : list) {
                if (!mob.isTarget(playerEntity, TargetPredicate.DEFAULT) || !playerEntity.hasStatusEffect(ModEffects.HUNT)) continue;
                mob.setTarget(playerEntity);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean shouldContinue() {
        LivingEntity livingEntity = mob.getTarget();
        if (livingEntity != null) {
            return mob.isTarget(livingEntity, TargetPredicate.DEFAULT);
        }
        return false;
    }
}
