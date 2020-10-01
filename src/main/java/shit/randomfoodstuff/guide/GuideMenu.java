package shit.randomfoodstuff.guide;

import java.util.ArrayList;
import java.util.Collection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;
import net.minecraft.world.biome.BiomeGenBase.Height;
import shit.randomfoodstuff.client.gui.Buttons;
import shit.randomfoodstuff.client.gui.GuiGuide;
import shit.randomfoodstuff.client.gui.Buttons.GuideMenuButton;

@SideOnly(Side.CLIENT)
public abstract class GuideMenu {

	private ArrayList<GuiButton> buttons = new ArrayList<GuiButton>();
	protected String heading;
	protected String identifier = null;
	protected GuiGuide parent;
	
	public int x = 79;
	public int y = 14 + GuideFormatter.FONT_HEIGHT;
	public int width = 153;
	public int heigth = 155;
	protected int lineSpacing = 5;
	
	public abstract void init();
	
	public boolean addMenuButton(String token, String caption, boolean isArticleButton) {
		int y = this.y + (GuideFormatter.FONT_HEIGHT + lineSpacing) * buttons.size() + parent.getYOffset();
		return addButton(new GuideMenuButton(parent.getUniqueButtonID(), this.x + parent.getXOffset(), y, token, caption, isArticleButton));
	}
	
	public boolean addButton(GuiButton button) {
		int lines = heigth / (GuideFormatter.FONT_HEIGHT + lineSpacing);
		
		if (buttons.size() < lines) {
			buttons.add(button);
			return true;
		} else {
			System.out.println("Cannot fit more buttons in Menu " + identifier);
			return false;
		}
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
