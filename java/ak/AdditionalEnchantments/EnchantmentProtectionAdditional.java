package ak.AdditionalEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentProtectionAdditional extends Enchantment {
    public EnchantmentProtectionAdditional(int id, String name, int weight) {
        super(id, new ResourceLocation(AdditionalEnchantments.MOD_ID + ":" + name), weight, EnumEnchantmentType.ARMOR);
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