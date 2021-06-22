package shit.randomfoodstuff.guide.recipes;

import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import shit.randomfoodstuff.client.gui.GuiGuide;
import shit.randomfoodstuff.util.GuiHelper;

public class GuideFurnaceRecipe implements IGuideRecipe {

    protected ItemStack output;
    protected ItemStack input;

    public GuideFurnaceRecipe(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void loadRecipe() {
    }

    @Override
    public void drawToScreen(GuiGuide screen, int x, int y) {
        //Draw Background
        GL11.glColor4f(1, 1, 1, 1);
        screen.mc.getTextureManager().bindTexture(IGuideRecipe.recipeTextures);
        screen.drawTexturedModalRect(x, y, 0, 65, getWidth(), getHeight());

        //Draw Recipe
        GuiHelper.drawItemStack(screen, this.input, x + 24, y + 24);
        GuiHelper.drawItemStack(screen, getOutput(), x + 100, y + 24);

    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public int getWidth() {
        return 126;
    }

    @Override
    public int getHeight() {
        return maxHeight;
    }

    @Override
    public String getHeading() {
        return "Smelting";
    }

}
