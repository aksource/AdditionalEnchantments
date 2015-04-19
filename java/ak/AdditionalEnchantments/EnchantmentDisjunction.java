package ak.AdditionalEnchantments;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EnchantmentDisjunction extends EnchantmentDamagable {
    public EnchantmentDisjunction(int id, int weight) {
        super(id, weight);
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        EntityLivingBase entityLivingBase = event.entityLiving;
        if (entityLivingBase instanceof EntityEnderman && event.source.damageType.equals("player")) {
            ItemStack itemStack = ((EntityPlayer) event.source.getEntity()).getCurrentEquippedItem();
            if (itemStack != null && EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.disjunction.effectId, itemStack) > 0) {
                event.ammount += EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.disjunction.effectId, itemStack) * 2.5F;
            }
        }
    }
}