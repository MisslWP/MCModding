package com.creativemd.ingameconfigmanager.api.common.config;

import com.creativemd.ingameconfigmanager.api.common.branch.ConfigBranch;
import com.creativemd.ingameconfigmanager.api.common.segment.ConfigSegment;
import com.creativemd.ingameconfigmanager.api.core.InGameConfigManager;
import com.n247s.N2ConfigApi.api.core.ConfigFile;
import com.n247s.N2ConfigApi.api.core.ConfigSectionCollection;
import com.n247s.N2ConfigApi.api.core.ConfigHandler.ProxySide;
import cpw.mods.fml.common.FMLCommonHandler;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayerMP;

public class ProfileConfigFile extends ConfigFile {

   private static final String[] description = new String[]{" ", "This configFile is a profile ConfigFile of the IngameConfigManager.", "Profile name: %s.", " ", "Do not change this file, or things might go Horribly wrong!", " "};
   private static final String sectionOutline = "--------------------------------------------------------------------------------------------------------------";


   public ProfileConfigFile(String fileName) {
      super(fileName, ProxySide.Server);
      this.setCustomSectionStarter((String)null);
      this.setCustomSectionHeadEnder("--------------------------------------------------------------------------------------------------------------");
      description[2] = String.format(description[2], new Object[]{fileName.substring(0, fileName.length() - 7)});
      this.setDescription(description);
      this.setCustomSectionEnder("--------------------------------------------------------------------------------------------------------------");
   }

   public void generateFile() {
      this.clearAllConfigSections();
      Iterator var1 = ConfigBranch.branches.iterator();

      while(var1.hasNext()) {
         ConfigBranch currentBranch = (ConfigBranch)var1.next();
         ConfigSectionCollection collection = new ConfigSectionCollection(InGameConfigManager.getCat(currentBranch), (String[])null, true);
         Iterator var4 = currentBranch.getConfigSegments().iterator();

         while(var4.hasNext()) {
            ConfigSegment currentSegment = (ConfigSegment)var4.next();
            String input = currentSegment.createPacketInformation(FMLCommonHandler.instance().getEffectiveSide().isServer());
            collection.addNewStringSection(currentSegment.getID().toLowerCase(), (String[])null, input, true);
         }

         this.addNewSection(collection);
      }

   }

   public boolean getPermission(EntityPlayerMP player) {
      return false;
   }

}
