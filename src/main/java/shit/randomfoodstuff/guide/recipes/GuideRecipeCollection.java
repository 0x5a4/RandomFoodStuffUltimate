package shit.randomfoodstuff.guide.recipes;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiScreen;
import shit.randomfoodstuff.client.gui.GuiGuide;
import shit.randomfoodstuff.guide.GuideFormatter;

public class GuideRecipeCollection {
	
	protected static int maxRecipeTime = 20 * 2;
	
	protected ArrayList<IGuideRecipe> recipeList = new ArrayList<IGuideRecipe>();
	protected int recipeIndex = 0;
	protected int timeToNextRecipe = maxRecipeTime;
	
	public GuideRecipeCollection() {}

	public GuideRecipeCollection(ArrayList<IGuideRecipe> recipeList) {
		this.recipeList = recipeList;
	}
	
	public void update() {
		if (!GuiScreen.isShiftKeyDown()) {
			if (timeToNextRecipe == 0) {
				timeToNextRecipe = maxRecipeTime;
				nextRecipe();
			}
			timeToNextRecipe--;
		}
	}
	
	public void addRecipe(IGuideRecipe recipe) {
		recipeList.add(recipe);
	}
	
	public void drawToScreen(GuiGuide screen, int x, int y) {
		screen.getFontRenderer().drawString(recipeList.get(0).getHeading(), x, y - GuideFormatter.FONT_HEIGHT, 4210752);
		recipeList.get(recipeIndex).drawToScreen(screen, x, y);
	}
	
	protected void nextRecipe() {
		if (recipeList.size() == 1) { 
			return;
		}
		
		if (recipeIndex < recipeList.size() - 1) {
			recipeIndex++;
		} else {
			recipeIndex = 0;
		}
	}
	
	public void loadRecipes() {
		for (IGuideRecipe recipe : recipeList) {
			recipe.loadRecipe();
		}
	}
	
	public int getWidth() {
		return recipeList.get(0).getWidth();
	}
	
	public int getHeight() {
		return recipeList.get(0).getHeight();
	}

	public int size() {
		return recipeList.size();
	}
	
}
