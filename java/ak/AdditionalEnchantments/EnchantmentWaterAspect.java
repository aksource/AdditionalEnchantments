package ak.AdditionalEnchantments;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EnchantmentWaterAspect extends EnchantmentDamagable {
    public EnchantmentWaterAspect(int id, int weight) {
        super(id, "water_aspect", weight);
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        EntityLivingBase entityLivingBase = event.entityLiving;
        if (entityLivingBase.isImmuneToFire() && event.source.damageType.equals("player")) {
            ItemStack itemStack = ((EntityPlayer) event.source.getEntity()).getCurrentEquippedItem();
            if (itemStack != null && EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.waterAspect.effectId, itemStack) > 0) {
                event.ammount += EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.waterAspect.effectId, itemStack) * 2.5F;
            }
        }
    }
}