package shit.randomfoodstuff.tileentity;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;

public class TileEntityBlockAwfull extends RandomTileEntity {
	
	protected Block fakeBlock = null;
	protected int fakeBlockID = -1;
	protected int fakeBlockMeta = -1;
	
	public int getFakeBlockID() {
		return fakeBlockID;
	}
	
	public int getFakeBlockMeta() {
		return fakeBlockMeta;
	}
	
	public Block getFakeBlock() {
		return fakeBlock;
	}
	
	public void setFakeBlockID(int fakeBlockID) {
		this.fakeBlockID = fakeBlockID;
		this.fakeBlock = Block.getBlockById(fakeBlockID);
	}
	
	public void setFakeBlockMeta(int fakeBlockMeta) {
		this.fakeBlockMeta = fakeBlockMeta;
	}
	
	public boolean hasFakeData() {
		return getFakeBlockID() != -1;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("fakeBlockID", getFakeBlockID());
		nbt.setInteger("fakeBlockMeta", getFakeBlockMeta());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		setFakeBlockID(nbt.getInteger("fakeBlockID"));
		setFakeBlockMeta(nbt.getInteger("fakeBlockMeta"));
	}
	
	@Override
	public Packet getDescriptionPacket() {
		return super.getDescriptionPacket();
	}

}
