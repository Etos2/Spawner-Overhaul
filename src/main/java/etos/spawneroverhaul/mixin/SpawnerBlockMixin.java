package etos.spawneroverhaul.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SpawnerBlock;

@Mixin(SpawnerBlock.class)
public abstract class SpawnerBlockMixin {
    @ModifyArg(method = "Lnet/minecraft/block/SpawnerBlock;<init>(Lnet/minecraft/block/AbstractBlock$Settings;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockWithEntity;<init>(Lnet/minecraft/block/AbstractBlock$Settings;)V"), index = 0)
    private static AbstractBlock.Settings init(AbstractBlock.Settings settings) {
        return settings.resistance(3600000.f);
    }
}
