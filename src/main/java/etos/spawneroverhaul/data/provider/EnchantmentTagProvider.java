package etos.spawneroverhaul.data.provider;

import java.util.concurrent.CompletableFuture;

import etos.spawneroverhaul.enchantment.Enchantments;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.util.Identifier;

public class EnchantmentTagProvider extends FabricTagProvider<Enchantment> {
    public EnchantmentTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ENCHANTMENT, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        Identifier empowering = Enchantments.EMPOWER_CURSE.getValue();
        getTagBuilder(EnchantmentTags.CURSE).add(empowering);
        getTagBuilder(EnchantmentTags.ON_RANDOM_LOOT).add(empowering);
        getTagBuilder(EnchantmentTags.TRADEABLE).add(empowering);
        getTagBuilder(EnchantmentTags.TREASURE).add(empowering);
    }
}