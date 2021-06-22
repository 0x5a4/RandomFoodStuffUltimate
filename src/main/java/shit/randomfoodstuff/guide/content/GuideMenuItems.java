package shit.randomfoodstuff.guide.content;

import shit.randomfoodstuff.client.gui.Buttons;
import shit.randomfoodstuff.guide.GuideMenu;

public class GuideMenuItems extends GuideMenu {

    @Override
    public void init() {
        addMenuButton("items.lauch", "Lauch", 10, 7, true, Buttons.defaulttexture, 0, 62, 149, 23);
        addMenuButton("items.drinks", "Drinks", 10, 7, true, Buttons.defaulttexture, 0, 62, 149, 23);
        addMenuButton("items.baguette", "Baguette Magique", 10, 7, true, Buttons.defaulttexture, 0, 62, 149, 23);
        addMenuButton("items.schnitzel", "Schnitzels", 10, 7, false, Buttons.defaulttexture, 0, 39, 149, 23);
        addMenuButton("items.arthur", "Arthur", 10, 7, true, Buttons.defaulttexture, 0, 62, 149, 23);
        addMenuButton("items.benni", "Benni", 10, 7, true, Buttons.defaulttexture, 0, 62, 149, 23);
    }

}
