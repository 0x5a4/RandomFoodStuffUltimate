package shit.randomfoodstuff.client.gui.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import shit.randomfoodstuff.tileentity.TileEntityFatInfuser;

public class WailaFatInfuserHandler implements IWailaDataProvider {

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		if (accessor.getTileEntity() instanceof TileEntityFatInfuser) {
			TileEntityFatInfuser tileentity = (TileEntityFatInfuser) accessor.getTileEntity();
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
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
		return tag;
	}

}
