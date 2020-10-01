package shit.randomfoodstuff.tileentity.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import shit.randomfoodstuff.item.ISchnitzelBackpackable;
import shit.randomfoodstuff.item.ItemSchnitzelBackpack;

public class ContainerSchnitzelBackpack extends Container{

	private final EntityPlayer player;
	public final InventorySchnitzelBackpack inventory;
	
	private final int playerInvRows = 3;
	private final int playerInvColums = 9;
	
	private final int invSize = ItemSchnitzelBackpack.getInventorySize();
	
	public ContainerSchnitzelBackpack(EntityPlayer player, InventorySchnitzelBackpack inventory) {
		this.player = player;
		this.inventory = inventory;
		int slotEquipped = player.inventory.currentItem;
		
		for (int i = 0; i < invSize; i++) {
			this.addSlotToContainer(new SlotSchnitzelBackpack(this, player, inventory, i, 8 + i * 18, 16));
		}
		
		for (int i = 0; i < playerInvRows; i++) {
			for (int j = 0; j < playerInvColums; j++) {
				this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 48 + i * 18));
			}
		}
		
		for (int i = 0; i < playerInvColums; i++) {
			if (i == slotEquipped) {
				this.addSlotToContainer(new SlotDisabled(player.inventory, i, 8 + i * 18, 106));
			} else {
				this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 106));
			}
		}
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		if (!player.worldObj.isRemote) {
			saveInventory(player);
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            if (itemstack1.getItem() instanceof ISchnitzelBackpackable) {
	            itemstack = itemstack1.copy();
	
	            if (slotIndex < 9)
	            {
	                if (!this.mergeItemStack(itemstack1, 9, this.inventorySlots.size(), true))
	                {
	                    return null;
	                }
	            }
	            else if (!this.mergeItemStack(itemstack1, 0, 9, false))
	            {
	                return null;
	            }
	
	            if (itemstack1.stackSize == 0)
	            {
	                slot.putStack((ItemStack)null);
	            }
	            else
	            {
	                slot.onSlotChanged();
	            }
            }
        }

        return itemstack;
	}
	
	public void saveInventory(EntityPlayer player) {
		inventory.onGuiSaved(player);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		if (!player.worldObj.isRemote) {
			saveInventory(player);
		}
	}
	
	

}
