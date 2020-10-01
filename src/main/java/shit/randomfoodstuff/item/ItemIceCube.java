package shit.randomfoodstuff.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.util.DurationHelper;

public class ItemIceCube extends ItemFood{

	public ItemIceCube(int healAmount, float saturation) {
		super(healAmount, saturation, false);
		
		setUnlocalizedName("itemIceCube");
		setTextureName(Reference.TextureName + "itemIceCube");
		setMaxStackSize(16);
		setCreativeTab(RFMain.tab);
		setPotionEffect(Potion.fireResistance.id, DurationHelper.minutesToSeconds(3), 1, 1.0F);
	}
	
	@Override
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
		list.add("Sometimes it's good to have something cold...");
		super.addInformation(p_77624_1_, p_77624_2_, list, p_77624_4_);
	}

}
