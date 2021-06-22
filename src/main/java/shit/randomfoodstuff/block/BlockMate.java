package shit.randomfoodstuff.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import shit.randomfoodstuff.RandomItems;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.util.RandomCrops;

import java.util.Random;

public class BlockMate extends RandomCrops {

    private static final String texturename = "BlockMate_";

    public BlockMate() {
        super();

        setBlockName("blockMate");
    }

    @Override
    public int quantityDropped(int parMetadata, int parFortune, Random parRand) {
        if (parMetadata == 7) {
            return parRand.nextInt(3) + 2;
        } else {
            return 1;
        }
    }

    @Override
    public Item getItemDropped(int parMetadata, Random parRand, int parFortune) {
        return RandomItems.itemMateTee;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister parIIconRegister) {
        iIcon = new IIcon[maxGrowthStage + 1];
        for (int i = 0; i <= 7; i++) {
            iIcon[i] = parIIconRegister.registerIcon(Reference.TextureName + texturename + i);
        }
    }

}
