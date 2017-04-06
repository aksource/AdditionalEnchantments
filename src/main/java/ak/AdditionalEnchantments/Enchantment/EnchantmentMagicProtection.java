package ak.AdditionalEnchantments.Enchantment;

import ak.AdditionalEnchantments.AdditionalEnchantments;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;

import static ak.AdditionalEnchantments.Constants.NAME_MAGIC_PROTECTION;

public class EnchantmentMagicProtection extends EnchantmentProtectionAdditional {
    public EnchantmentMagicProtection(int id, Rarity rarity) {
        super(id, AdditionalEnchantments.getResourceLocation(NAME_MAGIC_PROTECTION), rarity);
    }

    @Override
    public int calcModifierDamage(int par1, DamageSource dmgSource) {
        if (dmgSource.isMagicDamage()) {
            float f = (float) (6 + par1 * par1) / 3.0F;
            return MathHelper.floor(f * 1.0F);
        } else return 0;
    }
}