package ak.AdditionalEnchantments;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Random;

public class VorpalEventHook {
    private boolean vorpaled = false;
    private Random rand = new Random();

    @SubscribeEvent
    public void vorpalHurtEvent(LivingHurtEvent event) {
        if (!AdditionalEnchantments.addVorpal) return;
        if (event.getEntityLiving().isNonBoss() && event.getSource().getTrueSource() instanceof EntityLivingBase && event.getSource().getImmediateSource() instanceof EntityLivingBase) {
            EntityLivingBase attacker = (EntityLivingBase) event.getSource().getTrueSource();
            ItemStack heldItem = attacker.getHeldItem(EnumHand.MAIN_HAND);
            if (!heldItem.isEmpty() && EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.vorpal, heldItem) > 0) {
                EntityLivingBase target = event.getEntityLiving();
                int vorpalLv = EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.vorpal, heldItem);
                float targetHpRatio = target.getHealth() / target.getMaxHealth();
                int range = MathHelper.ceil(10000 * targetHpRatio);
                if (range > 0 && vorpalLv * 100 > rand.nextInt(range)) {
                    vorpaled = true;
                    event.setAmount(9999999F);
                }
            }
        }
    }

    @SubscribeEvent
    public void entityDropEvent(LivingDropsEvent event) {
        if (AdditionalEnchantments.addVorpal && event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityLivingBase) {
            EntityLivingBase attacker = (EntityLivingBase) event.getSource().getTrueSource();
            ItemStack equipItem = attacker.getHeldItem(EnumHand.MAIN_HAND);
            int vorpalLv = EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.vorpal, equipItem);
            if ((vorpaled || vorpalLv * 10 > rand.nextInt(100)) && !skullInDrops(event.getDrops())) {
                int skullmeta = skullKind(event.getEntityLiving());
                if (skullmeta >= 0) {
                    ItemStack skull = new ItemStack(Items.SKULL, 1, skullmeta);
                    EntityItem entityitem = new EntityItem(event.getEntityLiving().getEntityWorld(), event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ);
                    entityitem.setItem(skull);
                    event.getDrops().add(entityitem);
                    vorpaled = false;
                }
            }
        }
    }

    private int skullKind(EntityLivingBase living) {
        if (living instanceof EntitySkeleton) {
            return 0;
        }
        if (living instanceof EntityWitherSkeleton) {
            return 1;
        }
        if (living instanceof EntityPlayer) {
            return 3;
        }
        if (living instanceof EntityZombie) {
            return 2;
        }
        if (living instanceof EntityCreeper) {
            return 4;
        }
        return -1;
    }

    private boolean skullInDrops(List<EntityItem> droplist) {
        for (EntityItem entityItem : droplist) {
            if (entityItem.getItem().getItem() instanceof ItemSkull)
                return true;
        }
        return false;
    }
}