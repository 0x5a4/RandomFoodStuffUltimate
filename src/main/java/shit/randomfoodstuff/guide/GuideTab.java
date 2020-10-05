package shit.randomfoodstuff.guide;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import shit.randomfoodstuff.client.gui.GuiGuide;

public abstract class GuideTab {

	protected HashMap<String, Class<? extends GuidePage>> pageList = new HashMap<String, Class<? extends GuidePage>>();
	
	private TabIcon icon;
	private TabIcon selectIcon;
	private String name;
	private GuidePage currentPage;
	private boolean isActive = false;
	
	public abstract void open(GuiGuide parent);
	
	public void close() {
		if (currentPage != null) {
			currentPage.close();
		}
		this.setActive(false);
	}
	
	public void drawScreen(Minecraft mc, int par1, int par2, float par3) {
		if (isActive) {
			if (currentPage != null) {
				if (currentPage.parent != null) {
					currentPage.drawToScreen(mc, par1, par2, par3);
				}
			}
		}
	}
	
	public void actionPerformed(GuiButton button) {
		if (isActive) {
			if (currentPage != null) {
				if (currentPage.parent != null) {
					currentPage.actionPerformed(button);
				}
			}
		}
	}
	
	public void openPage(String name, GuiGuide parent) {
		GuidePage page = getPageInstance(name);
		if (page != null) {
			this.currentPage = page;
			page.parent = parent;
			page.open();
		}
	}
	
	public void registerPage(String name, Class<? extends GuidePage> pageClass) {
		if (pageList.containsKey(name)) {
			System.out.printf("Page %s already exists in tab %s\n", name, this.name);
			return;
		}
		System.out.printf("Registering Page %s under name %s\n", pageClass.getName(), name);
		pageList.put(name, pageClass);
	}
	
	public Class<? extends GuidePage> getPageClass(String name) {
		if (!pageList.containsKey(name)) {
			System.out.println("No such Page:" + name);
			return null;
		}
		return pageList.get(name);
	}
	
	public GuidePage getPageInstance(String name) {
		Class<? extends GuidePage> pageClass = getPageClass(name);
		if (pageClass != null) {
			try {
				Constructor<? extends GuidePage> constructor = pageClass.getConstructor();
				constructor.setAccessible(true);
				GuidePage page = constructor.newInstance();
				return page;
			} catch (Exception e) {
				System.err.printf("Error while instantiating %s\n", name);
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public GuideTab setName(String name) {
		this.name = name;
		return this;
	}
	
	public GuideTab setIcon(TabIcon icon) {
		this.icon = icon;
		return this;
	}
	
	public void setSelectIcon(TabIcon selectIcon) {
		this.selectIcon = selectIcon;
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public String getName() {
		return name;
	}
	
	public TabIcon getIcon() {
		return icon;
	}
	
	public TabIcon getSelectIcon() {
		return selectIcon;
	}

}
