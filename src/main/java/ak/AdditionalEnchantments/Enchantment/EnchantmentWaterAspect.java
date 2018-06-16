package ak.AdditionalEnchantments.Enchantment;

import ak.AdditionalEnchantments.AdditionalEnchantments;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static ak.AdditionalEnchantments.Constants.NAME_WATER_ASPECT;

public class EnchantmentWaterAspect extends EnchantmentDamagable {
    public EnchantmentWaterAspect(Rarity rarity) {
        super(rarity);
        setRegistryName(NAME_WATER_ASPECT);
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        EntityLivingBase entityLivingBase = event.getEntityLiving();
        if (entityLivingBase.isImmuneToFire() && event.getSource().damageType.equals("player")) {
            ItemStack itemStack = ((EntityPlayer) event.getSource().getTrueSource()).getHeldItemMainhand();
            if (!itemStack.isEmpty() && EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.waterAspect, itemStack) > 0) {
                event.setAmount(event.getAmount() +  EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.waterAspect, itemStack) * 2.5F);
            }
        }
    }
}