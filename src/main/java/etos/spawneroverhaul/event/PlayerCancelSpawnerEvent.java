package etos.spawneroverhaul.event;

import java.util.ArrayList;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlayerCancelSpawnerEvent implements PlayerBlockBreakEvents.Canceled {
    public static ArrayList<Pair<RegistryKey<World>, BlockPos>> TO_UPDATE_LIST = new ArrayList<Pair<RegistryKey<World>, BlockPos>>();

    @Override
    public void onBlockBreakCanceled(World world, PlayerEntity player, BlockPos pos, BlockState state,
            @Nullable BlockEntity blockEntity) {
        if (!world.isClient()) {
            if (blockEntity != null) {
                if (blockEntity instanceof MobSpawnerBlockEntity) {
                    RegistryKey<World> worldKey = world.getRegistryKey();
                    TO_UPDATE_LIST.add(new Pair<RegistryKey<World>, BlockPos>(worldKey, pos));
                }
            }
        }
    }
}
