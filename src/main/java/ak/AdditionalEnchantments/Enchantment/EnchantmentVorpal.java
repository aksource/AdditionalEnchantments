package ak.AdditionalEnchantments.Enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

import static ak.AdditionalEnchantments.AdditionalEnchantments.SLOTS_MAIN_HAND;
import static ak.AdditionalEnchantments.Constants.NAME_VORPAL;

public class EnchantmentVorpal extends Enchantment {
    public EnchantmentVorpal(Enchantment.Rarity rarity) {
        super(rarity, EnumEnchantmentType.WEAPON, SLOTS_MAIN_HAND);
        setRegistryName(NAME_VORPAL);
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