package shit.randomfoodstuff.client.gui.nei;

import java.util.List;

import codechicken.nei.api.IHighlightHandler;
import codechicken.nei.api.ItemInfo.Layout;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import shit.randomfoodstuff.RandomBlocks;
import shit.randomfoodstuff.tileentity.TileEntityFatInfuser;

public class FatInfuserHighlightHandler implements IHighlightHandler{

	@Override
	public ItemStack identifyHighlight(World world, EntityPlayer player, MovingObjectPosition mop) {
		return null;
	}

	@Override
	public List<String> handleTextData(ItemStack itemStack, World world, EntityPlayer player, MovingObjectPosition mop, List<String> currenttip, Layout layout) {
		if (mop.typeOfHit == MovingObjectType.BLOCK) {
			int x = mop.blockX;
			int y = mop.blockY;
			int z = mop.blockZ;
			if (world.getBlock(x, y, z) == RandomBlocks.blockFatInfuser) {
				if (world.getTileEntity(x, y, z) instanceof TileEntityFatInfuser) {
					TileEntityFatInfuser tileentity = (TileEntityFatInfuser) world.getTileEntity(x, y, z);
					if (tileentity.getStackInSlot(0) != null) {
						currenttip.add("Output: " + tileentity.getStackInSlot(0).getDisplayName() + " x" + tileentity.getStackInSlot(0).stackSize);
					}
					
					if (tileentity.getStackInSlot(1) != null) {
						currenttip.add("Input: " + tileentity.getStackInSlot(1).getDisplayName() + " x" + tileentity.getStackInSlot(1).stackSize);
					}
					
					if (tileentity.getStackInSlot(2) != null) {
						currenttip.add("Fuel: " + tileentity.getStackInSlot(2).getDisplayName() + " x" + tileentity.getStackInSlot(2).stackSize);
					}
				}
			}
		}
		
		return currenttip;
	}

}
