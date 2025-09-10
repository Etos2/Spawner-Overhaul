package etos.spawneroverhaul;

import etos.spawneroverhaul.data.provider.EnchantmentProvider;
import etos.spawneroverhaul.data.provider.EnchantmentTagProvider;
import etos.spawneroverhaul.enchantment.Enchantments;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class SpawnerOverhaulDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(EnchantmentProvider::new);
        pack.addProvider(EnchantmentTagProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder builder) {
        builder.addRegistry(RegistryKeys.ENCHANTMENT, Enchantments::bootstrap);
    }
}
