package ak.AdditionalEnchantments.Enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.enchantment.EnumEnchantmentType;

import static ak.AdditionalEnchantments.AdditionalEnchantments.SLOTS_MAIN_HAND;

public class EnchantmentProtectionAdditional extends Enchantment {
    public EnchantmentProtectionAdditional(Rarity rarity) {
        super(rarity, EnumEnchantmentType.ARMOR, SLOTS_MAIN_HAND);
    }

    public int getMaxLevel() {
        return 5;
    }

    public int getMinEnchantability(int par1) {
        return 5 + (par1 - 1) * 8;
    }

    public int getMaxEnchantability(int par1) {
        return this.getMinEnchantability(par1) + 15;
    }

    public boolean canApplyTogether(Enchantment enchantment) {
        return !(enchantment instanceof EnchantmentProtection) && !(enchantment instanceof EnchantmentProtectionAdditional);
    }
}