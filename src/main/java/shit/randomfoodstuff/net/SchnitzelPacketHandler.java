package shit.randomfoodstuff.net;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;

public class SchnitzelPacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("SchnitzelBackpack");

	public static void init() {
		INSTANCE.registerMessage(SchnitzelPacketProcessor.class, SchnitzelPacketProcessor.class, 0, Side.SERVER);
	}
}
