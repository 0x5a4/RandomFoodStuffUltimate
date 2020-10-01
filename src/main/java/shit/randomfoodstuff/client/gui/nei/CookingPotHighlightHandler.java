package shit.randomfoodstuff.client.gui.nei;

import java.util.ArrayList;
import java.util.List;

import codechicken.nei.api.IHighlightHandler;
import codechicken.nei.api.ItemInfo.Layout;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import shit.randomfoodstuff.RandomBlocks;
import shit.randomfoodstuff.cooking.SoupRegistry;
import shit.randomfoodstuff.tileentity.TileEntityCookingPot;

public class CookingPotHighlightHandler implements IHighlightHandler {

	@Override
	public ItemStack identifyHighlight(World world, EntityPlayer player, MovingObjectPosition mop) {
		return null;
	}
	
	@Override
	public List<String> handleTextData(ItemStack stack, World world, EntityPlayer player, MovingObjectPosition mop, List<String> currenttip, Layout layout) {
		if (mop.typeOfHit == MovingObjectType.BLOCK) {
			int x = mop.blockX;
			int y = mop.blockY;
			int z = mop.blockZ;
			if (world.getBlock(x, y, z) == RandomBlocks.blockCookingPot) {
				if (world.getTileEntity(x, y, z) instanceof TileEntityCookingPot) {
					TileEntityCookingPot tileentity = (TileEntityCookingPot) world.getTileEntity(x, y, z);
					int soupPercent = (int) (((float)tileentity.soupRemaining / ((float)TileEntityCookingPot.soupVal * 2)) * 100F);
					
					currenttip.add("Soup Remaining: " + soupPercent + "%");
					if (tileentity.containsItems()) {
						ArrayList<String> ingredList = new ArrayList<String>();
						for (int i = 0; i < tileentity.getSizeInventory() - 2; i++) {
							ItemStack ingred = tileentity.getStackInSlot(i);
							if (ingred != null) {
								String stackDisplayName = ingred.getDisplayName();
								if (!ingredList.contains(stackDisplayName)) {
									ingredList.add(stackDisplayName);
								}
							}
						}
						
						currenttip.add("Ingriedients: ");
						for (String s : ingredList) {
							currenttip.add("  " + s);
						}
					}
					
					if (tileentity.containsSpice()) {
						currenttip.add("Spices: ");
						if (tileentity.getReagent() != null) {
							if (SoupRegistry.getEffectDisplayName(tileentity.getSpice()).equals(SoupRegistry.getEffectDisplayName(tileentity.getReagent()))) {
								currenttip.add("  " + SoupRegistry.getEffectDisplayName(tileentity.getSpice()) + " x2");
							} else {
								currenttip.add("  " + SoupRegistry.getEffectDisplayName(tileentity.getReagent()));
								if (SoupRegistry.canReact(tileentity.getSpice(), tileentity.getReagent())) {
									currenttip.add("=" + SoupRegistry.getEffectDisplayName(SoupRegistry.getReaction(tileentity.getSpice(), tileentity.getReagent()).getResultEffect()));
								}
							}
						} else {
							currenttip.add("  " + SoupRegistry.getEffectDisplayName(tileentity.getSpice()));
						}
					}
				}
			}
		}
		return currenttip;
	}

}
