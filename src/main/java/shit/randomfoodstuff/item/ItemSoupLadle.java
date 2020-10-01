package shit.randomfoodstuff.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;

public class ItemSoupLadle extends Item{

	public ItemSoupLadle() {
		setUnlocalizedName("itemSoupLadle");
		setTextureName(Reference.TextureName + "itemSoupLadle");
		setMaxStackSize(1);
		setCreativeTab(RFMain.tab);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par1) {
		list.add("Used to clear the content of the Cooking Pot");
		super.addInformation(stack, player, list, par1);
	}
	
	@Override
	public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
		return true;
	}
}
