package ak.AdditionalEnchantments;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static ak.AdditionalEnchantments.AdditionalEnchantments.NAME_WATER_ASPECT;

public class EnchantmentWaterAspect extends EnchantmentDamagable {
    public EnchantmentWaterAspect(int id, Rarity rarity) {
        super(id, AdditionalEnchantments.getResourceLocation(NAME_WATER_ASPECT), rarity);
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        EntityLivingBase entityLivingBase = event.getEntityLiving();
        if (entityLivingBase.isImmuneToFire() && event.getSource().damageType.equals("player")) {
            ItemStack itemStack = ((EntityPlayer) event.getSource().getEntity()).getHeldItemMainhand();
            if (itemStack != null && EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.waterAspect, itemStack) > 0) {
                event.setAmount(event.getAmount() +  EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.waterAspect, itemStack) * 2.5F);
            }
        }
    }
}