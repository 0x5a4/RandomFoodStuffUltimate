package shit.randomfoodstuff.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.cooking.ISpice;
import shit.randomfoodstuff.util.ColorHelper;
import shit.randomfoodstuff.util.DurationHelper;

public class ItemSuperMushroom extends ItemFood implements ISpice{

	public ItemSuperMushroom(int healAmount, float saturation) {
		super(healAmount, saturation, false);
		
		setUnlocalizedName("itemSuperMushroom");
		setTextureName(Reference.TextureName + "itemSuperMushroom");
		setMaxStackSize(16);
		setCreativeTab(RFMain.tab);
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.poison.id, DurationHelper.secondsToTicks(20), 2));
		player.addPotionEffect(new PotionEffect(Potion.confusion.id, DurationHelper.secondsToTicks(10), 2));
		super.onFoodEaten(stack, world, player);
	}

	@Override
	public String getEffectName(ItemStack stack) {
		return "moldy";
	}

}
