package shit.randomfoodstuff.client.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.guide.GuideRegistry;
import shit.randomfoodstuff.guide.GuideTab;

@SideOnly(Side.CLIENT)
public class GuiGuide extends GuiScreen {
	
	private static final ResourceLocation bground = new ResourceLocation(Reference.GuiResourceLocation + "GuiGuide.png");
	private Buttons.ButtonPageSwitch buttonNext;
	private Buttons.ButtonPageSwitch buttonPrev;
	private Buttons.ButtonHome buttonMenu;
	private int textureWidth = 254;
	private int textureHeight = 191;
	private int nextButtonID;
	//Current Tab
	private GuideTab tab;
	
	@Override
	public void initGui() {
		if (GuideRegistry.getDefaultTab() != null) {
			tab = GuideRegistry.getDefaultTab();
			tab.setActive(true);
		} else {
			System.err.printf("Default Guidetab %s doesnt exist\n", GuideRegistry.defaultGuideTab);
		}
		addDefaultButtons();
		nextButtonID = 0;
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
	}
	
	/**
	 * @param buttonList
	 * @param clear Whether to clear the guis buttonList(Avoid Duplication of Home Button, etc)
	 */
	public void addButtonsToList(List<GuiButton> buttonList, boolean clear) {
		if (clear) {
			this.resetButtons();
		}
		this.buttonList.addAll(buttonList);
	}
	
	public void addLabelsToList(List<GuiLabel> labelList, boolean clear) {
		if (clear) {
			this.resetButtons();
		}
		this.labelList.addAll(labelList);
	}
	
	public int getUniqueButtonID() {
		return nextButtonID++;
	}
	
	public void resetButtons() {
		this.buttonList.clear();
		this.nextButtonID = 0;
		this.addDefaultButtons();
	}
	
	public void resetLabels() {
		this.labelList.clear();
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
