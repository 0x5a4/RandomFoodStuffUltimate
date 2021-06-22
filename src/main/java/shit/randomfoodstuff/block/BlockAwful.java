package shit.randomfoodstuff.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.RandomBlocks;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.entity.EntityDamageSourceAwfull;
import shit.randomfoodstuff.item.ItemBlockAwfull;
import shit.randomfoodstuff.item.tool.IAwfullLookingBlockBreaker;
import shit.randomfoodstuff.tileentity.TileEntityBlockAwfull;
import shit.randomfoodstuff.util.ColorHelper;
import shit.randomfoodstuff.world.FakeBlockAccess;

public class BlockAwful extends BlockContainer {

    public BlockAwful() {
        super(Material.rock);

        setBlockName("blockAwfull");
        setBlockTextureName(Reference.TextureName + "blockAwfull");
        setResistance(1000F);
        setHardness(10);
        setHarvestLevel("pickaxe", 1);
        setCreativeTab(RFMain.cTab);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        RFMain.proxy.fakeDisconnect("Connection terminated", new ChatComponentText("java.lang.ArrayIndexOutOfBoundException: {size:5, index:2}"));
        return false;
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
        if (player.getCurrentEquippedItem() == null) {
            return;
        }

        if (player.getCurrentEquippedItem().getItem() instanceof IAwfullLookingBlockBreaker) {
            super.onBlockClicked(world, x, y, z, player);
            return;
        }

        if (this.blockHardness > player.getBreakSpeed(this, false, world.getBlockMetadata(x, y, z), x, y, z)) {
            if (player.getCurrentEquippedItem() != null) {
                EntityItem entityItem = player.dropOneItem(true);
                if (entityItem != null) {
                    world.spawnEntityInWorld(player.dropOneItem(true));
                }
            } else if (!world.isRemote) {
                player.setFire(7);
            }
        } else {
            player.attackEntityFrom(new EntityDamageSourceAwfull(null), 2);
        }
        super.onBlockClicked(world, x, y, z, player);
    }

    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLiving = (EntityLivingBase) entity;
            if (entityLiving instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entityLiving;
                player.jump();
                player.motionY += 0.2F;
                if (world.isRemote) {
                    player.addChatMessage(new ChatComponentText(ColorHelper.getColorByName("DARK_PURPLE") + "Up you go"));
                }
            }
            entityLiving.attackEntityFrom(new EntityDamageSourceAwfull(null), 2);
        }
    }

    @Override
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
        if (blockAccess.getTileEntity(x, y, z) instanceof TileEntityBlockAwfull) {
            TileEntityBlockAwfull tileentity = (TileEntityBlockAwfull) blockAccess.getTileEntity(x, y, z);
            if (tileentity.hasFakeData()) {
                Block block = tileentity.getFakeBlock();
                FakeBlockAccess fakeAccess = new FakeBlockAccess(blockAccess, block, tileentity.getFakeBlockMeta(), x, y, z);
                return block.getIcon(fakeAccess, x, y, z, side);
            }
        }
        return super.getIcon(blockAccess, x, y, z, side);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        if (world.getTileEntity(x, y, z) != null) {
            if (world.getTileEntity(x, y, z) instanceof TileEntityBlockAwfull) {
                TileEntityBlockAwfull tileentity = (TileEntityBlockAwfull) world.getTileEntity(x, y, z);

                if (stack != null) {
                    if (stack.getItem() == Item.getItemFromBlock(RandomBlocks.blockAwfull)) {
                        int fakeBlockID = ItemBlockAwfull.getFakeBlockID(stack);
                        int fakeBlockMeta = ItemBlockAwfull.getFakeBlockMeta(stack);

                        if (fakeBlockID != -1) {
                            tileentity.setFakeBlockID(fakeBlockID);
                            tileentity.setFakeBlockMeta(fakeBlockMeta);
                        }
                    }
                }
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityBlockAwfull();
    }

    public static final boolean canImitate(World world, int x, int y, int z) {
        return canImitate(world.getBlock(x, y, z));
    }

    public static final boolean canImitate(Block block) {
        return block.getRenderType() == 0 && block != RandomBlocks.blockAwfull && block != Blocks.grass && block.renderAsNormalBlock() && block.isOpaqueCube();
    }

}
