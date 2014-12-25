package ak.AdditionalEnchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentVoidJump extends Enchantment
{
	public EnchantmentVoidJump(int id, int weight)
	{
		super(id, new ResourceLocation(AdditionalEnchantments.MOD_ID + ":" + "void_jump"), weight, EnumEnchantmentType.ARMOR_FEET);
	}
	
	public int getMinEnchantability(int par1)
	{
		return 15;
	}

	public int getMaxEnchantability(int par1)
	{
		return super.getMinEnchantability(par1) + 50;
	}
	
	public int getMaxLevel()
	{
		return 1;
	}
}