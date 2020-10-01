package shit.randomfoodstuff.client.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import shit.randomfoodstuff.tileentity.TileEntityCookingPot;
import shit.randomfoodstuff.tileentity.TileEntityFatInfuser;
import shit.randomfoodstuff.tileentity.container.ContainerCookingPot;
import shit.randomfoodstuff.tileentity.container.ContainerFatInfuser;
import shit.randomfoodstuff.tileentity.container.ContainerSchnitzelBackpack;
import shit.randomfoodstuff.tileentity.container.InventorySchnitzelBackpack;

public class GuiHandler implements IGuiHandler {

	public static final int GuiIDFatInfuser = 0;
	public static final int GuiIDSchnitzelBackpack = 1;
	public static final int GuiIDCookingPot = 2;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		
		if(entity != null) {
			switch(ID) {
			case GuiIDFatInfuser:
				if (entity instanceof TileEntityFatInfuser) return new ContainerFatInfuser(player.inventory, (TileEntityFatInfuser) entity); break;
			case GuiIDCookingPot:
				if (entity instanceof TileEntityCookingPot) return new ContainerCookingPot((TileEntityCookingPot) entity); break;
			}
		} else if (ID == GuiIDSchnitzelBackpack) {
			return new ContainerSchnitzelBackpack(player, new InventorySchnitzelBackpack(player.getHeldItem()));
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		
		if(entity != null) {
			switch(ID) {
			case GuiIDFatInfuser:
				if (entity instanceof TileEntityFatInfuser) return new GuiFatInfuser(player.inventory, (TileEntityFatInfuser) entity); break;
			case GuiIDCookingPot:
				if (entity instanceof TileEntityCookingPot) return new GuiCookingPot((TileEntityCookingPot) entity); break;
			}
		} else if (ID == GuiIDSchnitzelBackpack) {
			return new GuiSchnitzelBackpack(player, new InventorySchnitzelBackpack(player.getHeldItem()));
		}
		return null;
	}
	
}
