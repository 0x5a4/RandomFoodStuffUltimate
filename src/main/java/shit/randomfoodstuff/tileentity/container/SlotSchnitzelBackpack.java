package shit.randomfoodstuff.tileentity.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.item.ISchnitzelBackpackable;

public class SlotSchnitzelBackpack extends Slot {
	
	private static final ResourceLocation bground = new ResourceLocation(Reference.TextureName + "textures/gui/BackgroundSchnitzel.png");

	private final EntityPlayer player;
	private ContainerSchnitzelBackpack container;
	
	public SlotSchnitzelBackpack(ContainerSchnitzelBackpack container, EntityPlayer player, IInventory inventory, int slotIndex, int x, int y) {
		super(inventory, slotIndex, x, y);
		
		this.container = container;
		this.player = player;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() instanceof ISchnitzelBackpackable;
	}
	
	@Override
	public ResourceLocation getBackgroundIconTexture() {
		return bground;
	}
	
	
	
	
	

}
