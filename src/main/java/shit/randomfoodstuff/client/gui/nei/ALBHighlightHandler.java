package shit.randomfoodstuff.client.gui.nei;

import java.util.List;

import codechicken.nei.api.IHighlightHandler;
import codechicken.nei.api.ItemInfo.Layout;
import codechicken.nei.guihook.GuiContainerManager;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import shit.randomfoodstuff.RandomBlocks;
import shit.randomfoodstuff.item.tool.IAwfullLookingBlockBreaker;
import shit.randomfoodstuff.tileentity.TileEntityBlockAwfull;

public class ALBHighlightHandler implements IHighlightHandler{

	@Override
	public ItemStack identifyHighlight(World world, EntityPlayer player, MovingObjectPosition mop) {
		if (player.getCurrentEquippedItem() != null) {
			if (player.getCurrentEquippedItem().getItem() instanceof IAwfullLookingBlockBreaker) {
				return null;
			}
		}
		
		if (mop.typeOfHit == MovingObjectType.BLOCK) {
			Block block = world.getBlock(mop.blockX, mop.blockY, mop.blockZ);
			if (block == RandomBlocks.blockAwfull) {
				if (world.getTileEntity(mop.blockX, mop.blockY, mop.blockZ) instanceof TileEntityBlockAwfull) {
					TileEntityBlockAwfull tileentity = (TileEntityBlockAwfull) world.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
					if (tileentity.hasFakeData()) {
						return new ItemStack(tileentity.getFakeBlock(), 1, tileentity.getBlockMetadata());
					}
				}
				
			}
		}
			
		return null;
	}

	@Override
	public List<String> handleTextData(ItemStack itemStack, World world, EntityPlayer player, MovingObjectPosition mop, List<String> currenttip, Layout layout) {
		if (player.getCurrentEquippedItem() != null) {
			if (player.getCurrentEquippedItem().getItem() instanceof IAwfullLookingBlockBreaker) {
				return currenttip;
			}
		}
		
		if (mop.typeOfHit == MovingObjectType.BLOCK) {
			Block block = world.getBlock(mop.blockX, mop.blockY, mop.blockZ);
			if (block == RandomBlocks.blockAwfull) {
				if (world.getTileEntity(mop.blockX, mop.blockY, mop.blockZ) instanceof TileEntityBlockAwfull) {
					TileEntityBlockAwfull tileentity = (TileEntityBlockAwfull) world.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
					if (tileentity.hasFakeData()) {
						ItemStack fakeStack = new ItemStack(tileentity.getFakeBlock(), 1, tileentity.getFakeBlockMeta());
						int index = 0;
						for (int i = 0; i < currenttip.size(); i++) {
							String s = currenttip.get(i);
							if (s.toLowerCase() == GuiContainerManager.itemDisplayNameShort(new ItemStack(RandomBlocks.blockAwfull, 1, tileentity.blockMetadata))) {
								index = i;
								break;
							}
						}
						currenttip.set(index, GuiContainerManager.itemDisplayNameShort(fakeStack));
					}
				}
			}
		}
		return currenttip;
	}

}
