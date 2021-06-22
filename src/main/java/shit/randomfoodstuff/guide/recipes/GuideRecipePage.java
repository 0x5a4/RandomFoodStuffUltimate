package shit.randomfoodstuff.guide.recipes;

import shit.randomfoodstuff.client.gui.GuiGuide;
import shit.randomfoodstuff.guide.GuideFormatter;

public class GuideRecipePage {

    public GuideRecipeCollection[] recipeCollections = new GuideRecipeCollection[2];

    public void drawToScreen(GuiGuide screen) {
        int x = 79;
        int y = 14 + GuideFormatter.FONT_HEIGHT;
        for (GuideRecipeCollection recipe : recipeCollections) {
            if (recipe != null) {
                recipe.drawToScreen(screen, x, y);
                y += recipe.getWidth() + 3;
            }
        }
    }

    public boolean addRecipeCollection(GuideRecipeCollection recipe) {
        if (recipeCollections[0] == null) {
            recipeCollections[0] = recipe;
            return true;
        } else if (recipeCollections[1] == null) {
            recipeCollections[1] = recipe;
            return true;
        }
        return false;
    }
}
