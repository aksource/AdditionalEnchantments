package ak.AdditionalEnchantments;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class VoidJumpTeleporter extends Teleporter {

	public VoidJumpTeleporter(WorldServer par1WorldServer) {
		super(par1WorldServer);
		par1WorldServer.customTeleporters.add(this);
	}

	@Override
	public void placeInPortal(Entity entityIn, float rotationYaw) {
		entityIn.motionX = entityIn.motionY = entityIn.motionZ = 0.0D;
		BlockPos blockPos = entityIn.getEntityWorld().getTopSolidOrLiquidBlock(new BlockPos(entityIn.posX, entityIn.posY, entityIn.posZ));
		entityIn.moveToBlockPosAndAngles(blockPos, 0, 0);
		entityIn.fallDistance = 0;
	}
}
