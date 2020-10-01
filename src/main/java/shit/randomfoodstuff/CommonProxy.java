package shit.randomfoodstuff;

import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;
import shit.randomfoodstuff.event.handler.ScytheHandler;

public class CommonProxy implements IProxy {
	
	@Override
	public void registerModelCollections() throws IllegalArgumentException, IllegalAccessException {};

	@Override
	public void registerRenderers() throws IllegalArgumentException, IllegalAccessException {}
	
	@Override
	public void registerEvents() {}
	
	@Override
	public void openGuideGui() {}
	
	@Override
	public void fakeDisconnect(String msg, IChatComponent reason) {};
	
	@Override
	public void registerGuide() {}

	@Override
	public void checkSide() {
		System.out.println("Server");
	}
}
