package shit.randomfoodstuff.event;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FatInfusingEvent extends Event{

	public World worldobj;
	public int x;
	public int y;
	public int z;
	
	public FatInfusingEvent(World world, int x, int y, int z) {
		this.worldobj = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Fired when the Infusing is finished
	 */
	public static class Post extends FatInfusingEvent {

		public ItemStack result;
		
		public Post(World world, int x, int y, int z, ItemStack result) {
			super(world, x, y, z);
			
		}
		
	}
	
	public static class ItemInfusedEvent extends Event{

		public ItemStack output;
		public EntityPlayer player;
		public World world;
		
		public ItemInfusedEvent(World world, ItemStack output, EntityPlayer player) {
			
			this.world = world;
			this.output = output;
			this.player = player;
			
		}
		
	}
	
}
