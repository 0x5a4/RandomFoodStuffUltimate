package shit.randomfoodstuff.util;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

public abstract class RandomSpecialRenderedBlock extends BlockContainer {

    protected RandomSpecialRenderedBlock(Material material) {
        super(material);
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

}
