package shit.randomfoodstuff.guide;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuideTextPage {

    private String[] lines;

    public GuideTextPage(int size) {
        this.lines = new String[size];
    }

    public int getSize() {
        return lines.length;
    }

    public String[] getLines() {
        return lines;
    }

    public boolean addLine(String s) {
        for (int i = lines.length - 1; i >= 0; i--) {
            if (lines[i] != null) {
                if (i == lines.length - 1) {
                    return false;
                }
                lines[i + 1] = s;
                return true;
            } else if (i == 0) {
                lines[0] = s;
                return true;
            }
        }
        return false;
    }

    public void removeLine(int index) {
        lines[index] = null;
    }
}
