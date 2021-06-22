package shit.randomfoodstuff.world;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

public class FakeBlockAccess implements IBlockAccess {

    protected IBlockAccess parent;
    protected Block fakeBlock;
    protected int fakeMeta;
    protected int x;
    protected int y;
    protected int z;

    public FakeBlockAccess(IBlockAccess parent, Block fakeBlock, int fakeMeta, int x, int y, int z) {
        this.parent = parent;
        this.fakeBlock = fakeBlock;
        this.fakeMeta = fakeMeta;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        if (x == this.x && y == this.y && z == this.z) {
            return this.fakeBlock;
        } else {
            return parent.getBlock(x, y, z);
        }
    }

    @Override
    public int getBlockMetadata(int x, int y, int z) {
        if (x == this.x && y == this.y && z == this.z) {
            return this.fakeMeta;
        } else {
            return parent.getBlockMetadata(x, y, z);
        }
    }

    @Override
    public TileEntity getTileEntity(int p_147438_1_, int p_147438_2_, int p_147438_3_) {
        return parent.getTileEntity(p_147438_1_, p_147438_2_, p_147438_3_);
    }

    @Override
    public int getLightBrightnessForSkyBlocks(int p_72802_1_, int p_72802_2_, int p_72802_3_, int p_72802_4_) {
        return parent.getLightBrightnessForSkyBlocks(p_72802_1_, p_72802_2_, p_72802_3_, p_72802_4_);
    }

    @Override
    public int isBlockProvidingPowerTo(int p_72879_1_, int p_72879_2_, int p_72879_3_, int p_72879_4_) {
        return parent.isBlockProvidingPowerTo(p_72879_1_, p_72879_2_, p_72879_3_, p_72879_4_);
    }

    @Override
    public boolean isAirBlock(int p_147437_1_, int p_147437_2_, int p_147437_3_) {
        return parent.isAirBlock(p_147437_1_, p_147437_2_, p_147437_3_);
    }

    @Override
    public BiomeGenBase getBiomeGenForCoords(int p_72807_1_, int p_72807_2_) {
        return parent.getBiomeGenForCoords(p_72807_1_, p_72807_2_);
    }

    @Override
    public int getHeight() {
        return parent.getHeight();
    }

    @Override
    public boolean extendedLevelsInChunkCache() {
        return parent.extendedLevelsInChunkCache();
    }

    @Override
    public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default) {
        return parent.isSideSolid(x, y, z, side, _default);
    }

}
