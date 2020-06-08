package com.creativemd.ingameconfigmanager.api.common.packets;

import com.creativemd.creativecore.common.packet.CreativeCorePacket;
import com.creativemd.ingameconfigmanager.api.common.branch.ConfigBranch;
import com.creativemd.ingameconfigmanager.api.core.InGameConfigManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class RequestInformationPacket extends CreativeCorePacket {

   public int id;


   public RequestInformationPacket() {
      this.id = -1;
   }

   public RequestInformationPacket(ConfigBranch branch) {
      this.id = branch.id;
   }

   public void writeBytes(ByteBuf buf) {
      buf.writeInt(this.id);
   }

   public void readBytes(ByteBuf buf) {
      this.id = buf.readInt();
   }

   public void executeClient(EntityPlayer player) {}

   public void executeServer(EntityPlayer player) {
      ConfigBranch branch = ConfigBranch.getBranchByID(this.id);
      if(branch != null) {
         InGameConfigManager.sendUpdatePacket(branch, player);
      } else {
         InGameConfigManager.sendAllUpdatePackets(player);
      }

   }
}
