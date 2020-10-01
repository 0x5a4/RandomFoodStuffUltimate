package shit.randomfoodstuff.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.guide.GuideFormatter;
import shit.randomfoodstuff.util.ColorHelper;

public class Buttons extends GuiButton{
	
	protected static final ResourceLocation texture = new ResourceLocation(Reference.GuiResourceLocation + "GuiButtons.png");

	public Buttons(int id, int x, int y) {
		super(id, x, y, "");	
	}

	public static class GuideMenuButton extends Buttons {

		private String token;
		private String caption;
		private boolean isArticleButton;
		
		public GuideMenuButton(int id, int x, int y, String token, String caption, boolean isArticleButton) {
			super(id, x, y);
			
			this.token = token;
			this.caption = caption;
			this.isArticleButton = isArticleButton;
			this.height = GuideFormatter.FONT_HEIGHT;
			
		}
		
		@Override
		public void drawButton(Minecraft mc, int x, int y) {
			if (visible) {
				FontRenderer fontRenderer = mc.fontRenderer;
				fontRenderer.drawString(ColorHelper.getColorByName("underline") + caption, this.xPosition, this.yPosition, 4210752);
			}
		}
		
		public String getToken() {
			return token;
		}
		
		public boolean isArticleButton() {
			return isArticleButton;
		}
	}
	
	public static class ButtonPageSwitch extends Buttons {

		private boolean direction = true;
		
		public ButtonPageSwitch(int id, int x, int y, boolean direction) {
			super(id, x, y);
			
			this.direction = direction;
			this.width = 18;
			this.height = 10;
		}
		
		@Override
		public void drawButton(Minecraft mc, int x, int y) {
			if (visible) {
				boolean isHovered = (x >= xPosition && y >= yPosition && x < xPosition + width && y < yPosition + height);
				int textureX = 0;
				int textureY = 0;
				
				if (!direction) {
					textureY += 11;
				}
				
				if (isHovered) {
					textureX += 20;
				}
				
				GL11.glColor4f(1, 1, 1, 1);
				mc.getTextureManager().bindTexture(texture);
				drawTexturedModalRect(xPosition, yPosition, textureX, textureY, 18, 10);
			}
		}
	}
	
	public static class ButtonHome extends Buttons {

		
		public ButtonHome(int id, int x, int y) {
			super(id, x, y);
			
			this.width = 8;
			this.height = 8;
			
		}
		
		@Override
		public void drawButton(Minecraft mc, int x, int y) {
			if (visible) {
				boolean isHovered = (x >= xPosition && y >= yPosition && x < xPosition + width && y < yPosition + height);
				GL11.glColor4f(1, 1, 1, 1);
				mc.getTextureManager().bindTexture(texture);
				int textureX = 0;
				
				if (isHovered) {
					textureX += 9;
				}
				
				drawTexturedModalRect(xPosition, yPosition, textureX, 21, 8, 8);
			}
		}
		
	}
	
	public static class ButtonCrafting extends Buttons {
		
		
		public ButtonCrafting(int id, int x, int y) {
			super(id, x, y);
			
			this.width = 10;
			this.height = 10;	
		}
		
		@Override
		public void drawButton(Minecraft mc, int x, int y) {
			if (visible) {
				GL11.glColor4f(1, 1, 1, 1);
				mc.getTextureManager().bindTexture(texture);
				drawTexturedModalRect(xPosition, yPosition, 0, 29, 10, 10);
			}
		}
		
	}
}