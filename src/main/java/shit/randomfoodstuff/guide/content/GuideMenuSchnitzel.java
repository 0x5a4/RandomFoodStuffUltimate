package shit.randomfoodstuff.guide.content;

import shit.randomfoodstuff.client.gui.Buttons;
import shit.randomfoodstuff.guide.GuideMenu;

public class GuideMenuSchnitzel extends GuideMenu {

    @Override
    public void init() {
        addMenuButton("items.schnitzel.schnitzel", "Schnitzel", 10, 7, true, Buttons.defaulttexture, 0, 62, 149, 23);
        addMenuButton("items.schnitzel.backpack", "Schnitzel Backpack", 10, 7, true, Buttons.defaulttexture, 0, 62, 149, 23);
    }

}
