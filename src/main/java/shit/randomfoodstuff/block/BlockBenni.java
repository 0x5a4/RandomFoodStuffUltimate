package shit.randomfoodstuff.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.tileentity.TileEntityBlockBenni;
import shit.randomfoodstuff.util.RandomSpecialRenderedBlock;

public class BlockBenni extends RandomSpecialRenderedBlock {

    public BlockBenni() {
        super(Material.cloth);

        setBlockName("blockBenni");
        setStepSound(soundTypeCloth);
        setBlockBounds(0.3F, 0, 0.3F, 0.7F, 0.9F, 0.7F);
        setResistance(5F);
        setHardness(5F);
        setBlockTextureName(Reference.TextureName + "blockBenni");
        setCreativeTab(RFMain.cTab);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par1) {
        return new TileEntityBlockBenni();
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        world.setBlockMetadataWithNotify(x, y, z, 1, 2);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        int l = MathHelper.floor_double((double) (player.rotationYaw * 4.0f / 360.F) + 0.5D) & 3;

        if (l == 0) {
            world.setBlockMetadataWithNotify(x, y, z, 1, 2);
        }

        if (l == 1) {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }

        if (l == 2) {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }

        if (l == 3) {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
    }

}
