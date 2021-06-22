package shit.randomfoodstuff.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.tileentity.TileEntityFatInfuser;
import shit.randomfoodstuff.tileentity.container.ContainerFatInfuser;

public class GuiFatInfuser extends GuiContainer {

    private static final ResourceLocation gui = new ResourceLocation(Reference.GuiResourceLocation + "GuiFatInfuser.png");

    private TileEntityFatInfuser fatInfuser;

    public GuiFatInfuser(InventoryPlayer inventory, TileEntityFatInfuser entity) {
        super(new ContainerFatInfuser(inventory, entity));

        this.xSize = 176;
        this.ySize = 166;
        this.fatInfuser = entity;
    }

    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        String s = "Fat Infuser";
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1f, 1F, 1F, 1F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(gui);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if (this.fatInfuser.isBurning()) {
            int k = this.fatInfuser.getBurnTimeRemainingScaled(12);
            int j = 12 - k;
            drawTexturedModalRect(guiLeft + 101, guiTop + 61, 179, 37, 13 - j, 12);
        }

        int k = this.fatInfuser.getCookProgressScaled(73);
        drawTexturedModalRect(guiLeft + 54, guiTop + 19, 176, 0, k + 1, 36);
    }


}
