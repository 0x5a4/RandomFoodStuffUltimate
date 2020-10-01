package shit.randomfoodstuff.tileentity;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.MinecraftForge;
import shit.randomfoodstuff.RandomItems;
import shit.randomfoodstuff.cooking.ISpecialFood;
import shit.randomfoodstuff.cooking.ISpice;
import shit.randomfoodstuff.cooking.SoupRegistry;
import shit.randomfoodstuff.event.SoupCreationEvent;
import shit.randomfoodstuff.event.SoupCreationEvent.CreateSoupEvent;
import shit.randomfoodstuff.item.ItemSoup;
import shit.randomfoodstuff.util.Timer;
import shit.randomfoodstuff.util.ItemHelper;

public class TileEntityCookingPot extends RandomInventoryTileEntity {

	public static final int soupVal = 200;
	public volatile int soupRemaining;
	private Timer time = new Timer(60);
	private boolean flag = false;

	public TileEntityCookingPot() {
		super(5);
	}

	@Override
	public void updateEntity() {
		if (containsItems()) {
			if (flag && soupRemaining <= 0) {
				this.reset();
				return;
			}

			if (isBlockOnFire()) {
				initSoup();
				flag = true;
				time.update();
			}

			if (flag && !isBlockOnFire()) {
				soupRemaining -= 1;
				return;
			}

			if (time.isFinished()) {
				soupRemaining -= 1;
			}
		}
	}

	public boolean isBlockOnFire() {
		Block block = this.worldObj.getBlock(xCoord, yCoord - 1, zCoord);
		return block == Blocks.flowing_lava || block == Blocks.lava || block.getMaterial() == Material.fire;
	}

	public boolean canCreateSoupItem() {
		return soupRemaining >= soupVal && calcHealAmount() > 0;
	}

	public ItemStack createSoupItem() {
		if (canCreateSoupItem()) {
			ItemStack soup = new ItemStack(RandomItems.itemSoup);
			if (containsSpice()) {
				String spice = SoupRegistry.getEffectFromStack(getSpice());
				if (getReagent() != null) {
					if (isSpiceSameType()) {
						ItemSoup.writeNBT(soup, getDisplayName(), calcHealAmount(), calcSaturation(), spice, spice);
					} else {
						ItemSoup.writeNBT(soup, getDisplayName(), calcHealAmount(), calcSaturation(), spice, SoupRegistry.getEffectFromStack(getReagent()));
					}
				} else {
					ItemSoup.writeNBT(soup, getDisplayName(), calcHealAmount(), calcSaturation(), spice, null);
				}
				ArrayList<String> tooltip = new ArrayList<String>();
				tooltip.add("Seasoned with:");
				if (SoupRegistry.canReact(getSpice(), getReagent())) {
					tooltip.add(SoupRegistry.getEffectDisplayName(SoupRegistry.getReaction(getSpice(), getReagent()).getResultEffect()));
				} else {
					if (isSpiceSameType()) {
						String displayName = SoupRegistry.getEffect(SoupRegistry.getEffectFromStack(getSpice())).getSelfReactionDisplayName();
						if (displayName != null) {
							tooltip.add(displayName);
						} else {
							tooltip.add(SoupRegistry.getEffectDisplayName(getSpice()) + " x2");
						}
					} else {
						tooltip.add(SoupRegistry.getEffectDisplayName(getSpice()));
						if (getReagent() != null) {
							tooltip.add(SoupRegistry.getEffectDisplayName(getReagent()));
						}
					}
				}
				for (String s : tooltip) {
					System.out.println("begin " + s + " end");
				}
				ItemSoup.writeTooltip(soup, tooltip);
			} else {
				ItemSoup.writeNBT(soup, getDisplayName(), calcHealAmount(), calcSaturation());
			}
			return soup;
		}
		return null;
	}

	private String getDisplayName() {
		ArrayList<String> list = new ArrayList<String>();
		String result = "";
		for (int i = 0; i < 3; i++) {
			ItemStack stack = slots[i];
			if (stack != null) {
				String s = stack.getDisplayName();
				if (!list.contains(s)) {
					list.add(s);
				}
			}
		}

		for (String s : list) {
			result += s;
			result += " ";
		}
		result += "Soup";
		return result;
	}

	public ItemStack getSpice() {
		return slots[3];
	}

