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

public class BlockLauch extends RandomCrops {

    private static final String texturename = "BlockLauch_stage_";

    public BlockLauch() {
        setBlockName("blockLauch");
    }

    @Override
    public int quantityDropped(int parMetadata, int parFortune, Random parRand) {
        if (parMetadata == 7) {
            return parRand.nextInt(2) + 2;
        } else {
            return 1;
        }
    }

    @Override
    public Item getItemDropped(int parMetadata, Random parRand, int parFortune) {
        return RandomItems.itemLauch;
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
