package shit.randomfoodstuff.tileentity.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import shit.randomfoodstuff.item.ItemSchnitzelBackpack;

import java.util.UUID;

public class InventorySchnitzelBackpack implements IInventory {

    public ItemStack parent;
    private ItemStack[] slots;

    protected static String mostSig = "MostSig";
    protected static String leastSig = "LeastSig";
    public static String items = "Items";

    public InventorySchnitzelBackpack(ItemStack parent) {
        this.parent = parent;

        slots = new ItemStack[ItemSchnitzelBackpack.getInventorySize()];

        readFromNBT(parent.getTagCompound());
    }

    public void onGuiSaved(EntityPlayer entityPlayer) {
        parent = findParentStack(entityPlayer);

        if (parent != null) {

            save();
        }
    }

    public ItemStack findParentStack(EntityPlayer entityPlayer) {
        if (hasUUID(parent)) {
            UUID parentItemStackUUID = new UUID(parent.getTagCompound().getLong(mostSig), parent.getTagCompound().getLong(leastSig));
            for (int i = 0; i < entityPlayer.inventory.getSizeInventory(); i++) {
                ItemStack itemStack = entityPlayer.inventory.getStackInSlot(i);

                if (hasUUID(itemStack)) {
                    if (itemStack.getTagCompound().getLong(mostSig) == parentItemStackUUID.getMostSignificantBits() && itemStack.getTagCompound().getLong(leastSig) == parentItemStackUUID.getLeastSignificantBits()) {
                        return itemStack;
                    }
                }
            }
        }

        return null;
    }

    public void save() {
        NBTTagCompound nbtTagCompound = parent.getTagCompound();

        if (nbtTagCompound == null) {
            nbtTagCompound = new NBTTagCompound();

            UUID uuid = UUID.randomUUID();
            nbtTagCompound.setLong(mostSig, uuid.getMostSignificantBits());
            nbtTagCompound.setLong(leastSig, uuid.getLeastSignificantBits());
        }

        writeToNBT(nbtTagCompound);
        parent.setTagCompound(nbtTagCompound);
    }

    @Override
    public int getSizeInventory() {
        return slots.length;
    }

    @Override
    public ItemStack getStackInSlot(int slotsIndex) {
        return slotsIndex >= 0 && slotsIndex < this.getSizeInventory() ? slots[slotsIndex] : null;
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int par2) {
        ItemStack stack = getStackInSlot(slotIndex);
        if (stack != null) {
            if (stack.stackSize <= par2) {
                setInventorySlotContents(slotIndex, null);
            } else {
                stack = stack.splitStack(par2);
                if (stack.stackSize == 0) {
                    setInventorySlotContents(slotIndex, null);
                }
            }
        }

        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slotIndex) {
        if (slots[slotIndex] != null) {
            ItemStack itemStack = slots[slotIndex];
            slots[slotIndex] = null;
            return itemStack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack stack) {
        if (slotIndex >= 0 && slotIndex < this.getSizeInventory()) {
            this.slots[slotIndex] = stack;
        }
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound != null && nbtTagCompound.hasKey(items)) {
            if (nbtTagCompound.hasKey(items)) {
                NBTTagList tagList = nbtTagCompound.getTagList(items, 10);
                slots = new ItemStack[this.getSizeInventory()];
                for (int i = 0; i < tagList.tagCount(); ++i) {
                    NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
                    byte slotIndex = tagCompound.getByte("Slot");
                    if (slotIndex >= 0 && slotIndex < slots.length) {
                        slots[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
                    }
                }
            }
        }
    }

    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        NBTTagList tagList = new NBTTagList();
        for (int currentIndex = 0; currentIndex < slots.length; ++currentIndex) {
            if (slots[currentIndex] != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) currentIndex);
                slots[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        nbtTagCompound.setTag(items, tagList);
    }

    public static boolean hasTag(ItemStack itemStack, String keyName) {
        return itemStack != null && itemStack.stackTagCompound != null && itemStack.stackTagCompound.hasKey(keyName);
    }

    public static boolean hasUUID(ItemStack itemStack) {
        return hasTag(itemStack, mostSig) && hasTag(itemStack, leastSig);
    }

    public static void setUUID(ItemStack itemStack) {
        initNBTTagCompound(itemStack);

        if (!hasTag(itemStack, mostSig) && !hasTag(itemStack, leastSig)) {
            UUID itemUUID = UUID.randomUUID();
            setLong(itemStack, mostSig, itemUUID.getMostSignificantBits());
            setLong(itemStack, leastSig, itemUUID.getLeastSignificantBits());
        }
    }

    private static void initNBTTagCompound(ItemStack itemStack) {
        if (itemStack.stackTagCompound == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
    }

    public static void setLong(ItemStack itemStack, String keyName, long keyValue) {
        initNBTTagCompound(itemStack);

        itemStack.stackTagCompound.setLong(keyName, keyValue);
    }

    @Override
    public String getInventoryName() {
        return "Schnitzel Backpack";
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
    public void markDirty() {
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int slotIndex, ItemStack stack) {
        return false;
    }

}
