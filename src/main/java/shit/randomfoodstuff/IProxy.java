package shit.randomfoodstuff;

import net.minecraft.util.IChatComponent;

public interface IProxy {

	void registerModelCollections() throws IllegalArgumentException, IllegalAccessException;

	void registerRenderers() throws IllegalArgumentException, IllegalAccessException;

	void registerEvents();

	void registerGuide();

	void openGuideGui();

	void fakeDisconnect(String msg, IChatComponent reason);
	
	void checkSide();
	
}
