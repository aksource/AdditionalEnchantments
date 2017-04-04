package ak.AdditionalEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

import static ak.AdditionalEnchantments.AdditionalEnchantments.SLOTS_MAIN_HAND;

public class EnchantmentVorpal extends Enchantment {
    public EnchantmentVorpal(int id, Enchantment.Rarity rarity) {
        super(rarity, EnumEnchantmentType.WEAPON, SLOTS_MAIN_HAND);
        Enchantment.REGISTRY.register(id, AdditionalEnchantments.getResourceLocation(AdditionalEnchantments.NAME_VORPAL), this);
    }

    public int getMaxLevel() {
        return 3;
    }

    public int getMinEnchantability(int par1) {
        return 15 + (par1 - 1) * 9;
    }

    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 50;
    }
}