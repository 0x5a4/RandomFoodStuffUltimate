package shit.randomfoodstuff.guide.recipes;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import shit.randomfoodstuff.util.ItemHelper;

public class FurnaceRecipeDiscoverer implements IRecipeDiscoverer {

	@Override
	public ArrayList<IGuideRecipe> findRecipes(ItemStack stack) {
		ArrayList<IGuideRecipe> result = new ArrayList<IGuideRecipe>();
		for (Object o : FurnaceRecipes.smelting().getSmeltingList().keySet()) {
			if (o instanceof ItemStack) {
				ItemStack input = (ItemStack) o;
				if (ItemHelper.compareStacks(stack, FurnaceRecipes.smelting().getSmeltingResult(input))) {
					result.add(new GuideFurnaceRecipe(input, stack));
				}
			}
		}
		return result;
	}

}
