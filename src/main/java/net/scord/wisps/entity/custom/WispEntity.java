package net.scord.wisps.entity.custom;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;

import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.TagKey;
import net.minecraft.util.annotation.Debug;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import net.scord.wisps.WispsMod;
import net.scord.wisps.effect.ModEffects;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.CallbackI;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.*;

public class WispEntity extends PassiveEntity implements IAnimatable, Angerable {
    private AnimationFactory factory = new AnimationFactory(this);

    private static final int ANGER_TOLERANCE = 100;

    private static final TrackedData<Integer> ANGER = DataTracker.registerData(WispEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> REDSHIFT = DataTracker.registerData(WispEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> FADETIME = DataTracker.registerData(WispEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> ISFADING = DataTracker.registerData(WispEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private static final TrackedData<Boolean> MIGRATION = DataTracker.registerData(WispEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


    @Nullable
    private UUID angryAt;


    public WispEntity(EntityType<? extends WispEntity> entityType, World world) {
        super((EntityType<? extends PassiveEntity>)entityType, world);
        this.moveControl = new FlightMoveControl(this, 5, true);
        this.lookControl = new WispLookControl(this);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER, 1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0f);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0f);
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wisp.float", true));
        return  PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ANGER, 0);
        this.dataTracker.startTracking(REDSHIFT, 0);
        this.dataTracker.startTracking(FADETIME, 0);
        this.dataTracker.startTracking(ISFADING, false);
        this.dataTracker.startTracking(MIGRATION, false);
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        float weighting = 0f;
        if (world.getBlockState(pos).isAir()) {
            weighting += 30.0f;
        }
        if (world.getLightLevel(LightType.SKY, pos) > 12 && world.getLightLevel(LightType.BLOCK, pos) < 4) {
            weighting += 20.0f;
        }
        if (getMigration()) {
            if (pos.getX() < this.getX()) {
                weighting *= 5f;
            }
            double heightDiff = Math.abs(pos.getY()-this.getY());

            if (heightDiff < 2) {
                //weighting *= 2f;
            }
        }

        return weighting;
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }


    @Override
    protected void initGoals() {
       // this.goalSelector.add(1, new WispMigrateWest());
        this.goalSelector.add(0, new WispWanderAroundGoal());
        this.goalSelector.add(2, new SwimGoal(this));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.writeAngerToNbt(nbt);
        this.setFadeTime(nbt.getInt("Fade Time"));
        this.setFading(nbt.getBoolean("Is Fading"));
        this.setRedShift(nbt.getInt("Red Shift"));
        this.setMigration(nbt.getBoolean("Migration"));

    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.readAngerFromNbt(this.world, nbt);
        nbt.putInt("Fade Time", this.getFadeTime());
        nbt.putBoolean("Is Fading", this.isFading());
        nbt.putInt("Red Shift", this.getRedShift());
        nbt.putBoolean("Migration", this.getMigration());

    }

    public int getFadeTime() {
        return this.dataTracker.get(FADETIME);
    }

    public void setFadeTime(int fadeTime) {
        this.dataTracker.set(FADETIME, fadeTime);
    }

    public boolean getMigration() {
        return this.dataTracker.get(MIGRATION);
    }

    public void setMigration(boolean migration) {
        this.dataTracker.set(MIGRATION, migration);
    }


    public boolean isFading() {
        return this.dataTracker.get(ISFADING);
    }


    public void setFading(boolean fadeTime) {
        this.dataTracker.set(ISFADING, fadeTime);
    }

    /**
     * Fades the wisp out of existence. Is meant to be
     */
    public void fade() {
        if (!this.isDead()) {


            setFadeTime(getFadeTime()+1);

            if (getFadeTime() >= 100) {
                remove(RemovalReason.DISCARDED);
            }
        }

    }


    @Override
    public void tick() {
        super.tick();


        if (!this.world.isClient) {
            if (world.getTimeOfDay() > 19000) {
                if (!(hasCustomName() && world.isSkyVisible(getBlockPos()))) {
                    setFading(true);
                }

            }
        }

        if (isFading()) {
            fade();
        }

    }

    @Override
    public void mobTick() {
        if (!this.world.isClient) {
            if (angryAt != null) {
                setRedShift(Math.min(ANGER_TOLERANCE, getRedShift()+1));
            } else {
                setRedShift(Math.max(0, getRedShift()-1));;
            }

            this.tickAngerLogic((ServerWorld) this.world, false);

            //Angering logic (determines how the Wisp selects what it gets angry at)
            if (!isFading()) {
                boolean continueAnger = false;

                if (age % 5 == 0 || angryAt != null) {
                    if (!hasCustomName()) {
                        for (PlayerEntity playerEntity : world.getPlayers(TargetPredicate.DEFAULT, this,
                                calculateBoundingBox().expand(8, 5, 8))) {
                            if (angryAt == null) {
                                continueAnger = true;

                                int ticksSinceRest = MathHelper.clamp(((ServerPlayerEntity) playerEntity).getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
                                if (!playerEntity.hasStatusEffect(ModEffects.HUNT) && ticksSinceRest > 72000) {
                                    setAngerTime(ANGER_TOLERANCE);
                                    setAngryAt(playerEntity.getUuid());


                                    WispsMod.LOGGER.debug("Wisp: " + getUuidAsString() + " locked on to" + getAngryAt().toString());
                                    break;
                                }
                            }
                            else if (playerEntity == ((ServerWorld)world).getEntity(getAngryAt())) {
                                continueAnger = true;
                            }
                        }
                    }
                }
                if (continueAnger == false) {
                    stopAnger();
                }
                if (getAngerTime() == 1) {
                    applyHunt();
                    setFading(true);
                }
            }
        }
    }


    public void applyHunt() {
        ((PlayerEntity) ((ServerWorld)world).getEntity(angryAt)).addStatusEffect
                (new StatusEffectInstance(ModEffects.HUNT, 60*20, 0, true, true));
        stopAnger();

    }



    @Debug
    public GoalSelector getGoalSelector() {
        return this.goalSelector;
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
    }



    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient) {

        }
    }



    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.15f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 8.0);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world){

            @Override
            public boolean isValidPosition(BlockPos pos) {
                return !this.world.getBlockState(pos.down()).isAir();
            }

            @Override
            public void tick() {

                super.tick();
            }
        };
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(false);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }


    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_BEE_HURT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_BEE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BEE_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }


    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        if (this.isBaby()) {
            return dimensions.height * 0.5f;
        }
        return dimensions.height * 0.5f;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }



    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (!this.world.isClient) {

        }
        return super.damage(source, amount);
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    protected void swimUpward(TagKey<Fluid> fluid) {
        this.setVelocity(this.getVelocity().add(0.0, 0.01, 0.0));
    }

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, 0.5f * this.getStandingEyeHeight(), this.getWidth() * 0.2f);
    }

    @Override
    public int getAngerTime() {
        return this.dataTracker.get(ANGER);
    }

    @Override
    public void setAngerTime(int angerTime) {
        this.dataTracker.set(ANGER, angerTime);
    }

    public int getRedShift() {
        return this.dataTracker.get(REDSHIFT);
    }

    public void setRedShift(int redShift) {
        this.dataTracker.set(REDSHIFT, redShift);
    }

    @Override
    @Nullable
    public UUID getAngryAt() {
        return this.angryAt;
    }

    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TOLERANCE);
    }


    class WispLookControl
            extends LookControl {
        WispLookControl(MobEntity entity) {
            super(entity);
        }

        @Override
        public void tick() {

            super.tick();
        }
    }



    class WispWanderAroundGoal
            extends Goal {

        WispWanderAroundGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            return WispEntity.this.navigation.isIdle() && WispEntity.this.random.nextInt(10) == 0;
        }

        @Override
        public boolean shouldContinue() {
            return WispEntity.this.navigation.isFollowingPath();
        }

        @Override
        public void start() {
            Vec3d vec3d = this.getRandomLocation();
            if (vec3d != null) {
                WispEntity.this.navigation.startMovingAlong(WispEntity.this.navigation.findPathTo(new BlockPos(vec3d), 1), 1.0);
            }
        }

        @Nullable
        private Vec3d getRandomLocation() {
            Vec3d vec3d2;
            vec3d2 = WispEntity.this.getRotationVec(0.0f);
            int i = 8;


            Vec3d vec3d3 = AboveGroundTargeting.find(WispEntity.this, 8, 7, vec3d2.x, vec3d2.z, 1.5707964f, 3, 1);
            if (vec3d3 != null) {
                return vec3d3;
            }
            return NoPenaltySolidTargeting.find(WispEntity.this, 8, 4, -2, vec3d2.x, vec3d2.z, 1.5707963705062866);
        }
    }


    class WispMigrateWest extends Goal {
        int ticks;

        WispMigrateWest() {
            this.ticks = WispEntity.this.world.random.nextInt(10);
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean shouldContinue() {
            return WispEntity.this.navigation.isFollowingPath();
        }

        @Override
        public boolean canStart() {
            return WispEntity.this.getMigration();
        }

        @Override
        public void start() {
            Vec3d vec3d = this.getRandomLocation();
            if (vec3d != null) {
                WispEntity.this.navigation.startMovingAlong(WispEntity.this.navigation.findPathTo(new BlockPos(vec3d), 2), 1);
            }
        }


        @Nullable
        private Vec3d getRandomLocation() {
            Vec3d vec3d2;
            vec3d2 = WispEntity.this.getRotationVec(0.0f);
            int displacement = 5;


            Vec3d vec3d3 = AboveGroundTargeting.find(WispEntity.this, 6, 64, vec3d2.x, vec3d2.z, 1.5707964f, 64, 30);
            return vec3d3;
        }
    }


}
