package shit.randomfoodstuff.client.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.client.gui.Buttons.ButtonCrafting;
import shit.randomfoodstuff.client.gui.Buttons.ButtonHome;
import shit.randomfoodstuff.client.gui.Buttons.ButtonPageSwitch;
import shit.randomfoodstuff.guide.GuideArticle;
import shit.randomfoodstuff.guide.GuideMenu;
import shit.randomfoodstuff.guide.GuideRegistry;

@SideOnly(Side.CLIENT)
public class GuiGuide extends GuiScreen {

	private static final ResourceLocation bground = new ResourceLocation(Reference.GuiResourceLocation + "GuiGuide.png");
	private Buttons.ButtonPageSwitch buttonNext;
	private Buttons.ButtonPageSwitch buttonPrev;
	private Buttons.ButtonHome buttonMenu;
	private Buttons.ButtonCrafting buttonCrafting;
	private int textureWidth = 254;
	private int textureHeight = 191;
	private GuideArticle article = null;
	private GuideMenu menu = null;
	private int nextButtonID = 0;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void initGui() {
		buttonList.clear();
		this.nextButtonID = 0;
		Keyboard.enableRepeatEvents(true);
		
		if (GuideRegistry.doesMenuExist(GuideRegistry.getDefaultMenu())) {
			openMenu(GuideRegistry.getMenuByName(GuideRegistry.getDefaultMenu()));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateScreen() {
		try {
			if (article != null) {
				article.update();
				if (article.hasRecipePage()) {
					buttonCrafting.visible = true;
				} else {
					buttonCrafting.visible = false;
				}
				buttonNext.visible = article.hasNext();
				buttonPrev.visible = article.hasPrev();
			} else {
				buttonCrafting.visible = false;
				buttonNext.visible = false;
				buttonPrev.visible = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void drawScreen(int par1, int par2, float par3) {
		try {
			drawDefaultBackground();
			int xOffset = getXOffset();
			int yOffset = getYOffset();
			GL11.glColor4f(1, 1, 1, 1);
			mc.getTextureManager().bindTexture(bground);
			drawTexturedModalRect(xOffset, yOffset, 0, 0, textureWidth, textureHeight);
			
			//Draw the Background of the Crafting Button if needed
			if (article != null) {
				if (article.hasRecipePage()) {
					drawTexturedModalRect(xOffset + 236, yOffset + 3, 239, 194, 18, 15);
				}
			}
			super.drawScreen(par1, par2, par3);
			
			//Draw the current Entry
			if (article != null && menu == null) {
				article.drawToScreen(this);
			}
			
			//Draw the Menu
			if (menu != null && article == null) {
				drawCenteredOffsetString(menu.getHeading(), 154, 6, 4210752);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if (button == buttonNext) {
			article.next();
		} else if (button == buttonPrev) {
			article.prev();
		} else if (button == buttonMenu) {
			if (GuideRegistry.doesMenuExist(GuideRegistry.getDefaultMenu())) {
				this.openMenu(GuideRegistry.getMenuByName(GuideRegistry.getDefaultMenu()));
			}
		} else if (button == buttonCrafting){ 
			if (article.pageFlag) {
				article.openRecipePage();
			} else {
				article.openTextPage();
			}
		} else if (menu != null) {
			menu.actionPerformed(button);
		}
	}
	
	public void openArticle(GuideArticle article) {
		if (this.menu != null) {
			this.menu.close();
		}
		
		if (this.article != null) {
			article.close();
		}
		
		this.menu = null;
		this.article = article;
		this.article.pageFlag = true;
		this.article.getFormatter().processText(fontRendererObj);
		this.buttonList.clear();
		this.addButtons();
	}
	
	public void openMenu(GuideMenu menu) {
		if (this.article != null) {
			this.article.close();
		}
		
		if (this.menu != null) {
			this.menu.close();
		}
		
		this.article = null;
		this.menu = menu;
		menu.open(this);
		this.buttonList.clear();
		this.addButtons();
		this.buttonList.addAll(menu.getButtons());
	}
	
	public void addButtons() {
		nextButtonID = 0;
		buttonList.add(buttonNext = new Buttons.ButtonPageSwitch(getUniqueButtonID(), getXOffset() + 214,  getYOffset() + 174, true));
		buttonList.add(buttonPrev = new Buttons.ButtonPageSwitch(getUniqueButtonID(), getXOffset() + 79, getYOffset() + 174, false));
		buttonList.add(buttonMenu = new Buttons.ButtonHome(getUniqueButtonID(), getXOffset() + 62 , getYOffset() + 6));
		buttonList.add(buttonCrafting = new Buttons.ButtonCrafting(getUniqueButtonID(), getXOffset() + 239, getYOffset() + 4));
	}
	
	public void drawCenteredOffsetString(String s, int centerX, int centerY, int color) {
		int length = this.fontRendererObj.getStringWidth(s);
		this.fontRendererObj.drawString(s, getXOffset() + (centerX - length / 2), getYOffset() + centerY, color);
	}
	
	@Override
	public void onGuiClosed() {
		if (article != null) {
			article.close();
		}
		
		if (menu != null) {
			menu.close();
		}
		
		this.article = null;
		this.menu = null;
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	//Setters
	
	//Getters
	public int getUniqueButtonID() {
		int i = nextButtonID;
		nextButtonID++;
		return i;
	}
	
	public int getXOffset() {
		if (article != null) {
			if (article.hasRecipePage()) {
				return (this.width - textureWidth) / 2 - 32 - 18;
			}
		}
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
