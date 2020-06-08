package com.creativemd.ingameconfigmanager.api.core;

import com.creativemd.ingameconfigmanager.api.tab.ModTab;
import java.util.ArrayList;

public class TabRegistry {

   private static ArrayList tabs = new ArrayList();


   public static ModTab registerModTab(ModTab tab) {
      tab.setID(tabs.size());
      tabs.add(tab);
      return tab;
   }

   public static ModTab getTabByIndex(int index) {
      return (ModTab)tabs.get(index);
   }

   public static ArrayList getTabs() {
      return tabs;
   }

}
