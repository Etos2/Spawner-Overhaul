package etos.spawneroverhaul.mixin;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.spawner.MobSpawnerEntry;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.block.spawner.MobSpawnerLogic;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.dynamic.Range;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import etos.spawneroverhaul.extension.ExtendedSpawnerLogic;

@Mixin(MobSpawnerLogic.class)
public abstract class MobSpawnerLogicMixin implements ExtendedSpawnerLogic {
    private final String NBT_KEY = "BreakDelay";
    private static final int BREAKING_SPAWN_DELAY = 10;
    private static final int BREAKING_SPAWN_COUNT = 2;
    private static final int BREAKING_MAX_NEARBY_ENTITIES = 24;
    private static final int BREAKING_REQUIRED_PLAYER_RANGE = 64;

    private int breakDelay = -1;

    @Shadow
    private int spawnDelay;
    @Shadow
    @Nullable
    private MobSpawnerEntry spawnEntry;
    @Shadow
    private int minSpawnDelay;
    @Shadow
    private int maxSpawnDelay;
    @Shadow
    private int spawnCount;
    @Shadow
    private int maxNearbyEntities;
    @Shadow
    private int requiredPlayerRange;

    @Inject(method = "readData", at = @At("TAIL"))
    public void readData(@Nullable World world, BlockPos pos, ReadView view, CallbackInfo info) {
        this.breakDelay = view.getInt(NBT_KEY, -1);
    }

    @Inject(method = "writeData", at = @At("TAIL"))
    public void writeData(WriteView view, CallbackInfo info) {
        view.putInt(NBT_KEY, this.breakDelay);
    }

    @Inject(method = "serverTick", at = @At("HEAD"))
    public void serverTick(ServerWorld world, BlockPos pos, CallbackInfo info) {
        int breakDelay = this.getBreakDelay();
        if (breakDelay > 0) {
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + 0.5;
            world.spawnParticles(ParticleTypes.FLAME, x, y, z, 2, 0.35, 0.35, 0.35, 0.);
            this.setBreakDelay(--breakDelay);
        } else if (breakDelay == 0) {
            world.breakBlock(pos, false);
            return;
        }
    }

    @Override
    public int getBreakDelay() {
        return this.breakDelay;
    }

    @Override
    public void setBreakDelay(int delay) {
        this.breakDelay = delay;
    }

    @Override
    public void triggerBreak(int delay) {
        if (this.getBreakDelay() < 0) {
            this.setBreakDelay(delay);
            spawnDelay = 20;
            minSpawnDelay = BREAKING_SPAWN_DELAY;
            maxSpawnDelay = BREAKING_SPAWN_DELAY;
            spawnCount = BREAKING_SPAWN_COUNT;
            maxNearbyEntities = BREAKING_MAX_NEARBY_ENTITIES;
            requiredPlayerRange = BREAKING_REQUIRED_PLAYER_RANGE;

            if (this.spawnEntry != null) {
                Range<Integer> range = new Range<Integer>(0, 15);
                this.spawnEntry = new MobSpawnerEntry(
                        this.spawnEntry.getNbt(),
                        Optional.of(new MobSpawnerEntry.CustomSpawnRules(range, range)),
                        this.spawnEntry.getEquipment());
            }
        }
    }
}