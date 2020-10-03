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
	protected ArrayList<GuideMenuPage> pages = new ArrayList<GuideMenuPage>();
	protected String heading;
	protected String identifier = null;
	protected GuiGuide parent;
	
	public int x = 79;
	public int y = 14 + GuideFormatter.FONT_HEIGHT;
	public int width = defaultWidth;
	public int heigth = defaultHeight;
	protected int lineSpacing = 5;
	protected int index = 0;
	
	public abstract void init();
	
	public void addMenuButton(String token, String caption, int captionX, int captionY, boolean isArticleButton, ResourceLocation texture, int textureX, int textureY, int width, int height) {
		GuideMenuPage page = getNextMenuPage(height);
		page.addButton(new GuideMenuButton(parent.getUniqueButtonID(), parent.getXOffset() + this.x, parent.getYOffset() + this.y + page.getInternalNextButtonY(lineSpacing), token, caption, captionX, captionY, isArticleButton, texture, textureX, textureY, width, height));
	}
	
	public void addMenuButton(String token, String caption, int captionX, int captionY, boolean isArticleButton, int width, int height) {
		addMenuButton(token, caption, captionX, captionY, isArticleButton, null, 0, 0, width, height);
	}
	
	public void addMenuButton(String token, String caption, boolean isArticleButton, ResourceLocation texture, int textureX, int textureY, int width, int height) {
		addMenuButton(token, caption, -1, -1, isArticleButton, texture, textureX, textureY, width, height);
	}
	
	public void addMenuButton(String token, String caption, boolean isArticleButton) {
		GuideMenuPage page = getNextMenuPage(GuideFormatter.FONT_HEIGHT);
		page.addButton(new GuideMenuButton(parent.getUniqueButtonID(), parent.getXOffset() + this.x, parent.getYOffset() + this.y + page.getInternalNextButtonY(lineSpacing), token, caption, isArticleButton));
	}
	
	public void addMenuButton(GuideMenuButton button) {
		getNextMenuPage(button.height).addButton(button);
	}
	
	public GuideMenuPage getNextMenuPage(int buttonHeight) {
		for (GuideMenuPage page : pages) {
			if (page.canAddButton(buttonHeight, this.heigth, this.lineSpacing)) {
				return page;
			}
		}
		
		GuideMenuPage page = new GuideMenuPage();
		pages.add(page);
		return page;
	}
	
	public void next() {
		if (hasNext()) {
			index++;
		}
	}
	
	public void prev() {
		if (hasPrev()) {
			index--;
		}
	}
	
	public GuideMenuPage getCurrentPage() {
		if (pages.size() == 0) return null;
		return pages.get(this.index);
	}
	
	public boolean hasNext() {
		return index < pages.size() - 1;
	}
	
	public boolean hasPrev() {
		return index > 0;
	}
	
	public void open(GuiGuide parent) {
		buttons.clear();
		this.parent = parent;
		init();
	}
	
	public void close() {
		buttons.clear();
		pages.clear();
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
