package shit.randomfoodstuff.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import shit.randomfoodstuff.Reference;

public class RandomModelRenderer extends TileEntitySpecialRenderer {

    private ResourceLocation texture;

    private RandomModelCollection modelCollection;

    public RandomModelRenderer(RandomModelCollection modelCollection) {
        this.modelCollection = modelCollection;
    }

    public RandomModelRenderer setDefaultTextureMap(String texturename) {
        this.texture = new ResourceLocation(Reference.TextureName + "textures/models/" + texturename);
        return this;
    }

    @Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
        RandomModel model = this.modelCollection.getModelBasedOn(entity, (int) x, (int) y, (int) z);
        ResourceLocation texture = null;

        if (model.hasCustomTexture()) {
            texture = model.texture;
        } else {
            if (this.texture != null) {
                texture = this.texture;
            } else {
                System.err.println("No Texture found for Model");
            }
        }

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180, 0F, 0F, 1F);

        this.bindTexture(texture);

        GL11.glPushMatrix();
        model.renderModel(0.0625F);
        GL11.glPopMatrix();

        GL11.glPopMatrix();

    }
}
