package ak.AdditionalEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentDamagable extends Enchantment
{
	public EnchantmentDamagable(int id, String uniqueName, int weight)
	{
		super(id, new ResourceLocation(AdditionalEnchantments.MOD_ID + ":" + uniqueName), weight, EnumEnchantmentType.WEAPON);
	}
	public int getMaxLevel()
	{
		return 5;
	}
	public int getMinEnchantability(int par1)
	{
		return 5 + (par1 - 1) * 8;
	}
	public int getMaxEnchantability(int par1)
	{
		return this.getMinEnchantability(par1) + 20;
	}
	public boolean canApplyTogether(Enchantment enchantment)
	{
		return !(enchantment instanceof EnchantmentDamage) && !(enchantment instanceof EnchantmentDamagable);
	}
}