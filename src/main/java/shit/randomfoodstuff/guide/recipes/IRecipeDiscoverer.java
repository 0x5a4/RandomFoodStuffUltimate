package shit.randomfoodstuff.guide.recipes;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public interface IRecipeDiscoverer {

    public ArrayList<IGuideRecipe> findRecipes(ItemStack stack);


}
