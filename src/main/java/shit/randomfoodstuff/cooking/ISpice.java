package shit.randomfoodstuff.cooking;

import net.minecraft.item.ItemStack;

public interface ISpice {
	
	/**
	 * @param stack
	 * @return The default Effect Name of this stack (e.g. pepperoni). Return null if you dont want specific sub items to be spices
	 */
	public String getEffectName(ItemStack stack);
}
