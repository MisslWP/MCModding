package com.creativemd.ingameconfigmanager.mod;

import com.creativemd.ingameconfigmanager.api.core.TabRegistry;
import com.creativemd.ingameconfigmanager.api.tab.ModTab;
import com.creativemd.ingameconfigmanager.mod.block.AdvancedWorkbench;
import com.creativemd.ingameconfigmanager.mod.furnace.FurnaceMachine;
import com.creativemd.ingameconfigmanager.mod.general.GeneralBranch;
import com.creativemd.ingameconfigmanager.mod.workbench.WorkbenchMachine;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ConfigManagerModLoader {

   public static ModTab WorkBenchTab = new ModTab("Crafting Table", new ItemStack(Blocks.crafting_table));
   public static ModTab FurnaceTab = new ModTab("Furnace", new ItemStack(Blocks.furnace));
   public static WorkbenchMachine workbench;
   public static FurnaceMachine furnace;
   public static ModTab tab = new ModTab("InGameConfigManager", new ItemStack(Blocks.redstone_torch));
   public static AdvancedWorkbench advancedWorkbench;


   public static void loadMod() {
      workbench = new WorkbenchMachine(WorkBenchTab, "Crafting Table");
      advancedWorkbench = new AdvancedWorkbench(WorkBenchTab, "Advanced Workbench");
      TabRegistry.registerModTab(WorkBenchTab);
      furnace = new FurnaceMachine(FurnaceTab, "Furnace");
      TabRegistry.registerModTab(FurnaceTab);
      tab.addBranch(new GeneralBranch("general"));
      TabRegistry.registerModTab(tab);
   }

}
