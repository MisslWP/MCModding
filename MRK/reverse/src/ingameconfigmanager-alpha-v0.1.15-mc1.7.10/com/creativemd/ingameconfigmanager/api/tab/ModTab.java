package com.creativemd.ingameconfigmanager.api.tab;

import com.creativemd.ingameconfigmanager.api.common.branch.ConfigBranch;
import com.creativemd.ingameconfigmanager.api.core.InGameConfigManager;
import com.creativemd.ingameconfigmanager.api.tab.Tab;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Logger;

public class ModTab extends Tab {

   private Logger log;
   public ArrayList branches;
   private int id;


   public ModTab(String modname, ItemStack stack) {
      super(modname, stack);
      this.log = InGameConfigManager.logger;
      this.branches = new ArrayList();
      this.id = -1;
   }

   public void addBranch(ConfigBranch branch) {
      this.branches.add(branch);
      branch.tab = this;
   }

   public void setID(int id) {
      if(this.id == -1) {
         this.id = id;
      }

   }

   public int getID() {
      return this.id;
   }
}
