package com.creativemd.ingameconfigmanager.api.common.packets;

import com.creativemd.creativecore.common.packet.CreativeCorePacket;
import com.creativemd.creativecore.common.packet.PacketHandler;
import com.creativemd.ingameconfigmanager.api.common.event.ConfigEventHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.ItemStack;

public class CraftResultPacket extends CreativeCorePacket {

   public ItemStack stack;
   public int index;


   public CraftResultPacket(int index, ItemStack stack) {
      this.stack = stack;
      this.index = index;
   }

   public CraftResultPacket() {
      this.stack = null;
   }

   public void executeClient(EntityPlayer player) {
      if(player.openContainer instanceof ContainerWorkbench) {
         ConfigEventHandler.index = this.index;
         ((ContainerWorkbench)player.openContainer).craftResult.setInventorySlotContents(0, this.stack);
      }

   }

   public void executeServer(EntityPlayer player) {
      if(player.openContainer instanceof ContainerWorkbench) {
         ((ContainerWorkbench)player.openContainer).craftResult.setInventorySlotContents(0, this.stack);
         PacketHandler.sendPacketToPlayer(new CraftResultPacket(this.index, this.stack), (EntityPlayerMP)player);
      }

   }

   public void writeBytes(ByteBuf buf) {
      ByteBufUtils.writeItemStack(buf, this.stack);
      buf.writeInt(this.index);
   }

   public void readBytes(ByteBuf buf) {
      this.stack = ByteBufUtils.readItemStack(buf);
      this.index = buf.readInt();
   }
}
