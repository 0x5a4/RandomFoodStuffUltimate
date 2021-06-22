package shit.randomfoodstuff.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;

import java.util.Iterator;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiFakeDisconnect extends GuiScreen {

    private String msg;
    private IChatComponent reason;
    private List field_146305_g;
    private static final String __OBFID = "CL_00000693";

    public GuiFakeDisconnect(String msg, IChatComponent reason) {
        this.msg = I18n.format(msg, new Object[0]);
        this.reason = reason;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.toMenu", new Object[0])));
        this.field_146305_g = this.fontRendererObj.listFormattedStringToWidth(this.reason.getFormattedText(), this.width - 50);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawBackground(0);
        this.drawCenteredString(this.fontRendererObj, this.msg, this.width / 2, this.height / 2 - 50, 11184810);
        int k = this.height / 2 - 30;

        if (this.field_146305_g != null) {
            for (Iterator iterator = this.field_146305_g.iterator(); iterator.hasNext(); k += this.fontRendererObj.FONT_HEIGHT) {
                String s = (String) iterator.next();
                this.drawCenteredString(this.fontRendererObj, s, this.width / 2, k, 16777215);
            }
        }

        super.drawScreen(par1, par2, par3);
    }
}
