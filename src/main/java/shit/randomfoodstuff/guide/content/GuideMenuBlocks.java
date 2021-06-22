package shit.randomfoodstuff.guide.content;

import shit.randomfoodstuff.client.gui.Buttons;
import shit.randomfoodstuff.guide.GuideMenu;

public class GuideMenuBlocks extends GuideMenu {

    @Override
    public void init() {
        addMenuButton("blocks.fatInfuser", "Fat Infuser", 10, 7, true, Buttons.defaulttexture, 0, 62, 149, 23);
        addMenuButton("blocks.awfull", "Awfull looking Block", 10, 7, true, Buttons.defaulttexture, 0, 62, 149, 23);
    }

}
