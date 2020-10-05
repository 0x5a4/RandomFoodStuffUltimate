package shit.randomfoodstuff.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.cooking.ISpice;
import shit.randomfoodstuff.potion.RFPotion;
import shit.randomfoodstuff.util.DurationHelper;

public class ItemMutantLauch extends ItemFood implements ISpice{

	public ItemMutantLauch(int healAmount, float saturation) {
		super(healAmount, saturation, false);
		
		setUnlocalizedName("itemMutantLauch");
		setTextureName(Reference.TextureName + "itemMutantLauch");
		setMaxStackSize(16);
		setCreativeTab(RFMain.cTab);
	}
		
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par1) {
		list.add("Becoming a Lauch");
		super.addInformation(stack, player, list, par1);
	}
	
	@Override
	protected void onFoodEaten(ItemStack p_77849_1_, World p_77849_2_, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(RFPotion.potionLauch.id, DurationHelper.minutesToTicks(2)));
		super.onFoodEaten(p_77849_1_, p_77849_2_, player);
	}

	@Override
	public String getEffectName(ItemStack stack) {
		return "lauch";
	}

}
