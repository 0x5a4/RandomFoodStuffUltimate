package shit.randomfoodstuff.guide;

import java.util.ArrayList;

import shit.randomfoodstuff.client.gui.Buttons.GuideMenuButton;

public class GuideMenuPage {

	protected ArrayList<GuideMenuButton> menuButtons = new ArrayList<GuideMenuButton>();
	
	public void addButton(GuideMenuButton button) {
		menuButtons.add(button);
	}
	
	public boolean canAddButton(int buttonHeight, int maxHeight, int lineSpacing) {
		if (maxHeight >= getInternalNextButtonY(lineSpacing) + buttonHeight) {
			return true;
		}
		return false;
	}
	
	public int getInternalNextButtonY(int lineSpacing) {
		int y = 0;
		for (GuideMenuButton button : menuButtons) {
			y += button.height + lineSpacing;
		}
		return y;
	}
	
	public ArrayList<GuideMenuButton> getMenuButtons() {
		return menuButtons;
	}
}
