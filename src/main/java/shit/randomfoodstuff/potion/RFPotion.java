package shit.randomfoodstuff.potion;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.client.config.ConfigHandler;

public class RFPotion extends Potion {

	public static final ResourceLocation iconPotion = new ResourceLocation(Reference.TextureName + "textures/gui/effects.png");
	
	public static Potion potionPepperoni;
	public static Potion potionMoldy;
	public static Potion potionCaffeine;
	public static Potion potionFlight;
	public static Potion potionLauch;
	
	public int iconIndexX;
	public int iconIndexY;
	
	public RFPotion(int id, boolean isBad) {
		super(id, isBad, 0);
	}
	
	@Override
	public Potion setIconIndex(int x, int y) {
		this.iconIndexX = x;
		this.iconIndexY = y;
		return (Potion) this;
	}
	
	@Override
	public boolean hasStatusIcon() {
		return false;
	}
	
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		Minecraft.getMinecraft().renderEngine.bindTexture(iconPotion);
		GL11.glEnable(GL11.GL_BLEND);
		mc.currentScreen.drawTexturedModalRect(x + 6, y + 7, 0 + 18 * iconIndexX, 198 + 18 * iconIndexY, 18, 18);
	}
	
	public static void initPotions() {
		potionPepperoni = new RFPotion(ConfigHandler.potionPepperoni, true).setIconIndex(0, 0).setPotionName("Spicy");
		potionMoldy = new RFPotion(ConfigHandler.potionMoldy, false).setIconIndex(1, 0).setPotionName("Moldy");
		potionCaffeine = new RFPotion(ConfigHandler.potionCaffeine, false).setIconIndex(2, 0).setPotionName("Awake");
		potionFlight = new RFPotion(ConfigHandler.potionFlight, false).setIconIndex(3, 0).setPotionName("Flight");
		potionLauch = new RFPotion(ConfigHandler.potionLauch, false).setIconIndex(4, 0).setPotionName("Lauchy");
	}

}
