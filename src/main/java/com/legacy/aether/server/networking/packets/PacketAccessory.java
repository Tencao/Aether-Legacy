package com.legacy.aether.server.networking.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.legacy.aether.server.containers.inventory.InventoryAccessories;
import com.legacy.aether.server.player.PlayerAether;

public class PacketAccessory extends AetherPacket<PacketAccessory>
{

	private InventoryAccessories accessories;

	private int entityID;

	private ByteBuf buf;

	public PacketAccessory()
	{
		
	}

	public PacketAccessory(PlayerAether thePlayer)
	{
		this.accessories = thePlayer.accessories;
		this.entityID = thePlayer.thePlayer.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		this.entityID = buf.readInt();
		this.buf = buf;
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(this.entityID);
		this.accessories.writeData(buf);
	}

	@Override
	public void handleClient(PacketAccessory message, EntityPlayer player) 
	{
		if (player != null && player.worldObj != null)
		{
			EntityPlayer parent = (EntityPlayer) player.worldObj.getEntityByID(message.entityID);

			if (parent != null)
			{
				PlayerAether.get(parent).accessories.readData(message.buf);
			}
		}
	}

	@Override
	public void handleServer(PacketAccessory message, EntityPlayer player)
	{

	}

}
