package com.creativemd.ingameconfigmanager.api.tab;

import com.creativemd.ingameconfigmanager.api.common.branch.ConfigBranch;
import com.creativemd.ingameconfigmanager.api.tab.Tab;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;

public class SubTab extends Tab {

   public ArrayList branches = new ArrayList();


   public SubTab(String title, ItemStack stack) {
      super(title, stack);
   }

   public void addBranch(ConfigBranch branch) {
      this.branches.add(branch);
   }
}
