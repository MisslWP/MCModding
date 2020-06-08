package com.creativemd.ingameconfigmanager.api.common.packets;

import com.creativemd.creativecore.common.container.ContainerSub;
import com.creativemd.creativecore.common.gui.GuiContainerSub;
import com.creativemd.creativecore.common.packet.CreativeCorePacket;
import com.creativemd.ingameconfigmanager.api.client.gui.SubGuiBranch;
import com.creativemd.ingameconfigmanager.api.client.gui.SubGuiModOverview;
import com.creativemd.ingameconfigmanager.api.client.gui.SubGuiMods;
import com.creativemd.ingameconfigmanager.api.client.gui.SubGuiProfile;
import com.creativemd.ingameconfigmanager.api.common.branch.ConfigBranch;
import com.creativemd.ingameconfigmanager.api.common.container.SubContainerBranch;
import com.creativemd.ingameconfigmanager.api.common.container.SubContainerMods;
import com.creativemd.ingameconfigmanager.api.core.TabRegistry;
import com.creativemd.ingameconfigmanager.api.tab.ModTab;
import com.creativemd.ingameconfigmanager.mod.block.SubContainerAdvancedWorkbench;
import com.creativemd.ingameconfigmanager.mod.block.SubGuiAdvancedWorkbench;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public class ConfigGuiPacket extends CreativeCorePacket {

   public int gui;
   public int index;


   public ConfigGuiPacket() {}

   public ConfigGuiPacket(int gui, int index) {
      this.gui = gui;
      this.index = index;
   }

   public void writeBytes(ByteBuf buf) {
      buf.writeInt(this.gui);
      if(this.gui > 0) {
         buf.writeInt(this.index);
      }

   }

   public void readBytes(ByteBuf buf) {
      this.gui = buf.readInt();
      if(this.gui > 0) {
         this.index = buf.readInt();
      }

   }

   @SideOnly(Side.CLIENT)
   public void executeClient(EntityPlayer player) {
      switch(this.gui) {
      case 0:
         FMLCommonHandler.instance().showGuiScreen(new GuiContainerSub(player, new SubGuiMods(), new SubContainerMods(player)));
         break;
      case 1:
         ModTab tab = TabRegistry.getTabByIndex(this.index);
         if(tab != null) {
            FMLCommonHandler.instance().showGuiScreen(new GuiContainerSub(player, new SubGuiModOverview(tab), new SubContainerMods(player)));
         }
         break;
      case 2:
         ConfigBranch branch = ConfigBranch.getBranchByID(this.index);
         if(branch != null) {
            FMLCommonHandler.instance().showGuiScreen(new GuiContainerSub(player, new SubGuiBranch(branch), new SubContainerBranch(player, branch)));
         }
         break;
      case 3:
         FMLCommonHandler.instance().showGuiScreen(new GuiContainerSub(player, new SubGuiProfile(), new SubContainerMods(player)));
         break;
      case 4:
         FMLCommonHandler.instance().showGuiScreen(new GuiContainerSub(player, new SubGuiAdvancedWorkbench(), new SubContainerAdvancedWorkbench(player)));
      }

   }

   public void executeServer(EntityPlayer player) {
      switch(this.gui) {
      case 0:
      case 3:
         this.openContainerOnServer((EntityPlayerMP)player, new ContainerSub(player, new SubContainerMods(player)));
         break;
      case 1:
         ModTab tab = TabRegistry.getTabByIndex(this.index);
         if(tab != null) {
            this.openContainerOnServer((EntityPlayerMP)player, new ContainerSub(player, new SubContainerMods(player)));
         }
         break;
      case 2:
         ConfigBranch branch = ConfigBranch.getBranchByID(this.index);
         if(branch != null) {
            this.openContainerOnServer((EntityPlayerMP)player, new ContainerSub(player, new SubContainerBranch(player, branch)));
         }
         break;
      case 4:
         this.openContainerOnServer((EntityPlayerMP)player, new ContainerSub(player, new SubContainerAdvancedWorkbench(player)));
      }

   }

   public void openContainerOnServer(EntityPlayerMP entityPlayerMP, Container container) {
      entityPlayerMP.getNextWindowId();
      entityPlayerMP.closeContainer();
      int windowId = entityPlayerMP.currentWindowId;
      entityPlayerMP.openContainer = container;
      entityPlayerMP.openContainer.windowId = windowId;
      entityPlayerMP.openContainer.addCraftingToCrafters(entityPlayerMP);
   }
}
