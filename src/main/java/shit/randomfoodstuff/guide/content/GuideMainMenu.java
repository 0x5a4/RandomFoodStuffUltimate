package shit.randomfoodstuff.guide.content;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import shit.randomfoodstuff.guide.GuideMenu;

@SideOnly(Side.CLIENT)
public class GuideMainMenu extends GuideMenu {

	@Override
	public void init() {
		addMenuButton("soupLadle", "Soup Ladle", true);
		addMenuButton("baguette", "Baguette Magique", true);
		addMenuButton("fatInfuser", "Fat Infuser", true);
		addMenuButton("awfulBlock", "Awfull looking Block", true);
		addMenuButton("drinks", "Drinks", true);
		addMenuButton("backpack", "Schnitzel Backpack", true);
	}

}