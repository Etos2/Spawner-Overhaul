package etos.spawneroverhaul.mixin;

import org.spongepowered.asm.mixin.Mixin;

import etos.spawneroverhaul.extension.ExtendedSpawnerBlockEntity;
import etos.spawneroverhaul.extension.ExtendedSpawnerLogic;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(MobSpawnerBlockEntity.class)
public abstract class MobSpawnerBlockEntityMixin implements ExtendedSpawnerBlockEntity {

    @Override
    public void triggerBreak(World world, BlockPos pos) {
        this.triggerBreak(world, pos, ExtendedSpawnerBlockEntity.DEFAULT_BREAK_DELAY);
    }

    @Override
    public void triggerBreak(World world, BlockPos pos, int delay) {
        ExtendedSpawnerLogic logic = this.getLogic();
        if (logic.getBreakDelay() < 0) {
            world.playSound(null, pos, SoundEvents.BLOCK_TRIAL_SPAWNER_OMINOUS_ACTIVATE, SoundCategory.BLOCKS, 1.f,
                    0.75f);
            this.getLogic().triggerBreak(delay);
            this.getTarget().markDirty();
        }
    }

    @Override
    public void triggerBreakRejected(World world, BlockPos pos) {
        ServerWorld serverWorld = (ServerWorld) world;
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;
        serverWorld.playSound(null, pos, SoundEvents.BLOCK_TRIAL_SPAWNER_SPAWN_MOB, SoundCategory.BLOCKS);
        serverWorld.spawnParticles(ParticleTypes.FLAME, x, y, z, 25, 0.35, 0.35, 0.35, 0.);
    }

    private MobSpawnerBlockEntity getTarget() {
        return (MobSpawnerBlockEntity) (Object) this;
    }

    private ExtendedSpawnerLogic getLogic() {
        return (ExtendedSpawnerLogic) (Object) this.getTarget().getLogic();
    }
}
