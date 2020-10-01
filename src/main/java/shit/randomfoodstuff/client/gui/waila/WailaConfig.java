package shit.randomfoodstuff.client.gui.waila;

import mcp.mobius.waila.api.IWailaRegistrar;
import shit.randomfoodstuff.block.BlockAwfull;
import shit.randomfoodstuff.block.BlockCookingPot;
import shit.randomfoodstuff.block.BlockFatInfuser;

public class WailaConfig {

	public static void register(IWailaRegistrar registrar) {
		registrar.registerStackProvider(new WailaALBHandler(), BlockAwfull.class);
		registrar.registerHeadProvider(new WailaCookingPotHandler(), BlockCookingPot.class);
		registrar.registerHeadProvider(new WailaFatInfuserHandler(), BlockFatInfuser.class);
	}
	
}
