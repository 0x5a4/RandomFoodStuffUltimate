package shit.randomfoodstuff.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.cooking.ISpecialFood;
import shit.randomfoodstuff.util.ItemHelper;

public class ItemArthur extends ItemFood implements ISpecialFood{
	
	private IIcon rawArthur, roastedArthur;

	public ItemArthur(int healAmount, float saturation) {
		super(healAmount, saturation, false);
		
		setCreativeTab(RFMain.cTab);
		setHasSubtypes(true);
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item , 1, 1));
		super.getSubItems(item, tab, list);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		switch(stack.getItemDamage()) {
		case 1:
			return "itemRoastedArthur";
		default:
			return "itemRawArthur";
		}
	}
	
	@Override
	public void registerIcons(IIconRegister registry) {
		rawArthur = registry.registerIcon(Reference.TextureName + "itemRawArthur");
		roastedArthur = registry.registerIcon(Reference.TextureName + "itemRoastedArthur");
		super.registerIcons(registry);
	}
	
	@Override
	public IIcon getIconFromDamage(int damage) {
		switch(damage) {
		case 1:
			return roastedArthur;
		default:
			return rawArthur;
		}
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		switch(stack.getItemDamage()) {
		case 1:
			ItemHelper.addFoodStats(player, 5, ItemHelper.getSaturationAmplifier(5, 3)); break;
		case 0:
			ItemHelper.addFoodStats(player, 2, ItemHelper.getSaturationAmplifier(2, 1)); break;
		}
		super.onFoodEaten(stack, world, player);
	}

	@Override
	public int getFoodLevelForStack(ItemStack stack) {
		switch(stack.getItemDamage()) {
		case 0:
			return 2;
		case 1:
			return 7;
		}
		return 0;
	}

	@Override
	public float getSaturationForStack(ItemStack stack) {
		switch(stack.getItemDamage()) {
		case 0:
			return 1;
		case 1:
			return 5;
		}
		return 0;
	}

	
}
