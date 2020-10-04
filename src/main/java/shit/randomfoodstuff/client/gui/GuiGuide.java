package shit.randomfoodstuff.client.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.guide.GuidePage;

@SideOnly(Side.CLIENT)
public class GuiGuide extends GuiScreen {
	
	private static final ResourceLocation bground = new ResourceLocation(Reference.GuiResourceLocation + "GuiGuide.png");
	private Buttons.ButtonPageSwitch buttonNext;
	private Buttons.ButtonPageSwitch buttonPrev;
	private Buttons.ButtonHome buttonMenu;
	private Buttons.ButtonCrafting buttonCrafting;
	private int textureWidth = 254;
	private int textureHeight = 191;
	//Current Page
	
	
	@Override
	public void initGui() {
		
	}
	
	@Override
	public void updateScreen() {
		
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		try {
			drawDefaultBackground();
			int xOffset = getXOffset();
			int yOffset = getYOffset();
			GL11.glColor4f(1, 1, 1, 1);
			mc.getTextureManager().bindTexture(bground);
			drawTexturedModalRect(xOffset, yOffset, 0, 0, textureWidth, textureHeight);
			
			//Draw Tabs
			
			
			//Draw Buttons
			super.drawScreen(par1, par2, par3);
			
			//Draw Page
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		
	}
	
	public void addDefaultButtons() {
		buttonList.add(buttonNext = new Buttons.ButtonPageSwitch(getUniqueButtonID(), getXOffset() + 214,  getYOffset() + 174, true));
		buttonList.add(buttonPrev = new Buttons.ButtonPageSwitch(getUniqueButtonID(), getXOffset() + 79, getYOffset() + 174, false));
		buttonList.add(buttonMenu = new Buttons.ButtonHome(getUniqueButtonID(), getXOffset() + 62 , getYOffset() + 6));
		buttonList.add(buttonCrafting = new Buttons.ButtonCrafting(getUniqueButtonID(), getXOffset() + 239, getYOffset() + 4));
	}
	
	public void addButtonsToList(List<GuiButton> buttonList, boolean clear) {
		if (clear) {
			this.buttonList.clear();
			this.addDefaultButtons();
		}
		this.buttonList.addAll(buttonList);
	}
	
	public void drawCenteredOffsetString(String s, int centerX, int centerY, int color) {
		int length = this.fontRendererObj.getStringWidth(s);
		this.fontRendererObj.drawString(s, getXOffset() + (centerX - length / 2), getYOffset() + centerY, color);
	}
	
	@Override
	public void onGuiClosed() {
		
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	//Setters
	
	//Getters
	
	
	public int getXOffset() {
		return (this.width - textureWidth) / 2 - 32;
	}
	
	public int getYOffset() {
		return (this.height - textureHeight) / 2;
	}
	
	public FontRenderer getFontRenderer() {
		return this.fontRendererObj;
	}
	
	public static RenderItem getItemRenderer() {
		return itemRender;
	}
	
	
}
