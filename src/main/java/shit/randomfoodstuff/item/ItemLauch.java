package shit.randomfoodstuff.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.entity.EntityItemLauch;
import shit.randomfoodstuff.util.RandomFoodSeed;

public class ItemLauch extends RandomFoodSeed {

	public ItemLauch(int parHealAmount, float parSaturationModifier, Block parBlockPlant, Block parSoilBlock) {
		super(parHealAmount, parSaturationModifier, parBlockPlant, parSoilBlock);
		
		setUnlocalizedName("itemLauch");
		setTextureName(Reference.TextureName + "itemLauchSeed");
		setCreativeTab(RFMain.tab);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add("This appears to be some sort of Mathis...");
		super.addInformation(stack, player, list, bool);
	}
	
	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return true;
	}
	
	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		return new EntityItemLauch(world, location, itemstack);
	}

}
