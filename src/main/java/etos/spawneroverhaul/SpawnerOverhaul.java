package etos.spawneroverhaul;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import etos.spawneroverhaul.event.PlayerBreakSpawnerEvent;
import etos.spawneroverhaul.event.PlayerCancelSpawnerEvent;
import etos.spawneroverhaul.event.ServerEndTickEvent;

public class SpawnerOverhaul implements ModInitializer {
    public static final String MOD_ID = "spawner-overhaul";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        PlayerBlockBreakEvents.BEFORE.register(new PlayerBreakSpawnerEvent());
        PlayerBlockBreakEvents.CANCELED.register(new PlayerCancelSpawnerEvent());
        // TODO: This is a hack to refresh the effects of the SpawnerBlock (visual bug)
        ServerTickEvents.END_SERVER_TICK.register(new ServerEndTickEvent());
    }
}
