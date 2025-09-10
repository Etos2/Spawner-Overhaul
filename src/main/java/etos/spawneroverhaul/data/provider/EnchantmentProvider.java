package etos.spawneroverhaul.data.provider;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import etos.spawneroverhaul.enchantment.Enchantments;

public class EnchantmentProvider extends FabricDynamicRegistryProvider {
        public EnchantmentProvider(FabricDataOutput output,
                        CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
                super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
                RegistryWrapper.Impl<Enchantment> enchantmentRegistry = registries.getOrThrow(RegistryKeys.ENCHANTMENT);
                register(entries, enchantmentRegistry, Enchantments.EMPOWER_CURSE);
        }

        private void register(Entries entries, RegistryWrapper.Impl<Enchantment> registry,
                        RegistryKey<Enchantment> key) {
                entries.add(key, registry.getOrThrow(key).value());
        }

        @Override
        public String getName() {
                return "EnchantmentProvider";
        }
}
