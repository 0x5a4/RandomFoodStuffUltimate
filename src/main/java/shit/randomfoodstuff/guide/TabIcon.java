package shit.randomfoodstuff.guide;

import net.minecraft.util.ResourceLocation;

public class TabIcon {

	private ResourceLocation texture;
	private int textureX;
	private int textureY;
	private int textureWidth;
	private int textureHeight;
	
	public TabIcon(ResourceLocation texture, int textureX, int textureY, int textureWidth, int textuerHeight) {
		this.texture = texture;
		this.textureX = textureX;
		this.textureY = textureY;
		this.textureWidth = textureWidth;
		this.textureHeight = textuerHeight;
	}
	
	public ResourceLocation getTexture() {
		return texture;
	}
	
	public int getTextureX() {
		return textureX;
	}
	
	public int getTextureY() {
		return textureY;
	}
	
	public int getTextureWidth() {
		return textureWidth;
	}
	
	public int getTextureHeight() {
		return textureHeight;
	}

}
