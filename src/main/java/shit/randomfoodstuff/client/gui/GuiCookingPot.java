package shit.randomfoodstuff.client.gui;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.cooking.SoupRegistry;
import shit.randomfoodstuff.tileentity.TileEntityCookingPot;
import shit.randomfoodstuff.tileentity.container.ContainerCookingPot;

public class GuiCookingPot extends GuiContainer {

	private static final ResourceLocation bground = new ResourceLocation(Reference.GuiResourceLocation + "GuiCookingPot.png");
	private TileEntityCookingPot entity;
	
	public GuiCookingPot(TileEntityCookingPot entity) {
		super(new ContainerCookingPot(entity));
		this.entity = entity;
		
		this.xSize = 204;
		this.ySize = 100;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		//the parameters for drawString are: string, x, y, color
		int color = 4210752;
		this.fontRendererObj.drawString("Ingredients", 5, 5, color);
		this.fontRendererObj.drawString("Spices", 5, 40, color);
		this.fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + "Soup Information", 114, 5, color);
		if (entity.containsItems() && entity.isBlockOnFire()) {
			this.fontRendererObj.drawString("Food Level: " + entity.calcHealAmount() / 2, 114, 20, color);
			this.fontRendererObj.drawString(getSaturationString(entity.calcSaturation() / 2), 114, 30, color);
			if (entity.containsSpice()) {
				this.fontRendererObj.drawString("Spice:", 114, 40, color);
				if (entity.getReagent() != null) {
					if (SoupRegistry.getEffectFromStack(entity.getSpice()).equals(SoupRegistry.getEffectFromStack(entity.getReagent()))) {
						this.fontRendererObj.drawString(SoupRegistry.getEffectDisplayName(entity.getSpice()) + " x2", 125, 50, color);
					} else {
						this.fontRendererObj.drawString(SoupRegistry.getEffectDisplayName(entity.getSpice()), 125, 50, color);
						this.fontRendererObj.drawString(SoupRegistry.getEffectDisplayName(entity.getReagent()), 125, 60, color);
					}
					if (SoupRegistry.canReact(entity.getSpice(), entity.getReagent())) {				
						this.fontRendererObj.drawString("= " + SoupRegistry.getEffectDisplayName(SoupRegistry.getReaction(entity.getSpice(), entity.getReagent()).getResultEffect()), 114, 70, color);
					} else if (SoupRegistry.getEffectFromStack(entity.getSpice()).equals(SoupRegistry.getEffectFromStack(entity.getReagent()))) {
						String displayName = SoupRegistry.getEffect(SoupRegistry.getEffectFromStack(entity.getSpice())).getSelfReactionDisplayName();
						if (displayName != null) {
							this.fontRendererObj.drawString("= " + displayName, 114, 70, color);
						}
					}
				} else {
					this.fontRendererObj.drawString(SoupRegistry.getEffectDisplayName(entity.getSpice()), 125, 50, color);
				}
			}
		} else {
			this.fontRendererObj.drawString("No Soup :(", 130, 40, color);
			if (!entity.isBlockOnFire()) {
				this.fontRendererObj.drawString("Place a fire", 124, 50, color);
				this.fontRendererObj.drawString("underneath!", 124, 60, color);
			} else {
				this.fontRendererObj.drawString("Insert Items", 124, 50, color);
				this.fontRendererObj.drawString("via Rightclick", 124, 60, color);
			}
		}
	}
	
	protected static String getSaturationString(float saturation) {
		String s = "Saturation: ";
		boolean flag = false;
		for (char c : String.valueOf(saturation).toCharArray()) {
			if (!flag) {
				s += c;
			} else {
				s += c;
				break;
			}
			
			if (c == '.') flag = true;
		}
		return s;
	}
	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		this.mc.getTextureManager().bindTexture(bground);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (entity.containsItems()) {
			int k = 12 - entity.getPrimarySoupScaled(12);
			int j = 12 - entity.getSecondarySoupScaled(12);
			drawTexturedModalRect(guiLeft + 73, guiTop + 55, 0, 100, 12 - k, 11);
			drawTexturedModalRect(guiLeft + 89, guiTop + 55, 0, 100, 12 - j, 11);
		}
	}

}
