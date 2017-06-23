package ak.AdditionalEnchantments;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class VoidJumpEventHook {
    @SubscribeEvent
    public void jumpToHomeEvent(LivingHurtEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        boolean voidJumpEnchanted = false;
        int lv;
        for (ItemStack armor : entity.getArmorInventoryList()) {
            lv = EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.voidJump, armor);
            if (lv > 0) {
                voidJumpEnchanted = true;
                break;
            }
        }
        if (event.getSource() == DamageSource.OUT_OF_WORLD && voidJumpEnchanted) {
            entity.motionX = entity.motionY = entity.motionZ = 0;
            if (!event.getEntityLiving().getEntityWorld().isRemote)
                jumpToHome(entity);
            entity.fallDistance = 0;
            event.setCanceled(true);
        }
    }

    public void jumpToHome(EntityLivingBase entity) {
        BlockPos positions;
        World world = entity.getEntityWorld();
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            int dim = world.provider.getDimension();
            if (player.getBedLocation(dim) == null) {
                transferToDimension(0, entity);
            } else {
                positions = player.getBedLocation(dim);
                player.setPositionAndUpdate(positions.getX(), positions.getY(), positions.getZ());
            }
            this.spawnPortalParticle(player, world);
        } else if (entity instanceof EntityCreature) {
            EntityCreature living = (EntityCreature) entity;
            positions = living.getHomePosition();
            living.setPositionAndUpdate(positions.getX(), positions.getY(), positions.getZ());
        }
    }

    public void transferToDimension(int dim, EntityLivingBase entity) {
        if (entity instanceof EntityPlayerMP && entity.getServer() != null) {
            EntityPlayerMP playerMP = (EntityPlayerMP) entity;
            WorldServer worldServer = playerMP.getServer().getWorld(dim);
            BlockPos blockPos = worldServer.getTopSolidOrLiquidBlock(worldServer.getSpawnPoint());
            playerMP.setPositionAndUpdate(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            playerMP.getServer().getPlayerList().transferPlayerToDimension(playerMP,
                    dim, new VoidJumpTeleporter(playerMP.mcServer.getWorld(dim)));
        }
    }

    private void spawnPortalParticle(EntityLivingBase entity, World world) {
        for (int var2 = 0; var2 < 32; ++var2) {
            world.spawnParticle(EnumParticleTypes.PORTAL, entity.posX, entity.posY + world.rand.nextDouble() * 2.0D, entity.posZ, world.rand.nextGaussian(), 0.0D, world.rand.nextGaussian());
        }
    }
}