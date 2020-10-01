package shit.randomfoodstuff.item;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.client.gui.GuiHandler;
import shit.randomfoodstuff.cooking.INotCookable;
import shit.randomfoodstuff.tileentity.container.InventorySchnitzelBackpack;

public class ItemSchnitzelBackpack extends Item implements INotCookable{
	
	private static final String currentSchnitzel = "CurrentSchnitzel";
	
	public ItemSchnitzelBackpack() {
		super();
		
		setTextureName(Reference.TextureName + "itemSchnitzelBackpack");
		setUnlocalizedName("itemSchnitzelBackpack");
		setMaxStackSize(1);
		setCreativeTab(RFMain.tab);
	}
	
	public static String getItemDisplayName(ItemStack stack) {
		return stack.getDisplayName();
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		int currentSlot = getCurrentItem(stack);
		ItemStack[] inventory = getInternalInventory(stack);
		
		if (inventory == null) {
			return stack;
		}
		
		ItemStack itemEaten = inventory[currentSlot];
		
		if (itemEaten == null) {
			return stack;
		}
		
		inventory[currentSlot] = itemEaten.getItem().onEaten(itemEaten, world, player);
		saveInventory(stack, inventory);
		return stack;
	}
	
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registry) {
		this.itemIcon = registry.registerIcon(Reference.TextureName + "itemSchnitzelBackpack");
		super.registerIcons(registry);
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		if (!(stack.getTagCompound() == null))
        {
            ItemStack[] inv = getInternalInventory(stack);

            if (inv == null)
            {
                return this.itemIcon;
            }

            ItemStack item = getCurrentSchnitzel(stack);

            if (item != null)
            {
                return item.getIconIndex();
            }
        }

        return this.itemIcon;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (player.isSneaking()) {
			InventorySchnitzelBackpack.setUUID(stack);
			FMLNetworkHandler.openGui(player, RFMain.modinstance, GuiHandler.GuiIDSchnitzelBackpack, world, (int) player.posX, (int) player.posY, (int) player.posZ);
			return stack;
		} else {
			int currentSlot = getCurrentItem(stack);
			ItemStack[] inventory = getInternalInventory(stack);
			
			if (inventory == null) {
				return stack;
			}
			
			ItemStack itemUsed = inventory[currentSlot];
			
			if (itemUsed == null) {
				return stack;
			}
			
			saveInventory(stack, inventory);
			
			if (player.canEat(((ISchnitzelBackpackable) itemUsed.getItem()).alwaysEatable(itemUsed)) && ((ISchnitzelBackpackable) itemUsed.getItem()).canEatItem(itemUsed)) {
				player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
				return super.onItemRightClick(stack, world, player);
			}
			
		}
		
		return stack;
	}
	
	public static int getInventorySize() {
		return 9;
	}
	
	 public static int next(int mode) {
		 int index = mode + 1;

		 if (index >= getInventorySize()) {
			 index = 0;
		 }

		 return index;
	 }

	 public static int prev(int mode) {
		 int index = mode - 1;

		 if (index < 0) {
			 index = getInventorySize();
		 }

		 return index;
	 }
	 
	 private static void initModeTag(ItemStack itemStack) {
		 if (itemStack.stackTagCompound == null) {
			 itemStack.stackTagCompound = new NBTTagCompound();
			 itemStack.stackTagCompound.setInteger(currentSchnitzel, getInventorySize());
		 }
	 }
	 
	 public static ItemStack getCurrentSchnitzel(ItemStack itemStack) {
		 if (itemStack != null && itemStack.getItem() instanceof ItemSchnitzelBackpack) {
			 ItemStack[] itemStacks = getInternalInventory(itemStack);
			 int currentSlot = getCurrentItem(itemStack);
			 if (itemStacks != null) {
				 return itemStacks[currentSlot];
			 }
		 }

		 return null;
	 }

	 public static int getCurrentItem(ItemStack itemStack) {
		 if (itemStack != null && itemStack.getItem() instanceof ItemSchnitzelBackpack) {
			 initModeTag(itemStack);
			 int currentSchnitzel = itemStack.stackTagCompound.getInteger(ItemSchnitzelBackpack.currentSchnitzel);
			 currentSchnitzel = MathHelper.clamp_int(currentSchnitzel, 0, getInventorySize() - 1);
			 return currentSchnitzel;
		 }

		 return 4;
	 }
	 
	 public static ItemStack[] getInternalInventory(ItemStack itemStack) {
		 initModeTag(itemStack);
		 NBTTagCompound tagCompound = itemStack.getTagCompound();

		 if (tagCompound == null) {
			 return null;
		 }

		 ItemStack[] inv = new ItemStack[9];
		 NBTTagList tagList = tagCompound.getTagList(InventorySchnitzelBackpack.items, 10);
		 
		 if (tagList == null) {
			 return null;
		 }

		 for (int i = 0; i < tagList.tagCount(); i++) {
			 NBTTagCompound tag = tagList.getCompoundTagAt(i);
			 int slot = tag.getByte("Slot");

			 if (slot >= 0 && slot < getInventorySize()) {
				 inv[slot] = ItemStack.loadItemStackFromNBT(tag);
			 }
		 }

		 return inv;
	 }
	 
	 
	 public static void saveInventory(ItemStack itemStack, ItemStack[] inventory) {
		 NBTTagCompound itemTag = itemStack.getTagCompound();

		 if (itemTag == null) {
			 itemStack.setTagCompound(new NBTTagCompound());
		 }

		 NBTTagList itemList = new NBTTagList();

		 for (int i = 0; i < getInventorySize(); i++) {
			 if (inventory[i] != null) {
				 NBTTagCompound tag = new NBTTagCompound();
				 tag.setByte("Slot", (byte) i);
				 inventory[i].writeToNBT(tag);
				 itemList.appendTag(tag);
			 }
		 }

		 itemTag.setTag(InventorySchnitzelBackpack.items, itemList);
	 }

	 public static void cycleSchnitzel(boolean flag,int playerId, ItemStack stack, int mode) {
		 if (stack != null && stack.getItem() instanceof ItemSchnitzelBackpack) {
			 initModeTag(stack);
			 ItemStack[] inv = getInternalInventory(stack);
			 if (inv != null && flag) {
				 ItemStack selectedStack = inv[MathHelper.clamp_int(mode, 0, getInventorySize() - 1)];
				 if (selectedStack != null) {
					 for (Object player : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
						 if (player instanceof EntityPlayer) {
							 if (((EntityPlayer) player).getEntityId() == playerId) {
								 if (selectedStack.getItem() instanceof ItemWarpingSchnitzel) {
									 String displayName = getItemDisplayName(selectedStack);
									 if (!((EntityPlayer) player).worldObj.isRemote) {
										 System.out.println(displayName);
										 if (!displayName.equals(StatCollector.translateToLocal("item.itemWarpingSchnitzel.name"))) {
											 ((EntityPlayer) player).addChatComponentMessage(new ChatComponentText("Selected Warping Schnitzel to " + displayName));
										 }
										 break;
									 }
								 }
							 }
						 }
					 }
				 }
			 }
			 stack.stackTagCompound.setInteger(currentSchnitzel, mode);
		 }
	 }

	 @Override
	 public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
		 if (!(stack.getTagCompound() == null)) {
			 this.tickInternalInventory(stack, world, entity, par4, par5);
		 }
	 }

	 public void tickInternalInventory(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
		 ItemStack[] inv = getInternalInventory(stack);

		 if (inv == null) {
			 return;
		 }

		 for (int i = 0; i < getInventorySize(); i++) {
			 if (inv[i] == null) {
				 continue;
			 }
			 
			 if (inv[i].stackSize <= 0) {
				 inv[i] = null;
				 continue;
			 }
			 inv[i].getItem().onUpdate(inv[i], world, entity, par4, par5);
		 }
		 
		 saveInventory(stack, inv);
	 }
	 
	 @Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.eat;
	}
	 
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		int currentSlot = getCurrentItem(stack);
		ItemStack[] inventory = getInternalInventory(stack);
		
		if (inventory[currentSlot] != null) {
			return inventory[currentSlot].getMaxItemUseDuration();
		} else {
			return 0;
		}
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		int currentSlot = getCurrentItem(stack);
		ItemStack[] inventory = getInternalInventory(stack);
		
		if (inventory[currentSlot] != null) {
			return inventory[currentSlot].getItem().showDurabilityBar(inventory[currentSlot]);
		} else {
			return false;
		}
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		int currentSlot = getCurrentItem(stack);
		ItemStack[] inventory = getInternalInventory(stack);
		
		if (inventory[currentSlot] != null) {
			return inventory[currentSlot].getItem().getDurabilityForDisplay(inventory[currentSlot]);
		} else {
			return 0;
		}
	}
}
