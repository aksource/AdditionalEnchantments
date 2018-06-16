package ak.AdditionalEnchantments.Enchantment;

import ak.AdditionalEnchantments.AdditionalEnchantments;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static ak.AdditionalEnchantments.Constants.NAME_DISJUNCTION;

public class EnchantmentDisjunction extends EnchantmentDamagable {
    public EnchantmentDisjunction(Rarity rarity) {
        super(rarity);
        setRegistryName(NAME_DISJUNCTION);
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        EntityLivingBase entityLivingBase = event.getEntityLiving();
        if (entityLivingBase instanceof EntityEnderman && event.getSource().damageType.equals("player")) {
            ItemStack itemStack = ((EntityPlayer) event.getSource().getTrueSource()).getHeldItemMainhand();
            if (!itemStack.isEmpty() && EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.disjunction, itemStack) > 0) {
                event.setAmount(event.getAmount() +  EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.disjunction, itemStack) * 2.5F);
            }
        }
    }
}