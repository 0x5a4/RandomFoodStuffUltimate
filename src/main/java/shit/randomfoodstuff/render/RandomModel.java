package shit.randomfoodstuff.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import shit.randomfoodstuff.Reference;

import java.lang.reflect.Field;
import java.util.HashMap;

public class RandomModel extends ModelBase {

    public static final float degreeRadConvertRate = 0.0174533F;

    protected HashMap<String, ModelRenderer> partList = new HashMap<String, ModelRenderer>();
    public ResourceLocation texture;

    public RandomModel(ModelBase input) throws IllegalArgumentException, IllegalAccessException {
        for (Field f : input.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (f.getType() == ModelRenderer.class) {
                String name = f.getName();
                ModelRenderer value = (ModelRenderer) f.get(input);
                partList.put(name, value);
            }
        }
    }

    public RandomModel(HashMap<String, ModelRenderer> fields) {
        this.partList = fields;
    }

    @Override
    public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float f6) {
        super.render(entity, f1, f2, f3, f4, f5, f6);
        setRotationAngles(f1, f2, f3, f4, f5, f6, entity);
        for (ModelRenderer model : partList.values()) {
            model.render(f6);
        }
    }

    public void renderModel(float f) {
        for (ModelRenderer model : partList.values()) {
            model.render(f);
        }
    }

    public RandomModel attachTextureMap(String texturename) {
        this.texture = new ResourceLocation(Reference.TextureName + "textures/models/" + texturename);
        ;
        return this;
    }

    public boolean hasCustomTexture() {
        return this.texture != null;
    }

    public HashMap<String, ModelRenderer> getPartList() {
        return partList;
    }
}
