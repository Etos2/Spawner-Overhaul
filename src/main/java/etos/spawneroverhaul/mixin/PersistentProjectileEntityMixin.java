package etos.spawneroverhaul.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import etos.spawneroverhaul.enchantment.Enchantments;
import etos.spawneroverhaul.extension.ExtendedSpawnerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin extends Entity {
    public PersistentProjectileEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "applyCollision", at = @At("HEAD"))
    private void applyCollision(BlockHitResult blockHitResult, CallbackInfo info) {
        if (blockHitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = blockHitResult.getBlockPos();
            World world = this.getWorld();
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ExtendedSpawnerBlockEntity spawner) {
                ItemStack item = this.getWeaponStack();
                if (item != null && Enchantments.hasEnchantment(world, item, Enchantments.EMPOWER_CURSE)) {
                    spawner.triggerBreak(world, pos);
                }
            }
        }
    }
}
