package shit.randomfoodstuff.tileentity.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import shit.randomfoodstuff.tileentity.TileEntityFatInfuser;
import shit.randomfoodstuff.util.FatInfuserRecipeHandler;

public class ContainerFatInfuser extends Container {
	
	private TileEntityFatInfuser fatinfuser;
	
	public int lastBurnTime;
	public int lastCookTime;
	public int lastCurrentItemBurnTime;
	
	public ContainerFatInfuser(InventoryPlayer inventory, TileEntityFatInfuser entity) {
		this.fatinfuser = entity;
		
		//Output
		this.addSlotToContainer(new SlotFatInfuserOutput(entity, 0, 136, 18));
		//Item In
		this.addSlotToContainer(new Slot(entity, 1, 34, 19));
		//Fuel In
		this.addSlotToContainer(new Slot(entity, 2, 81, 59));
		
		//Player Inv
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.lastCookTime != this.fatinfuser.cookTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.fatinfuser.cookTime);
            }

            if (this.lastBurnTime != this.fatinfuser.burnTime)
            {
                icrafting.sendProgressBarUpdate(this, 1, this.fatinfuser.burnTime);
            }

            if (this.lastBurnTime != this.fatinfuser.currentItemBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 2, this.fatinfuser.currentItemBurnTime);
            }
        }

        this.lastCookTime = this.fatinfuser.cookTime;
        this.lastBurnTime = this.fatinfuser.burnTime;
        this.lastBurnTime = this.fatinfuser.currentItemBurnTime;
	}
	
	 @SideOnly(Side.CLIENT)
	 @Override
	 public void updateProgressBar(int par1, int par2) {
		 if (par1 == 0) {
			 this.fatinfuser.cookTime = par2;
		 }

		 if (par1 == 1) {
			 this.fatinfuser.burnTime = par2;
		 }

		 if (par1 == 2) {
			 this.fatinfuser.currentItemBurnTime = par2;
		 }
		 
	 }
	 
	public ItemStack transferStackInSlot(EntityPlayer player, int clickedSlot) {
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(clickedSlot);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (clickedSlot == 1){
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (clickedSlot != 2 && clickedSlot != 1) {
				if (FatInfuserRecipeHandler.canCraft(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				} else if (FatInfuserRecipeHandler.isItemFuel(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 2, 0, false)) {
						return null;
					}
				} else if (clickedSlot >= 3 && clickedSlot < 30) {
					if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
						return null;
					}
				} else if (clickedSlot >= 30 && clickedSlot < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
				return null;
			}
			
			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack)null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	 }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return fatinfuser.isUseableByPlayer(player);
	}

}
