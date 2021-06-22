package shit.randomfoodstuff.guide.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import shit.randomfoodstuff.util.ItemHelper;

import java.util.ArrayList;

public class CraftingRecipeDiscoverer implements IRecipeDiscoverer {

    @Override
    public ArrayList<IGuideRecipe> findRecipes(ItemStack stack) {
        ArrayList<IGuideRecipe> result = new ArrayList<IGuideRecipe>();
        for (Object o : CraftingManager.getInstance().getRecipeList()) {
            if (o instanceof IRecipe) {
                IRecipe recipe = (IRecipe) o;
                if (ItemHelper.compareStacks(stack, recipe.getRecipeOutput())) {
                    if (recipe instanceof ShapelessRecipes || recipe instanceof ShapedRecipes) {
                        result.add(new GuideCraftingRecipe(recipe));
                    }
                }
            }
        }
        return result;
    }

}
