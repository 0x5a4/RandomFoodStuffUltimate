package shit.randomfoodstuff;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.util.IChatComponent;

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
	public Side getSide() {
		return Side.SERVER;
	}
}
