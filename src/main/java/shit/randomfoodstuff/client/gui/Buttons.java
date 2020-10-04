package shit.randomfoodstuff.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.guide.GuideArticle;
import shit.randomfoodstuff.guide.GuideFormatter;
import shit.randomfoodstuff.guide.GuideMenu;
import shit.randomfoodstuff.util.ColorHelper;

public class Buttons extends GuiButton {

	public static final ResourceLocation defaulttexture = new ResourceLocation(Reference.GuiResourceLocation + "GuiButtons.png");

	public Buttons(int id, int x, int y) {
		super(id, x, y, "");
	}

	public static class GuideMenuButton extends Buttons {

		protected String token;
		protected String caption;
		protected boolean isArticleButton;
		protected ResourceLocation buttonTexture;
		protected int textureX;
		protected int textureY;
		protected int captionX = -1;
		protected int captionY = -1;

		/**
		 * @param id              The Button ID
		 * @param x               x
		 * @param y               y
		 * @param token           The token this button leads to
		 * @param caption         The caption this button should have. Can be null
		 * @param isArticleButton If this leads to an Article or a Menu
		 * @param texture         Optional Texture. Can be null
		 * @param textureX
		 * @param textureY
		 * @param width           Width of the button. Is also used for Texturesize
		 * @param height          Height of the button. Is also used for Texturesize
		 */
		public GuideMenuButton(int id, int x, int y, String token, String caption, int captionX, int captionY, boolean isArticleButton, ResourceLocation texture, int textureX, int textureY, int width, int height) {
			super(id, x, y);

			this.token = token;
			this.caption = caption;
			this.isArticleButton = isArticleButton;
			this.height = height;
			this.width = width;
			this.buttonTexture = texture;
			this.textureX = textureX;
			this.textureY = textureY;
			this.captionX = captionX;
			this.captionY = captionY;
		}

		public GuideMenuButton(int id, int x, int y, String token, String caption, boolean isArticleButton, ResourceLocation texture, int textureX, int textureY, int width, int height) {
			this(id, x, y, token, caption, -1, -1, isArticleButton, texture, textureX, textureY, width, height);
		}

		public GuideMenuButton(int id, int x, int y, String token, String caption, int captionX, int captionY, boolean isArticleButton, int width, int height) {
			this(id, x, y, token, caption, captionX, captionY, isArticleButton, null, 0, 0, width, height);
		}
		
		public GuideMenuButton(int id, int x, int y, String token, String caption, boolean isArticleButton) {
			this(id, x, y, token, caption, isArticleButton, null, 0, 0, GuideMenu.defaultWidth, GuideFormatter.FONT_HEIGHT);
		}

		@Override
		public void drawButton(Minecraft mc, int x, int y) {
			if (visible) {
				FontRenderer fontRenderer = mc.fontRenderer;
				if (buttonTexture != null) {
					if (caption != null) {
						mc.getTextureManager().bindTexture(buttonTexture);
						GL11.glColor4f(1, 1, 1, 1);
						GL11.glEnable(GL11.GL_BLEND);
			            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
						drawTexturedModalRect(this.xPosition, this.yPosition, this.textureX, this.textureY, this.width, this.height);
						if (captionX < 0 || captionY < 0) {
							int textX = this.xPosition + (this.width / 2 - fontRenderer.getStringWidth(caption) / 2);
							int textY = this.yPosition + (this.height / 2 - fontRenderer.FONT_HEIGHT / 2);
							fontRenderer.drawString(caption, textX, textY, 4210752);
						} else {
							fontRenderer.drawString(caption, captionX, captionY, 4210752);
						}
					}
				} else {
					if (captionX < 0 || captionY < 0) {
						fontRenderer.drawString(caption, this.xPosition, this.yPosition, 4210752);
					} else {
						fontRenderer.drawString(caption, captionX, captionY, 4210752);
					}
				}
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
				mc.getTextureManager().bindTexture(defaulttexture);
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
				mc.getTextureManager().bindTexture(defaulttexture);
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
				mc.getTextureManager().bindTexture(defaulttexture);
				drawTexturedModalRect(xPosition, yPosition, 0, 29, 10, 10);
			}
		}

	}
}