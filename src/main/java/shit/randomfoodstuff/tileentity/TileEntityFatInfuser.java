package shit.randomfoodstuff.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.MinecraftForge;
import shit.randomfoodstuff.event.FatInfusingEvent;
import shit.randomfoodstuff.util.FatInfuserRecipeHandler;

public class TileEntityFatInfuser extends RandomInventoryTileEntity {

	// Slot 1 = Item Input
	// Slot 2 = Fuel Input

	public int burnTime = 0;
	public int cookTime;
	public int currentItemBurnTime;

	public TileEntityFatInfuser() {
		super(3);
	}

	public boolean isBurning() {
		return this.burnTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		return this.cookTime * par1 / getSpeed();
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1) {
		if (this.currentItemBurnTime == 0) {
			this.currentItemBurnTime = 200;
		}

		return this.burnTime * par1 / this.currentItemBurnTime;
	}

	public void updateEntity() {
		boolean flag = this.burnTime > 0;
		boolean flag1 = false;

		if (this.burnTime > 0) {
			--this.burnTime;
		}

		if (!this.worldObj.isRemote) {
			if (this.burnTime != 0 || this.slots[2] != null && this.slots[1] != null) {
				if (this.burnTime == 0 && this.canSmelt()) {
					this.currentItemBurnTime = this.burnTime = getItemBurnTime(this.slots[2]);

					if (this.burnTime > 0) {
						flag1 = true;

						if (this.slots[2] != null) {
							--this.slots[2].stackSize;

							if (this.slots[2].stackSize == 0) {
								this.slots[2] = slots[2].getItem().getContainerItem(slots[2]);
							}
						}
					}
				}

				if (this.isBurning() && this.canSmelt()) {
					++this.cookTime;

					if (this.cookTime >= this.getSpeed()) {

						this.cookTime = 0;
						this.smeltItem();
						flag1 = true;
					}
				} else {
					this.cookTime = 0;
				}
			}
		}

		if (slots[1] == null && cookTime > 0) {
			cookTime = 0;
			flag1 = true;
		}

		if (flag1) {
			this.markDirty();
		}
	}

	private boolean canSmelt() {
		if (this.slots[1] == null) {
			return false;
		} else {
			ItemStack itemstack = FatInfuserRecipeHandler.getResultFor(slots[1]);
			if (itemstack == null)
				return false;
			if (this.slots[0] == null)
				return true;
			if (!this.slots[0].isItemEqual(itemstack))
				return false;
			int result = slots[0].stackSize + itemstack.stackSize;
			return result <= getInventoryStackLimit() && result <= this.slots[0].getMaxStackSize();
		}
	}

	public void smeltItem() {
		if (this.canSmelt()) {
			ItemStack itemstack = FatInfuserRecipeHandler.getResultFor(slots[1]);
			FatInfusingEvent.Post event = new FatInfusingEvent.Post(getWorldObj(), xCoord, yCoord, zCoord, itemstack);
			MinecraftForge.EVENT_BUS.post(event);

			if (this.slots[0] == null) {
				this.slots[0] = itemstack.copy();
			} else if (this.slots[0].getItem() == itemstack.getItem()) {
				this.slots[0].stackSize += itemstack.stackSize;
			}

			--this.slots[1].stackSize;

			if (this.slots[1].stackSize <= 0) {
				this.slots[1] = null;
			}
		}
	}

	public static int getItemBurnTime(ItemStack stack) {
		if (stack == null) {
			return 0;
		} else {
			return 600;
		}
	}

	public static boolean isItemFuel(ItemStack stack) {
		return getItemBurnTime(stack) > 0;
	}

	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		NBTTagList list = nbt.getTagList("Item", 10);
		this.slots = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound compound = (NBTTagCompound) list.getCompoundTagAt(i);
			byte b = compound.getByte("Slot");

			if (b >= 0 && b < this.slots.length) {
				this.slots[b] = ItemStack.loadItemStackFromNBT(compound);
			}
		}

		this.burnTime = (int) nbt.getShort("BurnTime");
		this.cookTime = (int) nbt.getShort("CookTime");
		this.currentItemBurnTime = (int) nbt.getShort("CurrentBurnTime");
	}

	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setShort("BurnTime", (short) this.burnTime);
		nbt.setShort("CookTime", (short) this.cookTime);
		nbt.setShort("CurrentBurnTime", (short) this.currentItemBurnTime);

		NBTTagList list = new NBTTagList();

		for (int i = 0; i < this.slots.length; i++) {
			if (this.slots[i] != null) {
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("Slot", (byte) i);
				this.slots[i].writeToNBT(compound);
				list.appendTag(compound);
			}
		}

		nbt.setTag("Item", list);
	}

	public static int getSpeed() {
		return 400;
	}

	@Override
	public String getInventoryName() {
		return "container.fatinfuser";
	}

}
