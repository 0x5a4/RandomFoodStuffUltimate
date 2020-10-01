package shit.randomfoodstuff.client.gui.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.api.ItemInfo.Layout;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import shit.randomfoodstuff.RandomBlocks;

public class NEIConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		System.out.println("Sneaking into NEI... Stay silent");
		//Recipe Handlers
		API.registerRecipeHandler(new NEIFatInfuserRecipeHandler());
		API.registerUsageHandler(new NEIFatInfuserRecipeHandler());
		
		//Highlight Handlers
		API.registerHighlightHandler(new ALBHighlightHandler(), Layout.HEADER);
		API.registerHighlightIdentifier(RandomBlocks.blockAwfull, new ALBHighlightHandler());
		API.registerHighlightHandler(new CookingPotHighlightHandler(), Layout.HEADER);
		API.registerHighlightHandler(new FatInfuserHighlightHandler(), Layout.HEADER);
		
		//Hide Items
		API.hideItem(new ItemStack(Item.getItemFromBlock(RandomBlocks.blockLauch)));
		API.hideItem(new ItemStack(Item.getItemFromBlock(RandomBlocks.blockPepperoni)));
		API.hideItem(new ItemStack(Item.getItemFromBlock(RandomBlocks.blockMateTee)));
		
		System.out.println("We got this. Now lets get outta here");
	}

	@Override
	public String getName() {
		return "RandomFoodStuff4 NEI";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

}
