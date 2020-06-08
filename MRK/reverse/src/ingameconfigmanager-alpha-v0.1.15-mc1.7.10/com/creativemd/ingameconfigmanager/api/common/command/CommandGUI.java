package com.creativemd.ingameconfigmanager.api.common.command;

import com.creativemd.ingameconfigmanager.api.core.InGameConfigManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class CommandGUI extends CommandBase {

   public String getCommandName() {
      return "ConfigManager";
   }

   public String getCommandUsage(ICommandSender icommandsender) {
      return "/ConfigManager";
   }

   public void processCommand(ICommandSender icommandsender, String[] astring) {
      if(icommandsender instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)icommandsender;
         InGameConfigManager.openModsGui(player);
      }

   }
}
