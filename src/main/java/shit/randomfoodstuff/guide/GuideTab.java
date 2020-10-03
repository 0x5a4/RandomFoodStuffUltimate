package shit.randomfoodstuff.guide;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import shit.randomfoodstuff.client.gui.GuiGuide;

public abstract class GuideTab {

	protected HashMap<String, Class<? extends GuidePage>> pageList = new HashMap<String, Class<? extends GuidePage>>();
	
	private TabIcon icon;
	private TabIcon selectIcon;
	private String name;
	
	public abstract void open(GuiGuide parent);
	
	public void registerPage(String name, Class<? extends GuidePage> pageClass) {
		if (pageList.containsKey(name)) {
			System.out.printf("Page %s already exists in tab %s", name, this.name);
			return;
		}
		System.out.printf("Registering Page %s under name %s", pageClass.getName(), name);
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
				page.load();
				return page;
			} catch (Exception e) {
				System.err.printf("Error while instantiating %s");
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
