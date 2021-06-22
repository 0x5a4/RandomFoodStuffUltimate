package shit.randomfoodstuff.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import shit.randomfoodstuff.Reference;

import java.util.Random;

public class BlockRegrowable extends BlockBush implements IGrowable {

    protected int maxGrowthStage;
    protected int regrowthTime;
    protected Item firstItem;
    protected int firstItemQuantity = 1;
    protected Item secondItem;
    protected int secondItemQuantity = 1;
    protected Item byProduct;
    protected int byProductQuantity = 1;

    @SideOnly(Side.CLIENT)
    protected IIcon[] icons;

    public BlockRegrowable(Item firstItem, Item secondItem, int maxGrowthTime, int regrowthTime) {
        setTickRandomly(true);
        setBlockBounds(0, 0, 0, 1F, 0.25F, 1F);
        setHardness(0);
        disableStats();
        setStepSound(soundTypeGrass);
        setFirstItem(firstItem);
        setSecondItem(secondItem);
        setRegrowthTime(regrowthTime);
        setMaxGrowthStage(maxGrowthTime);
    }

    @Override
    protected boolean canPlaceBlockOn(Block block) {
        return block == Blocks.farmland;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        super.updateTick(world, x, y, z, rand);
        int growthStage = world.getBlockMetadata(x, y, z);

        if (growthStage != maxGrowthStage && growthStage != maxGrowthStage + regrowthTime) {
            world.setBlockMetadataWithNotify(x, y, z, growthStage + 1, 2);
        }

    }

    public void incrementGrowthStage(World world, Random random, int x, int y, int z) {
        int growthStage = world.getBlockMetadata(x, y, z);
        int i = MathHelper.getRandomIntegerInRange(random, 2, 5);

        if (growthStage < maxGrowthStage) {
            if (growthStage + i > maxGrowthStage) {
                world.setBlockMetadataWithNotify(x, y, z, maxGrowthStage, 2);
            } else {
                world.setBlockMetadataWithNotify(x, y, z, growthStage + i, 2);
            }
        } else if (growthStage > maxGrowthStage) {
            if (growthStage + i > maxGrowthStage + regrowthTime) {
                world.setBlockMetadataWithNotify(x, y, z, maxGrowthStage + regrowthTime, 2);
            } else {
                world.setBlockMetadataWithNotify(x, y, z, growthStage + i, 2);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        boolean flag = false;
        if (world.getBlockMetadata(x, y, z) == 7) {
            world.setBlockMetadataWithNotify(x, y, z, this.maxGrowthStage + 1, 2);
            EntityItem entityitem = new EntityItem(world, x, y, z, new ItemStack(this.firstItem, this.firstItemQuantity));
            entityitem.motionY = 0.5D;
            world.spawnEntityInWorld(entityitem);
        } else if (world.getBlockMetadata(x, y, z) == maxGrowthStage + regrowthTime) {
            world.setBlockMetadataWithNotify(x, y, z, maxGrowthStage + 1, 2);
            EntityItem entityitem = new EntityItem(world, x, y, z, new ItemStack(this.secondItem, this.secondItemQuantity));
            entityitem.motionY = 0.5D;
            world.spawnEntityInWorld(entityitem);
        }
        return flag;
    }

    @Override
    public boolean func_149851_a(World world, int x, int y, int z, boolean parBoolean) {
        return world.getBlockMetadata(x, y, z) != maxGrowthStage + regrowthTime && world.getBlockMetadata(x, y, z) != maxGrowthStage;
    }

    @Override
    public boolean func_149852_a(World world, Random random, int x, int y, int z) {
        return true;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return icons[meta];
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        icons = new IIcon[maxGrowthStage + regrowthTime + 1];
        for (int i = 0; i < icons.length; i++) {
            icons[i] = p_149651_1_.registerIcon(Reference.TextureName + this.textureName + "_1");
        }
    }

    @Override
    public void func_149853_b(World world, Random random, int x, int y, int z) {
        this.incrementGrowthStage(world, random, x, y, z);
    }

    @Override
    public Item getItem(World world, int x, int y, int z) {
        return this.byProduct;
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int par2) {
        if (meta == maxGrowthStage) {
            return this.firstItem;
        } else if (meta == maxGrowthStage + regrowthTime) {
            return this.secondItem;
        }
        return this.byProduct;
    }

    public boolean isBlockFullyGrown(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z) >= maxGrowthStage;
    }

    public Block setMaxGrowthStage(int maxGrowthStage) {
        this.maxGrowthStage = maxGrowthStage;
        return this;
    }

    public Block setRegrowthTime(int par1) {
        this.regrowthTime = par1;
        return this;
    }

    public Block setFirstItem(Item item) {
        this.firstItem = item;
        return this;
    }

    public Block setFirstItemQuantity(int firstItemQuantity) {
        this.firstItemQuantity = firstItemQuantity;
        return this;
    }

    public Block setSecondItem(Item item) {
        this.secondItem = item;
        return this;
    }

    public Block setSecondItemQuantity(int secondItemQuantity) {
        this.secondItemQuantity = secondItemQuantity;
        return this;
    }

    public Block setByProduct(Item item) {
        this.byProduct = item;
        return this;
    }

    public Block setByProductQuantity(int byProductQuantity) {
        this.byProductQuantity = byProductQuantity;
        return this;
    }
}
