package ak.AdditionalEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

import static ak.AdditionalEnchantments.AdditionalEnchantments.SLOTS_PROTECTS;

public class EnchantmentVoidJump extends Enchantment {
    public EnchantmentVoidJump(int id, Rarity rarity) {
        super(rarity, EnumEnchantmentType.ARMOR_FEET, SLOTS_PROTECTS);
        Enchantment.REGISTRY.register(id, AdditionalEnchantments.getResourceLocation(AdditionalEnchantments.NAME_VOID_JUMP), this);
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