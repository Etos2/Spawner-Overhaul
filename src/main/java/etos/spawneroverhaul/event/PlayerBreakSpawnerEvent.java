package etos.spawneroverhaul.event;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import etos.spawneroverhaul.enchantment.Enchantments;
import etos.spawneroverhaul.extension.ExtendedSpawnerBlockEntity;

public class PlayerBreakSpawnerEvent implements PlayerBlockBreakEvents.Before {

    @Override
    public boolean beforeBlockBreak(World world, PlayerEntity player, BlockPos pos,
            BlockState state, @Nullable BlockEntity blockEntity) {

        if (!player.isCreative() && state.getBlock() == Blocks.SPAWNER) {
            if (!world.isClient() && blockEntity != null && blockEntity instanceof ExtendedSpawnerBlockEntity spawner) {
                ItemStack item = player.getMainHandStack();
                if (Enchantments.hasEnchantment(world, item, Enchantments.EMPOWER_CURSE)) {
                    spawner.triggerBreak(world, pos, ExtendedSpawnerBlockEntity.DEFAULT_BREAK_DELAY);
                } else {
                    spawner.triggerBreakRejected(world, pos);
                }
            }

            return false;
        }

        return true;
    }
}