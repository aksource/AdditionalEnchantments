package ak.AdditionalEnchantments;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.ArrayList;
import java.util.Random;

public class VorpalEventHook
{
	private boolean vorpaled = false;
	private Random rand = new Random();

    @SubscribeEvent
    public void vorpalHurtEvent(LivingHurtEvent event) {
        if (!AdditionalEnchantments.addVorpal) return;
        if (event.source.getEntity() instanceof EntityLivingBase && event.source.getSourceOfDamage() instanceof EntityLivingBase) {
            EntityLivingBase attacker = (EntityLivingBase)event.source.getEntity();
            ItemStack heldItem = attacker.getHeldItem();
            if (heldItem != null && EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.idVorpal, heldItem) > 0) {
                EntityLivingBase target = event.entityLiving;
                int vorpalLv = EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.idVorpal, heldItem);
                float targetHpRatio = target.getHealth() / target.getMaxHealth();
                int range = MathHelper.ceiling_float_int(10000 * targetHpRatio);
                if(vorpalLv * 100 > rand.nextInt(range)) {
                    vorpaled = true;
                    event.ammount = 9999999F;
                }
            }
        }
    }

//	@SubscribeEvent
//	public void vorpalAttackEvent(AttackEntityEvent event)
//	{
//		ItemStack equipItem = event.entityPlayer.getCurrentEquippedItem();
//		int vorpalLv;
//		vorpaled = false;
//		if(AdditionalEnchantments.addVorpal && event.target instanceof EntityLivingBase &&equipItem != null && EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.idVorpal, equipItem) > 0){
//			vorpalLv = EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.idVorpal, equipItem);
//			EntityLivingBase target = (EntityLivingBase) event.target;
//			if(vorpalLv * 10 > rand.nextInt(100)){
//				vorpaled = true;
//				target.attackEntityFrom(DamageSource.causePlayerDamage(event.entityPlayer), 9999999F);
//			}
//		}
//	}

	@SubscribeEvent
	public void entityDropEvent(LivingDropsEvent event)
	{
		if(AdditionalEnchantments.addVorpal && event.source.getEntity() != null && event.source.getEntity() instanceof EntityLivingBase)
		{
            EntityLivingBase attacker = (EntityLivingBase) event.source.getEntity();
			ItemStack equipItem = attacker.getHeldItem();
			int vorpalLv = EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.idVorpal, equipItem);
			if((vorpaled || vorpalLv * 10 > rand.nextInt(100)) && !skullInDrops(event.drops)){
				int skullmeta = skullKind(event.entityLiving);
				if(skullmeta >= 0){
					ItemStack skull = new ItemStack(Items.skull, 1, skullmeta);
					EntityItem entityitem = new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ);
					entityitem.setEntityItemStack(skull);
					event.drops.add(entityitem);
					vorpaled = false;
				}
			}
		}
	}
	private int skullKind(EntityLivingBase living) {
		if(living instanceof EntitySkeleton){
			if(((EntitySkeleton)living).getSkeletonType() == 0)
				return 0;
			else
				return 1;
		}
        if(living instanceof EntityPlayer){
			return 3;
		}
        if(living instanceof EntityZombie){
			return 2;
		}
        if(living instanceof EntityCreeper){
			return 4;
		}
        return -1;
	}
	private boolean skullInDrops(ArrayList<EntityItem> droplist) {
		for(EntityItem entityItem : droplist) {
			if(entityItem.getEntityItem().getItem() instanceof ItemSkull)
				return true;
		}
		return false;
	}
}