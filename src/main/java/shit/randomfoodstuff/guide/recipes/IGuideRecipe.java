package shit.randomfoodstuff.guide.recipes;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.client.gui.GuiGuide;

public interface IGuideRecipe {
	
	public static final ResourceLocation recipeTextures = new ResourceLocation(Reference.GuiResourceLocation + "GuiGuideRecipes.png");
	
	public static int maxWidth = 153;
	public static int maxHeight = 64;

	/**
	 * Actually not very necessary...
	 * However this is called when the Recipe page of an Article is opened
	 * Its up to you what to do then
	 */
	public void loadRecipe();
	public void drawToScreen(GuiGuide screen, int x, int y);
	
	public ItemStack getOutput();
	
	public int getWidth();
	public int getHeight();
	
	public String getHeading();
	
	
}
