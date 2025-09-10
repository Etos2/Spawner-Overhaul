package etos.spawneroverhaul.extension;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ExtendedSpawnerBlockEntity {
    public final int DEFAULT_BREAK_DELAY = 60;
    void triggerBreak(World world, BlockPos pos, int delay);
    void triggerBreak(World world, BlockPos pos);
    void triggerBreakRejected(World world, BlockPos pos);
}
