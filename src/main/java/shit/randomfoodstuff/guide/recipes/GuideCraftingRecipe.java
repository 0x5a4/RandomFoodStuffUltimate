package shit.randomfoodstuff.guide.recipes;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import shit.randomfoodstuff.client.gui.GuiGuide;
import shit.randomfoodstuff.util.GuiHelper;

public class GuideCraftingRecipe implements IGuideRecipe{

	protected IRecipe recipe;
	protected ArrayList<ItemStack> craftingGrid = new ArrayList<ItemStack>();
	protected boolean isShaped = true;
	
	public GuideCraftingRecipe(IRecipe recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public void loadRecipe() {
		craftingGrid.clear();
		if (this.recipe instanceof ShapedRecipes) {
			ShapedRecipes shapedRecipe = (ShapedRecipes) this.recipe;
			int recipeWidth = shapedRecipe.recipeWidth;
			int recipeHeight = shapedRecipe.recipeHeight;
			for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    if (x < recipeWidth && y < recipeHeight) {
                        craftingGrid.add(shapedRecipe.recipeItems[y * recipeWidth + x]);
                    } else {
                        craftingGrid.add(null);
                    }
                }
            }
			isShaped = true;
		} else if (this.recipe instanceof ShapelessRecipes) {
			ShapelessRecipes shapelessRecipe = (ShapelessRecipes) this.recipe;
			
			for (int i = 0; i < shapelessRecipe.getRecipeSize(); i++) {
                craftingGrid.add((ItemStack) shapelessRecipe.recipeItems.get(i));
            }
            
            for (int i = craftingGrid.size(); i < 9; i++) {
                craftingGrid.add(null);
            }
            
            isShaped = false;
		}
	}

	@Override
	public void drawToScreen(GuiGuide screen, int x, int y) {
		//Draw Background
        GL11.glColor4f(1, 1, 1, 1);
        screen.mc.getTextureManager().bindTexture(IGuideRecipe.recipeTextures);
        screen.drawTexturedModalRect(x, y, 0, 0, this.getWidth(), this.getHeight());
        
        //Draw Recipe
        if (!craftingGrid.isEmpty()) {
	        int index = 0;
	        for (int i = 0; i < 3; i++) {
	            int stackX = x + 6 + (18 * i);
	            for (int j = 0; j < 3; j++) {
	                int stackY = y + 6 + (18 * j);
	                ItemStack stack = craftingGrid.get(index);
	                if (stack != null) {
	                    GuiHelper.drawItemStack(screen, stack, stackX, stackY);
	                }
	                index++;
	            }
	        }
	        GuiHelper.drawItemStack(screen, this.recipe.getRecipeOutput(), x + 99, y + 23);
        }
	}

	@Override
	public ItemStack getOutput() {
		return null;
	}

	@Override
	public int getWidth() {
		return 126;
	}

	@Override
	public int getHeight() {
		return maxHeight;
	}
	
	public boolean isShaped() {
		return isShaped;
	}

	@Override
	public String getHeading() {
		if (isShaped) {
			return "Shaped Recipe";
		} else {
			return "Shapeless Recipe";
		}
	}

}
