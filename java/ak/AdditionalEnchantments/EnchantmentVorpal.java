package ak.AdditionalEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentVorpal extends Enchantment
{
	public EnchantmentVorpal(int id, int weight)
	{
		super(id, new ResourceLocation(AdditionalEnchantments.MOD_ID + ":" + "vorpal"), weight, EnumEnchantmentType.WEAPON);
	}
	public int getMaxLevel()
	{
		return 3;
	}
	public int getMinEnchantability(int par1)
	{
		return 15 + (par1 - 1) * 9;
	}
	public int getMaxEnchantability(int par1)
	{
		return super.getMinEnchantability(par1) + 50;
	}
}