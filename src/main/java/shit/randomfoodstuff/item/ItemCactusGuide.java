package shit.randomfoodstuff.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;

public class ItemCactusGuide extends Item{
	
	public ItemCactusGuide() {
		setUnlocalizedName("itemCactusGuide");
		setTextureName(Reference.TextureName + "itemCactusGuide");
		setMaxStackSize(1);
		setCreativeTab(RFMain.tab);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		RFMain.proxy.openGuideGui();
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag) {
		list.add(EnumChatFormatting.GREEN + "Mr. President");
		super.addInformation(stack, player, list, flag);
	}

}
