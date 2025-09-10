package etos.spawneroverhaul.enchantment;

import java.util.List;

import etos.spawneroverhaul.SpawnerOverhaul;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.enchantment.effect.entity.ApplyMobEffectEnchantmentEffect;
import net.minecraft.enchantment.effect.value.AddEnchantmentEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class Enchantments {
    public static final RegistryKey<Enchantment> EMPOWER_CURSE = of("empower_curse");

    private static RegistryKey<Enchantment> of(String path) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(SpawnerOverhaul.MOD_ID, path));
    }

    public static void bootstrap(Registerable<Enchantment> registerable) {
        RegistryEntryLookup<Item> items = registerable.getRegistryLookup(RegistryKeys.ITEM);

        RegistryEntryList toApply = RegistryEntryList.of(List.of(
                StatusEffects.STRENGTH,
                StatusEffects.SPEED,
                StatusEffects.RESISTANCE));
        ApplyMobEffectEnchantmentEffect randomPotionEffect = new ApplyMobEffectEnchantmentEffect(toApply,
                EnchantmentLevelBasedValue.constant(10),
                EnchantmentLevelBasedValue.constant(20),
                EnchantmentLevelBasedValue.constant(1),
                EnchantmentLevelBasedValue.constant(1));

        register(registerable, EMPOWER_CURSE, Enchantment.builder(
                Enchantment.definition(
                        items.getOrThrow(ItemTags.DURABILITY_ENCHANTABLE),
                        1,
                        1,
                        Enchantment.constantCost(25),
                        Enchantment.constantCost(50),
                        8,
                        AttributeModifierSlot.ANY))
                .addEffect(
                        EnchantmentEffectComponentTypes.POST_ATTACK,
                        EnchantmentEffectTarget.ATTACKER,
                        EnchantmentEffectTarget.VICTIM,
                        randomPotionEffect)
                .addEffect(
                        EnchantmentEffectComponentTypes.POST_ATTACK,
                        EnchantmentEffectTarget.VICTIM,
                        EnchantmentEffectTarget.ATTACKER,
                        randomPotionEffect)
                .addEffect(EnchantmentEffectComponentTypes.DAMAGE,
                        new AddEnchantmentEffect(EnchantmentLevelBasedValue
                                .constant(2.0F))));
    }

    private static void register(Registerable<Enchantment> registry, RegistryKey<Enchantment> key,
            Enchantment.Builder builder) {
        registry.register(key, builder.build(key.getValue()));
    }

    public static boolean hasEnchantment(World world, ItemStack itemStack, RegistryKey<Enchantment> key) {
        Registry<Enchantment> registry = world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
        RegistryEntry<Enchantment> enchantment = registry.getOrThrow(key);

        return EnchantmentHelper.getLevel(enchantment, itemStack) > 0;
    }
}