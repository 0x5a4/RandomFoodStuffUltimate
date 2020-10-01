package shit.randomfoodstuff.event;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.world.World;
import shit.randomfoodstuff.block.BlockAwfull;

@Cancelable
/**
 * Fired when the Awfull Looking Block tries to Immitate something
 * If canceled the Immitaion wont happen
 */
public class AwfullLookingBlockImmitationEvent extends Event{

	public World world;
	/**
	 * X Coord of the Block tried to Immitate
	 */
	public int x;
	/**
	 * Y Coord of the Block tried to Immitate
	 */
	public int y;
	/**
	 * Z Coord of the Block tried to Immitate
	 */
	public int z;
	/**
	 * The Block ID attempted to Fake. Can be changed to immitate another Block. 
	 * Not all Blocks can be immitated though, so {@link BlockAwfull#canImmitate(net.minecraft.block.Block)} should be used 
	 * to check if Immitation is possible. This check has allready been run for the original value
	 */
	public int blockID;
	/**
	 * The Metadata the Faked Block will have. Can also be changed
	 */
	public int blockMeta;
	
	public AwfullLookingBlockImmitationEvent(World world, int x, int y, int z, int blockID, int blockMeta) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.blockID = blockID;
		this.blockMeta = blockMeta;
	}
}
