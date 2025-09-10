package etos.spawneroverhaul.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ServerEndTickEvent implements ServerTickEvents.EndTick {

    @Override
    public void onEndTick(MinecraftServer server) {
        if (PlayerCancelSpawnerEvent.TO_UPDATE_LIST.size() > 0) {
            for (Pair<RegistryKey<World>, BlockPos> updatePair : PlayerCancelSpawnerEvent.TO_UPDATE_LIST) {
                World world = server.getWorld(updatePair.getLeft());
                BlockPos pos = updatePair.getRight();
                BlockState blockState = world.getBlockState(pos);
                world.updateListeners(pos, blockState, blockState, Block.REDRAW_ON_MAIN_THREAD);
            }
            PlayerCancelSpawnerEvent.TO_UPDATE_LIST.clear();
        }
    }

}
