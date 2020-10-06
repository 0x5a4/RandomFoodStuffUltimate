package shit.randomfoodstuff.guide.content;

import shit.randomfoodstuff.client.gui.Buttons;
import shit.randomfoodstuff.guide.GuideMenu;

public class GuideMenuCooking extends GuideMenu {

	@Override
	public void init() {
		addMenuButton("cooking.cookingPot", "Cooking Pot", 10, 7, true, Buttons.defaulttexture, 0, 62, 149, 23);
		addMenuButton("cooking.spices", "Spices", 10, 7, true, Buttons.defaulttexture, 0, 62, 149, 23);
		addMenuButton("cooking.soupLadle", "Soup Ladle", 10, 7, true, Buttons.defaulttexture, 0, 62, 149, 23);
	}

}
