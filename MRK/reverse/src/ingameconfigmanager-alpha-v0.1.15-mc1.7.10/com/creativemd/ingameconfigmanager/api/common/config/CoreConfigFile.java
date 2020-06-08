package com.creativemd.ingameconfigmanager.api.common.config;

import com.n247s.N2ConfigApi.api.core.ConfigFile;
import com.n247s.N2ConfigApi.api.core.ConfigSectionCollection;
import com.n247s.N2ConfigApi.api.core.ConfigHandler.ProxySide;
import net.minecraft.entity.player.EntityPlayerMP;

public class CoreConfigFile extends ConfigFile {

   private static final String[] description = new String[]{" ", "This configFile is the core ConfigFile of the IngameConfigManager.", " ", "Do not change this file, or things might go Horribly wrong!", " "};
   private static final String sectionOutline = "--------------------------------------------------------------------------------------------------------------";


   public CoreConfigFile(String fileName) {
      super(fileName, ProxySide.Server);
      this.setCustomSectionStarter((String)null);
      this.setCustomSectionHeadEnder("--------------------------------------------------------------------------------------------------------------");
      this.setDescription(description);
      this.setCustomSectionEnder("--------------------------------------------------------------------------------------------------------------");
   }

   public void generateFile() {
      this.clearAllConfigSections();
      ConfigSectionCollection sectionCollection = new ConfigSectionCollection("General", (String[])null, true);
      sectionCollection.addNewStringSection("profileName", (String[])null, "new1", true);
      sectionCollection.addNewStringArraySection("profiles", (String[])null, new String[0], true, false);
      this.addNewSection(sectionCollection);
   }

   public String getProfileName() {
      return (String)this.getValue("profileName");
   }

   public String[] getProfiles() {
      return (String[])((String[])this.getValue("profiles"));
   }

   public boolean getPermission(EntityPlayerMP player) {
      return false;
   }

}
