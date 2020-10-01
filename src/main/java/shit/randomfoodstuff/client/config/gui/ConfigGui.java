package shit.randomfoodstuff.client.config.gui;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.client.config.ConfigHandler;

public class ConfigGui extends GuiConfig{

	public ConfigGui(GuiScreen parentScreen) {
		super(parentScreen, getConfigElements(parentScreen), Reference.ModID, false, false, Reference.Name + " Configuration");
		
	}
	
	
	private static List<IConfigElement> getConfigElements(GuiScreen parent) {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		
		list.add(new ConfigElement<ConfigCategory>(ConfigHandler.config.getCategory("Potion ID's".toLowerCase())));
		list.add(new ConfigElement<ConfigCategory>(ConfigHandler.config.getCategory("Items".toLowerCase())));
		
		return list;
	}

}