	public ItemStack getReagent() {
		return slots[4];
	}

	private boolean isSpiceSameType() {
		return SoupRegistry.getEffectFromStack(getSpice()).equals(SoupRegistry.getEffectFromStack(getReagent()));
	}

	public int calcHealAmount() {
		int healAmount = 0;
		for (int i = 0; i < 3; i++) {
			if (slots[i] != null) {
				if (slots[i].getItem() instanceof ISpecialFood) {
					healAmount += ((ISpecialFood) slots[i].getItem()).getFoodLevelForStack(slots[i]);
				} else {
					healAmount += ItemHelper.getFoodLevelForItem(slots[i]);
				}
			}
		}
		return healAmount / 2;
	}

	public float calcSaturation() {
		float saturation = 0;
		for (int i = 0; i < 3; i++) {
			if (slots[i] != null) {
				if (slots[i].getItem() instanceof ISpecialFood) {
					saturation += ((ISpecialFood) slots[i].getItem()).getSaturationForStack(slots[i]);
				} else {
					saturation += ItemHelper.getSaturationFromAmplifier(ItemHelper.getFoodLevelForItem(slots[i]), ItemHelper.getSaturationAmplifierForItem(slots[i]));
				}
			}
		}
		return saturation / 2;
	}

	public boolean containsSpice() {
		return slots[3] != null;
	}

	public boolean addSpice(ItemStack spice) {
		spice.stackSize = 1;
		for (int i = 3; i < 5; i++) {
			if (slots[i] == null) {
				slots[i] = spice;
				return true;
			}
		}
		return false;
	}

	public void addIngredient(ItemStack ingred) {
		ingred.stackSize = 1;
		for (int i = 0; i < 3; i++) {
			if (slots[i] == null) {
				slots[i] = ingred;
				return;
			}
		}
	}

	public void negateSoup() {
		this.soupRemaining -= soupVal;
	}

	private void initSoup() {
		if (this.soupRemaining <= 0) {
			soupRemaining = soupVal * 2;
			CreateSoupEvent event = new CreateSoupEvent(getWorldObj(), xCoord, yCoord, zCoord);
			MinecraftForge.EVENT_BUS.post(event);
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	public boolean canAddSpice(ItemStack stack) {
		return slots[3] == null || slots[4] == null;
	}

	public boolean canAddIngredient() {
		for (int i = 0; i < 3; i++) {
			if (slots[i] == null) {
				return true;
			}
		}
		return false;
	}

	public int getPrimarySoupScaled(int par1) {
		return this.getSoupValueForDisplay(1) * par1 / soupVal;
	}

	public int getSecondarySoupScaled(int par1) {
		return this.getSoupValueForDisplay(2) * par1 / soupVal;
	}

	private int getSoupValueForDisplay(int par1) {
		if (par1 == 1) {
			if (this.soupRemaining > soupVal) {
				return soupVal;
			} else {
				return soupRemaining;
			}
		} else {
			if (this.soupRemaining > soupVal) {
				return this.soupRemaining - soupVal;
			}
		}
		return 0;
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setInteger("soup", this.soupRemaining);
		compound.setInteger("timeStart", time.getStartingTime());
		compound.setInteger("timeRemaining", time.getTimeRemaining());
		compound.setBoolean("flag", this.flag);

		NBTTagList list = new NBTTagList();
		for (int i = 0; i < this.slots.length; i++) {
			if (this.slots[i] != null) {
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setByte("Slot", (byte) i);
				this.slots[i].writeToNBT(nbt);
				list.appendTag(nbt);
			}
		}

		compound.setTag("Item", list);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		NBTTagList list = compound.getTagList("Item", 10);
		this.slots = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt = (NBTTagCompound) list.getCompoundTagAt(i);
			byte b = nbt.getByte("Slot");

			if (b >= 0 && b < this.slots.length) {
				this.slots[b] = ItemStack.loadItemStackFromNBT(nbt);
			}
		}

		this.soupRemaining = compound.getInteger("soup");
		this.time.setTo(compound.getInteger("timeStart"), compound.getInteger("timeRemaining"));
		this.flag = compound.getBoolean("flag");
	}

	public void reset() {
		this.clearInventory();
		this.time.refresh();
		this.soupRemaining = 0;
		flag = false;
	}

	@Override
	public String getInventoryName() {
		return "container.cookingpot";
	}

}
