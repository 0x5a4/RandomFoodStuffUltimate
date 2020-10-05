package shit.randomfoodstuff.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.potion.RFPotion;
import shit.randomfoodstuff.util.DurationHelper;

public class ItemFlyingSchnitzel extends ItemFood implements ISchnitzelBackpackable{

	public ItemFlyingSchnitzel(int healAmount, float saturation) {
		super(healAmount, saturation, false);
		
		setUnlocalizedName("itemFlyingSchnitzel");
		setTextureName(Reference.TextureName + "itemFlyingSchnitzel");
		setMaxStackSize(8);
		setAlwaysEdible();
		setCreativeTab(RFMain.cTab);
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		if (!player.isPotionActive(RFPotion.potionFlight)) {
			player.addPotionEffect(new PotionEffect(RFPotion.potionFlight.id, DurationHelper.minutesToTicks(10)));
			if (!world.isRemote) {
				player.addChatComponentMessage(new ChatComponentText("You can now fly for 10 Minutes! YAY!"));
			}
		} else {
			player.addPotionEffect(new PotionEffect(RFPotion.potionFlight.id, DurationHelper.minutesToTicks(10)));
			if (!world.isRemote) {
				player.addChatComponentMessage(new ChatComponentText("Youre flying time has been refreshed :)"));
			}
		}
		super.onFoodEaten(stack, world, player);
	}
	
	@Override
	public boolean canEatItem(ItemStack stack) {
		return true;
	}

	@Override
	public boolean alwaysEatable(ItemStack stack) {
		return true;
	}

}
