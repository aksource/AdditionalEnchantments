package ak.AdditionalEnchantments.Enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

import static ak.AdditionalEnchantments.AdditionalEnchantments.SLOTS_PROTECTS;
import static ak.AdditionalEnchantments.Constants.NAME_VOID_JUMP;

public class EnchantmentVoidJump extends Enchantment {
    public EnchantmentVoidJump(Rarity rarity) {
        super(rarity, EnumEnchantmentType.ARMOR_FEET, SLOTS_PROTECTS);
        setRegistryName(NAME_VOID_JUMP);
    }

    public int getMinEnchantability(int par1) {
        return 15;
    }

    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 50;
    }

    public int getMaxLevel() {
        return 1;
    }
}