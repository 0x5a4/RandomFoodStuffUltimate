package shit.randomfoodstuff.guide;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import shit.randomfoodstuff.client.gui.GuiGuide;

public abstract class GuidePage extends Gui {

	public GuiGuide parent;
	protected ArrayList<GuiButton> buttonList = new ArrayList<GuiButton>();
	
	//Use load()
	private GuidePage() {};
	
	public abstract void load();
	public abstract void update();
	public abstract void drawToScreen(Minecraft mc, int par1, int par2, int par3);
	
	public void actionPerformed(GuiButton button) {}
	public void open() {}
	public void close() {}
	
	public ArrayList<GuiButton> getButtonList() {
		return buttonList;
	}
}
