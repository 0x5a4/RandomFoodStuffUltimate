package shit.randomfoodstuff.util;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import shit.randomfoodstuff.client.gui.GuiGuide;

public class GuiHelper {

	public static void drawItemStack(GuiGuide screen, ItemStack stack, int x, int y) {
		RenderHelper.disableStandardItemLighting();
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glTranslatef(0, 0, 32);
		FontRenderer fontRenderer = stack.getItem().getFontRenderer(stack) != null ? stack.getItem().getFontRenderer(stack) : screen.getFontRenderer();
		screen.getItemRenderer().renderItemAndEffectIntoGUI(fontRenderer, screen.mc.getTextureManager(), stack, x, y);
		screen.getItemRenderer().renderItemOverlayIntoGUI(fontRenderer, screen.mc.getTextureManager(), stack, x, y);
	}
}
