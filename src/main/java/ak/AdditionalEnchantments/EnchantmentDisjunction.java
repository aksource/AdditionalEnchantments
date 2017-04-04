package ak.AdditionalEnchantments;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static ak.AdditionalEnchantments.AdditionalEnchantments.NAME_DISJUNCTION;

public class EnchantmentDisjunction extends EnchantmentDamagable {
    public EnchantmentDisjunction(int id, Rarity rarity) {
        super(id, AdditionalEnchantments.getResourceLocation(NAME_DISJUNCTION), rarity);
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        EntityLivingBase entityLivingBase = event.getEntityLiving();
        if (entityLivingBase instanceof EntityEnderman && event.getSource().damageType.equals("player")) {
            ItemStack itemStack = ((EntityPlayer) event.getSource().getEntity()).getHeldItemMainhand();
            if (itemStack != null && EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.disjunction, itemStack) > 0) {
                event.setAmount(event.getAmount() +  EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.disjunction, itemStack) * 2.5F);
            }
        }
    }
}