package shit.randomfoodstuff.guide;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import shit.randomfoodstuff.client.gui.Buttons;
import shit.randomfoodstuff.client.gui.Buttons.GuideMenuButton;
import shit.randomfoodstuff.client.gui.GuiGuide;

@SideOnly(Side.CLIENT)
public abstract class GuideMenu {

	public static final int defaultWidth = 153;
	public static final int defaultHeight = 155;
	
	private ArrayList<GuiButton> buttons = new ArrayList<GuiButton>();
	protected String heading;
	protected String identifier = null;
	protected GuiGuide parent;
	
	public int x = 79;
	public int y = 14 + GuideFormatter.FONT_HEIGHT;
	public int width = defaultWidth;
	public int heigth = defaultHeight;
	protected int lineSpacing = 5;
	
	public abstract void init();
	
	public boolean addMenuButton(String token, String caption, boolean isArticleButton) {
		return addMenuButton(new GuideMenuButton(parent.getUniqueButtonID(), this.x + parent.getXOffset(), y + parent.getYOffset() + getInternalNextButtonY(), token, caption, isArticleButton));
	}
	
	public boolean addMenuButton(String token, String caption, boolean isArticleButton, ResourceLocation texture, int textureX, int textureY, int width, int height) {
		return addMenuButton(new GuideMenuButton(parent.getUniqueButtonID(), this.x + parent.getXOffset(), y + parent.getYOffset() + getInternalNextButtonY(), token, caption, isArticleButton, texture, textureX, textureY, width, height));
	}
	
	public boolean addMenuButton(GuideMenuButton button) {
		if (heigth < getInternalNextButtonY() + button.height) {
			System.out.println("Could not fit button " + button.getToken() + " onto menu " + this.identifier + "(MaxY: " + this.heigth + ",buttonY: " + this.heigth + ")");
		}
		buttons.add(button);
		return true;
	}
	
	protected int getInternalNextButtonY() {
		int y = 0;
		for (GuiButton button : buttons) {
			if (button instanceof GuideMenuButton) {
				y += button.height + lineSpacing;
			}
		}
		return y;
	}
	
	public void open(GuiGuide parent) {
		buttons.clear();
		this.parent = parent;
		init();
	}
	
	public void close() {
		buttons.clear();
		this.parent = null;
	}
	
	public void actionPerformed(GuiButton parButton) {
		if (parButton instanceof Buttons.GuideMenuButton) {
			Buttons.GuideMenuButton button = (Buttons.GuideMenuButton) parButton;
			if (button.isArticleButton()) {
				GuideArticle article = GuideRegistry.getArticleByName(button.getToken());
				if (article != null) {
					parent.openArticle(article);
				} else {
					System.out.println("No such Article: " + button.getToken());
				}
			} else {
				GuideMenu menu = GuideRegistry.getMenuByName(button.getToken());
				if (menu != null) {
					parent.openMenu(menu);
				} else {
					System.out.println("No such Menu: " + button.getToken());
				}
			}
		}
	}
	
	//Setters
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	//Getters
	public String getHeading() {
		return StatCollector.translateToLocal("guide.menu." + identifier + ".heading");
	}
	
	public ArrayList<GuiButton> getButtons() {
		return buttons;
	}
	
}
