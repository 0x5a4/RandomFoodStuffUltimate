package shit.randomfoodstuff.guide.recipes;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public interface IRecipeDiscoverer {
	
	public ArrayList<IGuideRecipe> findRecipes(ItemStack stack);

	

}
