package ak.AdditionalEnchantments;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class VoidJumpTeleporter extends Teleporter {

	public VoidJumpTeleporter(WorldServer par1WorldServer) {
		super(par1WorldServer);
		par1WorldServer.customTeleporters.add(this);
	}

	@Override
	public void placeInPortal(Entity par1Entity, float par8) {
		par1Entity.motionX = par1Entity.motionY = par1Entity.motionZ = 0.0D;
		BlockPos blockPos = par1Entity.worldObj.getTopSolidOrLiquidBlock(new BlockPos(par1Entity.posX, par1Entity.posY, par1Entity.posZ));
		par1Entity.moveToBlockPosAndAngles(blockPos, 0, 0);
		par1Entity.fallDistance = 0;
	}
}
