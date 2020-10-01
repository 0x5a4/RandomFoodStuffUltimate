package shit.randomfoodstuff.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.cooking.ISpice;
import shit.randomfoodstuff.cooking.SoupRegistry;

public class ItemClubMate extends ItemFood implements ISpice{

	public ItemClubMate(int healAmount, float saturation) {
		super(healAmount, saturation, true);
		
		setUnlocalizedName("itemClubMate");
		setTextureName(Reference.TextureName + "itemClubMate");
		setCreativeTab(RFMain.tab);
		
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.drink;
	}
	
	@Override
	public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer player) {
		--p_77654_1_.stackSize;
        player.getFoodStats().func_151686_a(this, p_77654_1_);
        p_77654_2_.playSoundAtEntity(player, "random.drink", 0.5F, p_77654_2_.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(p_77654_1_, p_77654_2_, player);
        player.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
        
        return p_77654_1_;
	}
	
	@Override
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
		list.add("Das goettliche Getraenk!!");
		super.addInformation(p_77624_1_, p_77624_2_, list, p_77624_4_);
	}

	@Override
	public String getEffectName(ItemStack stack) {
		return "awake";
	}

}
