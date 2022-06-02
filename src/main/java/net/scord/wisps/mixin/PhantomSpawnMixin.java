package net.scord.wisps.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.spawner.PhantomSpawner;
import net.scord.wisps.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

/**This Mixin alters the spawn behaviour of the Phantoms
 * The ideal spawning behaviour
 * -Phantoms occassionally just Spawn. They act passively and do nothing but vibe. This should be fairly rare.
 * -Phantoms spawn very, very frequently when
 */

@Mixin(PhantomSpawner.class)
public abstract class PhantomSpawnMixin {
    @Accessor abstract int getCooldown();
    @Accessor abstract void setCooldown(int cd);
    final int TICKS_IN_SECOND = 20;
    final int BASE_COOLDOWN = 10 * TICKS_IN_SECOND;
    final int VARIATION_COOLDOWN = 5 * TICKS_IN_SECOND;

    /**
     * This method is intended to rewrite the Phantom spawning behaviour.
     *
     * @param world         the world (wow)
     * @param spawnMonsters are we spawning a monster? (yes)
     * @param spawnAnimals  are we spawning an animal (no)
     * @reason The change is extremely drastic, so Injection was unlikely to be satisfactory for my needs here.
     * @author me nerds
     */
    @Inject(method = "spawn", at = @At(value = "HEAD"))
    public void spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals, CallbackInfoReturnable<Integer> cir) {

        if (!spawnMonsters) {
            cir.setReturnValue(0);
        }
        if (!world.getGameRules().getBoolean(GameRules.DO_INSOMNIA)) {
            cir.setReturnValue(0);
        }
        Random random = world.random;

        int cooldown = getCooldown()-1;
        setCooldown(cooldown);
        if (cooldown > 0) {
            cir.setReturnValue(0);
        }
        cooldown += (BASE_COOLDOWN + random.nextInt(VARIATION_COOLDOWN));
        setCooldown(cooldown);
        if (world.getAmbientDarkness() < 5 && world.getDimension().hasSkyLight()) {
            cir.setReturnValue(0);
        }
        int i = 0;
        for (PlayerEntity playerEntity : world.getPlayers()) {
            FluidState fluidState;
            BlockState blockState;
            BlockPos blockPos2;
            LocalDifficulty localDifficulty;
            if (playerEntity.isSpectator()) continue;
            BlockPos blockPos = playerEntity.getBlockPos();
            //If we can see the sky, are above sea level, and
            if (world.getDimension().hasSkyLight() && (blockPos.getY() < world.getSeaLevel() || !world.isSkyVisible(blockPos))) continue;

            ServerStatHandler serverStatHandler = ((ServerPlayerEntity)playerEntity).getStatHandler();
            int timeSinceLastRest = MathHelper.clamp(serverStatHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
            int ticksInDay = 24000;

            //If the player has hunt, or the player is SEVERELY tired, then give a small chance of spotting a passive Phantom. (around 8-9% of it going through to the next step)
            if (!playerEntity.hasStatusEffect(ModEffects.HUNT) && (Math.min(random.nextInt(timeSinceLastRest), ticksInDay * 11) < ticksInDay * 10)) continue;

            int huntLevel = -1;
            if (playerEntity.hasStatusEffect(ModEffects.HUNT)) {
                huntLevel = playerEntity.getStatusEffect(ModEffects.HUNT).getAmplifier();
            }

            if (!SpawnHelper.isClearForSpawn(world, blockPos2 = blockPos.up(20 + random.nextInt(15)).east(-10 + random.nextInt(21)).south(-10 + random.nextInt(21)), blockState = world.getBlockState(blockPos2), fluidState = world.getFluidState(blockPos2), EntityType.PHANTOM)) continue;
            EntityData entityData = null;
            //initialise some data before we spawn Phantoms.
            localDifficulty = world.getLocalDifficulty(blockPos);
            //spawn atleast 1 Phantom, with a chance to spawn 1+HuntLevel more per group.
            int l = 1 + random.nextInt(Math.min(4, 1 + (int) Math.floor(huntLevel/2))); //L is the number of Phantoms we wish to spawn.

            for (int m = 0; m < l; ++m) {
                PhantomEntity phantomEntity = EntityType.PHANTOM.create(world);
                phantomEntity.refreshPositionAndAngles(blockPos2, 0.0f, 0.0f);
                entityData = phantomEntity.initialize(world, localDifficulty, SpawnReason.NATURAL, entityData, null);
                //randomly scale the size of the Phantom. This is determined between three sizes at maximum, starting at one size.
                int origin = Math.min(16, (int) Math.pow(2, Math.max(0, huntLevel-2)));
                int bound = Math.min(16, (int) Math.pow(2, Math.max(huntLevel, 0)));

                if (origin == bound) {
                    phantomEntity.setPhantomSize(bound);
                }
                else {
                    phantomEntity.setPhantomSize(random.nextInt(origin, bound));
                }

                //drop it into the world...
                world.spawnEntityAndPassengers(phantomEntity);
            }
            i += l;
        }
        cir.setReturnValue(i);;
    }

}
