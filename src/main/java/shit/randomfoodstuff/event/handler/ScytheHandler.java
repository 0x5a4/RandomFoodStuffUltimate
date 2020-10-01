package shit.randomfoodstuff.event.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.IGrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.world.BlockEvent;
import shit.randomfoodstuff.item.IScythe;

public class ScytheHandler {

	@SubscribeEvent
	public void onHarvestDrops(BlockEvent.HarvestDropsEvent event) {
		if (event.harvester != null) {
			if (event.harvester.getCurrentEquippedItem() != null) {
				if (event.harvester.getCurrentEquippedItem().getItem() instanceof IScythe && event.block instanceof IGrowable) {
					if (ScytheHandler.getHarvestState(event.harvester.getCurrentEquippedItem())) {
						ScytheHandler.setHarvestState(event.harvester.getCurrentEquippedItem(), false);
						if (!event.world.isRemote) {
							boolean flag = false;
							for (int i = 0; i < event.drops.size(); i++) {
								if (!(event.drops.get(i).getItem() instanceof IPlantable)) {
									flag = true;
								}
							}
							
							for (int i = 0; i < event.drops.size(); i++) {
								if (event.drops.get(i).getItem() instanceof IPlantable) {
									if (!flag) {
										event.drops.get(i).stackSize += ((IScythe) event.harvester.getCurrentEquippedItem().getItem()).getDropModifier();
									}
								} else {
									event.drops.get(i).stackSize += ((IScythe) event.harvester.getCurrentEquippedItem().getItem()).getDropModifier();
								}
							}
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		if (event.getPlayer() != null) {
			ItemStack heldItem = event.getPlayer().getCurrentEquippedItem();
			if (heldItem != null) {
				if (heldItem.getItem() instanceof IScythe && event.block instanceof IGrowable) {
					IGrowable growable = (IGrowable) event.block;
					if (!growable.func_149851_a(event.world, event.x, event.y, event.z, event.world.isRemote)) {
						setHarvestState(heldItem, true);
					} else {
						setHarvestState(heldItem, false);
					}
				}
			}
		}
	}
	
	public static void setHarvestState(ItemStack stack ,boolean flag) {
		NBTTagCompound itemTag = stack.getTagCompound();

        if (itemTag == null)
        {
            stack.setTagCompound(new NBTTagCompound());
            itemTag = stack.getTagCompound();
        }
        
        itemTag.setBoolean("harvestState", flag);
	}
	
	public static boolean getHarvestState(ItemStack stack) {
		if (!stack.hasTagCompound())
        {
            stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound itemTag = stack.getTagCompound();

        return itemTag.getBoolean("harvestState");
	}
}
