package shit.randomfoodstuff.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.cooking.INotCookable;
import shit.randomfoodstuff.cooking.SoupRegistry;
import shit.randomfoodstuff.util.ItemHelper;

public class ItemSoup extends ItemFood implements INotCookable{

	public static final String NBT_FOODLEVEL = "healAmount";
	public static final String NBT_SATURATION = "saturation";
	public static final String NBT_TOOLTIPLENGTH = "toolTipLength";
	public static final String NBT_TOOLTIPPREFIX = "tooltip";
	public static final String NBT_SPICE = "spice";
	public static final String NBT_REAGENT = "reagent";
	public static final String NBT_DISPLAYNAME = "displayName";
	
	public ItemSoup() {
		super(0, 0, false);
		
		setUnlocalizedName("itemSoup");
		setTextureName(Reference.TextureName + "itemSoup");
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if (stack.hasTagCompound()) {
			if (stack.getTagCompound().hasKey(NBT_TOOLTIPLENGTH)) {
				int tooltiplenght = stack.getTagCompound().getInteger(NBT_TOOLTIPLENGTH);
				for (int i = 0; i < tooltiplenght; i++) {
					list.add(stack.getTagCompound().getString(NBT_TOOLTIPPREFIX + i));
				}
			}
			if (GuiScreen.isShiftKeyDown()) {
				list.add("FoodLevel: " + stack.getTagCompound().getInteger(NBT_FOODLEVEL));
				list.add("Saturation: " + stack.getTagCompound().getInteger(NBT_SATURATION));
			}
		} else {
			list.add("Useless if obtained from Creative Inventory");
		}
		super.addInformation(stack, player, list, bool);
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		if (stack.hasTagCompound()) {
			int healAmount = stack.getTagCompound().getInteger(NBT_FOODLEVEL);
			float saturation = stack.getTagCompound().getFloat(NBT_SATURATION);
			float amp = ItemHelper.getSaturationAmplifier(healAmount, saturation);
			ItemHelper.addFoodStats(player, healAmount, amp);
			if (stack.getTagCompound().hasKey(NBT_SPICE)) {
				String spice = stack.getTagCompound().getString(NBT_SPICE);
				System.out.println("Trying to perform soup Effect: " + spice);
				String reagent = stack.getTagCompound().hasKey(NBT_REAGENT) ? stack.getTagCompound().getString(NBT_REAGENT) : null;
				SoupRegistry.performEffect(spice, reagent, player, world);
			}
		}
		ItemStack bowl = new ItemStack(Items.bowl, 1);
		if (!player.inventory.addItemStackToInventory(bowl)) {
			player.dropPlayerItemWithRandomChoice(bowl, false);
		}
	}
	
	public static NBTTagCompound writeNBT(ItemStack stack, String displayName, int healAmount, float saturation) {
		NBTTagCompound itemTag = stack.getTagCompound();
		if (itemTag == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		itemTag = stack.getTagCompound();
		itemTag.setInteger(NBT_FOODLEVEL, healAmount);
		itemTag.setFloat(NBT_SATURATION, saturation);
		itemTag.setString(NBT_DISPLAYNAME, displayName);
		return itemTag;
	}
	
	public static void writeNBT(ItemStack stack, String displayName, int healAmount, float saturation, String effectName) {
		writeNBT(stack, displayName, healAmount, saturation, effectName, null);
	}
	
	public static void writeNBT(ItemStack stack, String displayName, int healAmount, float saturation, String spice, String reagent) {
		NBTTagCompound itemTag = writeNBT(stack, displayName, healAmount, saturation);
		itemTag.setString(NBT_SPICE, spice);
		if (reagent != null) {
			itemTag.setString(NBT_REAGENT, reagent);
		}
	}
	
	public static void writeTooltip(ItemStack stack, ArrayList<String> tooltip) {
		if (tooltip != null) {
			if (!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			}
			NBTTagCompound itemTag = stack.getTagCompound();
			itemTag.setInteger(NBT_TOOLTIPLENGTH, tooltip.size());
			for (int i = 0; i < tooltip.size(); i++) {
				itemTag.setString(NBT_TOOLTIPPREFIX + i, tooltip.get(i));
			}
		}
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (stack.hasTagCompound()) {
			return stack.getTagCompound().getString(NBT_DISPLAYNAME);
		} else {
			return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
		}
	}
}
