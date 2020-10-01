package shit.randomfoodstuff.cooking;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public abstract class SoupEffect {
	
	/**
	 * Auto Registers the Effect
	 */
	public SoupEffect(String id) {
		SoupRegistry.registerSoupEffect(id, this);
	}
	
	public SoupEffect() {}
	
	/**
	 * Called when this effect is triggered. 
	 */
	public abstract void perform(World world, EntityPlayer player);
	
	public abstract String getDisplayName();
	
	public String getSelfReactionDisplayName() {
		return null;
	}
	
	/**
	 * Called when Spice and Reagent Match the Same Effect(this)
	 */
	public void reactWithSelf(World world, EntityPlayer player) {}
	
	/**
	 * Called when this effect is used as a Reagent.
	 * @return Return true when you want the spice not to perform its logic
	 */
	public boolean onReactedWith(String spice, String reagent, World world, EntityPlayer player) {
		return false;
	};
	
	public String getID() {
		return SoupRegistry.getEffectID(this);
	}
	
	/**
	 * Whether this is a Template Soup Effect. Dont know why youd need this but here you go
	 * @return
	 */
	public boolean isTemplate() {
		return this instanceof TemplateSoupEffect;
	}
	
}
