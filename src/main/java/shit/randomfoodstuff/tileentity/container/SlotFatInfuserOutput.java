package shit.randomfoodstuff.tileentity.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import shit.randomfoodstuff.event.FatInfusingEvent;
import shit.randomfoodstuff.event.FatInfusingEvent.ItemInfusedEvent;

public class SlotFatInfuserOutput extends Slot {

	public SlotFatInfuserOutput(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
		
	}
	
	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
		super.onPickupFromSlot(player, stack);
		
		FatInfusingEvent.ItemInfusedEvent event = new ItemInfusedEvent(player.worldObj, stack, player);
		MinecraftForge.EVENT_BUS.post(event);
		
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}

}
