package ak.AdditionalEnchantments;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;

public class VoidJumpEventHook
{
	@SubscribeEvent
	public void jumpToHomeEvent(LivingHurtEvent event)
	{
		EntityLivingBase entity = event.entityLiving;
		ItemStack armor;
		boolean voidJumpEnchanted = false;
		int lv;
		for(int i = 1;i < 5; i++){
			armor = entity.getEquipmentInSlot(i);
			lv = EnchantmentHelper.getEnchantmentLevel(AdditionalEnchantments.idVoidJump, armor);
			if(lv > 0){
				voidJumpEnchanted = true;
				break;
			}
		}
		if(event.source == DamageSource.outOfWorld && voidJumpEnchanted){
			entity.motionX = entity.motionY = entity.motionZ = 0;
			if(!event.entityLiving.worldObj.isRemote)
				jumpToHome(entity);
			entity.fallDistance = 0;
			event.setCanceled(true);
		}
	}
	public void jumpToHome(EntityLivingBase entity)
	{
        BlockPos positions;
		World world = entity.worldObj;
		if(entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) entity;
			int dim = world.provider.getDimensionId();
			if(player.getBedLocation(dim) == null){
				tranferToDimension(0, entity);
			}else{
				positions = player.getBedLocation(dim);
				player.setPositionAndUpdate(positions.getX(), positions.getY(), positions.getZ());
			}
			this.spawnPortalParticle(player, world);
		}else if(entity instanceof EntityCreature){
			EntityCreature living = (EntityCreature) entity;
			positions = living.func_180486_cf();
			living.setPositionAndUpdate(positions.getX(), positions.getY(), positions.getZ());
		}
	}
	public void tranferToDimension(int dim, EntityLivingBase entity)
	{
		if(entity instanceof EntityPlayerMP){
			EntityPlayerMP playerMP = (EntityPlayerMP) entity;
			WorldServer worldserver1 = MinecraftServer.getServer().worldServerForDimension(dim);
            BlockPos blockPos = worldserver1.getSpawnPoint();
			playerMP.setPositionAndUpdate(blockPos.getX(), blockPos.getY(), blockPos.getZ());
			transferPlayerToDimension(playerMP. mcServer.getConfigurationManager(), playerMP, dim, new VoidJumpTeleporter(playerMP.mcServer.worldServerForDimension(dim)));
		}
	}

    @SuppressWarnings("unchecked")
	public static void transferPlayerToDimension(ServerConfigurationManager serverConf, EntityPlayerMP par1EntityPlayerMP, int par2, Teleporter teleporter)
	{
		int j = par1EntityPlayerMP.dimension;
		WorldServer worldserver = MinecraftServer.getServer().worldServerForDimension(par1EntityPlayerMP.dimension);
		par1EntityPlayerMP.dimension = par2;
		WorldServer worldserver1 = MinecraftServer.getServer().worldServerForDimension(par1EntityPlayerMP.dimension);
		par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S07PacketRespawn(par1EntityPlayerMP.dimension, par1EntityPlayerMP.worldObj.getDifficulty(), worldserver1.getWorldInfo().getTerrainType(), par1EntityPlayerMP.theItemInWorldManager.getGameType()));
		worldserver.removePlayerEntityDangerously(par1EntityPlayerMP);
		par1EntityPlayerMP.isDead = false;
		serverConf.transferEntityToWorld(par1EntityPlayerMP, par2, worldserver, worldserver1, teleporter);
		serverConf.func_72375_a(par1EntityPlayerMP, worldserver);
		par1EntityPlayerMP.playerNetServerHandler.setPlayerLocation(par1EntityPlayerMP.posX, par1EntityPlayerMP.posY, par1EntityPlayerMP.posZ, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);
		par1EntityPlayerMP.theItemInWorldManager.setWorld(worldserver1);
		serverConf.updateTimeAndWeatherForPlayer(par1EntityPlayerMP, worldserver1);
		serverConf.syncPlayerInventory(par1EntityPlayerMP);
        for (PotionEffect potionEffect : (Collection<PotionEffect>)par1EntityPlayerMP.getActivePotionEffects()) {
            par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(par1EntityPlayerMP.getEntityId(), potionEffect));
        }

		FMLCommonHandler.instance().firePlayerChangedDimensionEvent(par1EntityPlayerMP, j, par2);
	}
	private void spawnPortalParticle(EntityLivingBase entity, World world)
	{
		for (int var2 = 0; var2 < 32; ++var2){
			world.spawnParticle(EnumParticleTypes.PORTAL, entity.posX, entity.posY + world.rand.nextDouble() * 2.0D, entity.posZ, world.rand.nextGaussian(), 0.0D, world.rand.nextGaussian());
		}
	}
}