package shit.randomfoodstuff.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public abstract class RandomInventoryTileEntity extends RandomTileEntity implements ISidedInventory {

	//Slot 0 = Output
	protected ItemStack[] slots;
	
	public RandomInventoryTileEntity(int inventorySize) {
		this.slots = new ItemStack[inventorySize];
	}
	
	public boolean containsItems() {
		for (ItemStack stack : slots) {
			if (stack != null) {
				return true;
			}
		}
		return false;
	}
	
	public void clearInventory() {
		for (int i = 0; i < slots.length; i++) {
			slots[i] = null;
		}
	}
	@Override
	public int getSizeInventory() {
		return this.slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return slots[slot];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.slots[par1] != null) {
			ItemStack stack;

			if (this.slots[par1].stackSize <= par2) {
				stack = this.slots[par1];
				this.slots[par1] = null;
				return stack;
			} else{
				stack = this.slots[par1].splitStack(par2);

				if (this.slots[par1].stackSize == 0) {
					this.slots[par1] = null;
				}
					
				return stack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.slots[par1] != null) {
			ItemStack stack = this.slots[par1];
			this.slots[par1] = null;
			return stack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack stack) {
		this.slots[par1] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return slot != 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int par1) {
		return null;
	}

	@Override
	public boolean canInsertItem(int par1, ItemStack stack, int par2) {
		return false;
	}

	@Override
	public boolean canExtractItem(int par1, ItemStack stack, int par2) {
		return false;
	}

}
