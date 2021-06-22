package shit.randomfoodstuff.guide;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import shit.randomfoodstuff.client.gui.GuiGuide;
import shit.randomfoodstuff.guide.recipes.GuideRecipeCollection;
import shit.randomfoodstuff.guide.recipes.GuideRecipePage;
import shit.randomfoodstuff.guide.recipes.IRecipeDiscoverer;
import shit.randomfoodstuff.util.ColorHelper;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public abstract class GuideArticle {

    public static final int defaultWidth = 153;
    public static final int defaultHeight = 155;

    protected ArrayList<GuideTextPage> textPages = new ArrayList<GuideTextPage>();
    protected ArrayList<GuideRecipePage> recipePages = new ArrayList<GuideRecipePage>();

    /**
     * True = TextPage; False = RecipePage
     */
    public boolean pageFlag = true;
    protected int pageIndex = 0;
    protected String rawText = "";
    protected String heading = "";
    protected String identifier = null;
    protected GuideFormatter formatter;

    public int xPosition = 79;
    public int yPosition = 14 + GuideFormatter.FONT_HEIGHT;
    public int width = defaultWidth;
    public int heigth = defaultHeight;

    public GuideArticle() {
        this.formatter = new GuideFormatter(this);
        loadContent();
    }

    public abstract void loadContent();

    public void drawToScreen(GuiGuide screen) {
        int x = screen.getXOffset() + this.xPosition;
        int y = screen.getYOffset() + this.yPosition;

        //Draw Heading
        screen.drawCenteredOffsetString(ColorHelper.getColorByName("underline") + getHeading(), 154, 6, 4210752);

        if (pageFlag) {
            for (String s : getTextPage().getLines()) {
                if (s != null) {
                    screen.getFontRenderer().drawString(s, x, y, 4210752);
                    y += getFormatter().getLineSpacing() + GuideFormatter.FONT_HEIGHT;
                }
            }
        } else {
            int recipeX = screen.getXOffset() + 83;
            int recipeY = screen.getYOffset() + 23 + GuideFormatter.FONT_HEIGHT;
            for (GuideRecipeCollection recipe : getRecipePage().recipeCollections) {
                if (recipe != null) {
                    recipe.drawToScreen(screen, recipeX, recipeY);
                    recipeY += recipe.getHeight() + 11;
                }
            }
            RenderHelper.enableStandardItemLighting();
        }
    }

    public void update() {
        if (!pageFlag) {
            GuideRecipePage recipePage = getRecipePage();
            for (GuideRecipeCollection collection : recipePage.recipeCollections) {
                if (collection != null) {
                    collection.update();
                }
            }
        }
    }

    public void next() {
        if (hasNext()) {
            pageIndex++;
        }
    }

    public void prev() {
        if (hasPrev()) {
            pageIndex--;
        }
    }

    public void openTextPage() {
        this.pageFlag = true;
        this.pageIndex = 0;
    }

    public void openRecipePage() {
        this.pageFlag = false;
        this.pageIndex = 0;
        for (GuideRecipePage recipePage : recipePages) {
            for (GuideRecipeCollection collection : recipePage.recipeCollections) {
                if (collection != null) {
                    collection.loadRecipes();
                }
            }
        }
    }

    public void close() {
        pageIndex = 0;
        textPages.clear();
    }

    public void err(String message) {
        System.err.println("Error in GuideEntry:" + identifier);
        System.err.println(message);
    }

    public GuideFormatter getFormatter() {
        return formatter;
    }

    public void addTextPage(GuideTextPage page) {
        this.textPages.add(page);
    }

    public GuideArticle addRecipes(ItemStack stack) {
        for (IRecipeDiscoverer discoverer : GuideRegistry.getRecipeDiscovererList()) {
            GuideRecipeCollection recipeCollection = new GuideRecipeCollection(discoverer.findRecipes(stack));
            if (recipeCollection.size() > 0) {
                this.addRecipeCollection(recipeCollection);
            }
        }
        return this;
    }

    public GuideArticle addRecipeCollection(GuideRecipeCollection recipe) {
        if (!hasRecipePage()) {
            GuideRecipePage page = new GuideRecipePage();
            page.addRecipeCollection(recipe);
            recipePages.add(page);
        } else {
            if (!getRecipePage(recipePages.size() == 1 ? 0 : recipePages.size() - 1).addRecipeCollection(recipe)) {
                GuideRecipePage page = new GuideRecipePage();
                page.addRecipeCollection(recipe);
                recipePages.add(page);
            }
        }
        return this;
    }

    //Setter
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setHeading(String heading) {
        this.heading = ColorHelper.getColorByName("underline") + heading;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    //Getter
    public GuideTextPage getTextPage() {
        return getTextPage(pageIndex);
    }

    public GuideTextPage getTextPage(int index) {
        return textPages.get(index);
    }

    public GuideRecipePage getRecipePage() {
        return getRecipePage(pageIndex);
    }

    public GuideRecipePage getRecipePage(int index) {
        return recipePages.get(index);
    }

    public String getRawText() {
        return StatCollector.translateToLocal("guide.article." + identifier + ".text");
    }

    public int getPageCount() {
        return textPages.size();
    }

    public boolean hasNext() {
        if (pageFlag) {
            return pageIndex < textPages.size() - 1;
        } else {
            return pageIndex < recipePages.size() - 1;
        }
    }

    public boolean hasPrev() {
        return pageIndex > 0;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getHeading() {
        return StatCollector.translateToLocal("guide.article." + identifier + ".heading");
    }

    public boolean hasRecipePage() {
        return recipePages.size() > 0;
    }

}
