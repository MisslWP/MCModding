package com.creativemd.ingameconfigmanager.api.common.packets;

import com.creativemd.creativecore.common.container.ContainerSub;
import com.creativemd.creativecore.common.gui.controls.GuiScrollBox;
import com.creativemd.creativecore.common.packet.CreativeCorePacket;
import com.creativemd.ingameconfigmanager.api.client.gui.SubGuiBranch;
import com.creativemd.ingameconfigmanager.api.common.branch.ConfigBranch;
import com.creativemd.ingameconfigmanager.api.common.branch.ConfigSegmentCollection;
import com.creativemd.ingameconfigmanager.api.common.segment.ConfigSegment;
import com.creativemd.ingameconfigmanager.api.core.InGameConfigManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;

public class BranchInformationPacket extends CreativeCorePacket {

   public ConfigBranch branch;
   public int segStart;
   public int segEnd;
   public boolean finalPacket;
   public ConfigSegmentCollection collection;


   public BranchInformationPacket() {}

   public BranchInformationPacket(int id, int segStart, int segEnd) {
      this((ConfigBranch)ConfigBranch.branches.get(id), segStart, segEnd);
   }

   public BranchInformationPacket(ConfigBranch branch, int segStart, int segEnd) {
      this.branch = branch;
      this.segStart = segStart;
      this.segEnd = segEnd;
   }

   public void writeBytes(ByteBuf buf) {
      this.branch.onPacketSend(FMLCommonHandler.instance().getEffectiveSide().isServer(), new ConfigSegmentCollection(this.branch.getConfigSegments()));
      buf.writeInt(this.branch.id);
      int count = 0;
      ArrayList segments = this.branch.getConfigSegments();

      int i;
      for(i = this.segStart; i < this.segEnd; ++i) {
         if(segments.size() > i && ((ConfigSegment)segments.get(i)).createPacketInformation(FMLCommonHandler.instance().getEffectiveSide().isServer()) != null) {
            ++count;
         }
      }

      buf.writeBoolean(this.segEnd == segments.size());
      buf.writeBoolean(this.segStart == 0);
      buf.writeInt(count);

      for(i = this.segStart; i < this.segEnd; ++i) {
         if(segments.size() > i) {
            String input = ((ConfigSegment)segments.get(i)).createPacketInformation(FMLCommonHandler.instance().getEffectiveSide().isServer());
            if(input != null) {
               writeString(buf, ((ConfigSegment)segments.get(i)).getID());
               writeString(buf, input);
            }
         }
      }

   }

   public void readBytes(ByteBuf buf) {
      this.branch = (ConfigBranch)ConfigBranch.branches.get(buf.readInt());
      this.finalPacket = buf.readBoolean();
      boolean firstPacket = buf.readBoolean();
      int count = buf.readInt();
      this.branch.getConfigSegments();
      if(firstPacket) {
         this.branch.onBeforeReceived(FMLCommonHandler.instance().getEffectiveSide().isServer());
      }

      this.collection = new ConfigSegmentCollection(this.branch.getConfigSegments());
      ConfigSegmentCollection collectionOld = new ConfigSegmentCollection(new ArrayList(this.branch.getConfigSegments()));

      for(int i = 0; i < count; ++i) {
         String id = readString(buf);
         ConfigSegment segment = collectionOld.getSegmentByID(id);
         String information = readString(buf);
         if(segment != null) {
            segment.receivePacketInformation(information);
         } else {
            ConfigSegment fSegment = this.branch.onFailedLoadingSegment(this.collection, id, information, i);
            if(fSegment != null) {
               fSegment.receivePacketInformation(information);
               this.collection.asList().add(fSegment);
            }
         }
      }

   }

   public boolean isFinalPacket() {
      return this.finalPacket;
   }

   public void receiveUpdate(boolean server) {
      this.branch.onRecieveFromPre(server, this.collection);
      this.branch.onRecieveFrom(server, this.collection);
      this.branch.onRecieveFromPost(server, this.collection);
   }

   @SideOnly(Side.CLIENT)
   public void executeClient(EntityPlayer player) {
      if(this.isFinalPacket()) {
         this.receiveUpdate(false);
         if(player.openContainer instanceof ContainerSub && ((ContainerSub)player.openContainer).gui.getTopLayer() instanceof SubGuiBranch) {
            SubGuiBranch gui = (SubGuiBranch)((ContainerSub)player.openContainer).gui.getTopLayer();
            if(gui.branch == this.branch) {
               int scrolled = ((GuiScrollBox)gui.getControl("scrollbox")).scrolled;
               gui.createSegmentControls();
               GuiScrollBox box = (GuiScrollBox)gui.getControl("scrollbox");
               box.scrolled = scrolled;
               if(box.scrolled > box.maxScroll) {
                  box.scrolled = box.maxScroll;
               }
            }
         }
      }

   }

   public void executeServer(EntityPlayer player) {
      if(this.isFinalPacket()) {
         this.receiveUpdate(true);
         InGameConfigManager.sendUpdatePacket(this.branch);
         InGameConfigManager.saveConfig(this.branch);
      }

   }
}
