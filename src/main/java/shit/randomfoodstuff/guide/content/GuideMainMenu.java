package shit.randomfoodstuff.guide.content;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import shit.randomfoodstuff.client.gui.Buttons;
import shit.randomfoodstuff.guide.GuideMenu;

@SideOnly(Side.CLIENT)
public class GuideMainMenu extends GuideMenu {

	@Override
	public void init() {
		addMenuButton("cooking", "Cooking", 10, 7, false, Buttons.defaulttexture, 0, 39, 149, 23);
		addMenuButton("items", "Items", 10, 7, false, Buttons.defaulttexture, 0, 39, 149, 23);
		addMenuButton("blocks", "Blocks", 10, 7, false, Buttons.defaulttexture, 0, 39, 149, 23);
	}

}
