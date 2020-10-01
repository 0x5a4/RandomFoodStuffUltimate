package shit.randomfoodstuff.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.tileentity.container.ContainerSchnitzelBackpack;
import shit.randomfoodstuff.tileentity.container.InventorySchnitzelBackpack;

public class GuiSchnitzelBackpack extends GuiContainer {

	private static final ResourceLocation bground = new ResourceLocation(Reference.GuiResourceLocation + "GuiSchnitzelBackpack.png");
	
	public GuiSchnitzelBackpack(EntityPlayer player, InventorySchnitzelBackpack inventory) {
		super(new ContainerSchnitzelBackpack(player, inventory));
		
		this.xSize = 176;
		this.ySize = 130;
		
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		this.mc.getTextureManager().bindTexture(bground);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
