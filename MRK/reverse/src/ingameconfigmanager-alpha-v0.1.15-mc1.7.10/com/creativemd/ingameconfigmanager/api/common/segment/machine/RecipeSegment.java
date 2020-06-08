package com.creativemd.ingameconfigmanager.api.common.segment.machine;

import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.ingameconfigmanager.api.common.machine.RecipeMachine;
import com.creativemd.ingameconfigmanager.api.common.segment.ConfigSegment;
import com.creativemd.ingameconfigmanager.api.common.segment.machine.AddRecipeSegment;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;

public abstract class RecipeSegment extends ConfigSegment {

   public RecipeMachine machine;
   public Object recipe;


   public RecipeSegment(String id, Object defaultValue, RecipeMachine machine, Object recipe) {
      super(id, defaultValue);
      this.machine = machine;
      this.recipe = recipe;
      this.addSubSegments();
   }

   public abstract void addSubSegments();

   public void onSegmentLoaded(int x, int y, int maxWidth) {
      this.machine.onControlsCreated(!(super.value instanceof Boolean)?super.value:this.recipe, this instanceof AddRecipeSegment, x, y, maxWidth, super.guiControls, super.containerControls);
   }

   public ArrayList createContainerControls(SubContainer gui, int x, int y, int maxWidth) {
      ArrayList controls = new ArrayList();

      for(int i = 0; i < super.subSegments.size(); ++i) {
         new ArrayList();
         ArrayList Subcontrols = ((ConfigSegment)super.subSegments.get(i)).createContainerControls(gui, x + ((ConfigSegment)super.subSegments.get(i)).xOffset, y + ((ConfigSegment)super.subSegments.get(i)).yOffset, maxWidth);
         ((ConfigSegment)super.subSegments.get(i)).containerControls = Subcontrols;
         controls.addAll(Subcontrols);
      }

      return controls;
   }

   @SideOnly(Side.CLIENT)
   public ArrayList createGuiControls(SubGui gui, int x, int y, int maxWidth) {
      ArrayList controls = new ArrayList();

      for(int i = 0; i < super.subSegments.size(); ++i) {
         new ArrayList();
         ArrayList Subcontrols = ((ConfigSegment)super.subSegments.get(i)).createGuiControls(gui, x + ((ConfigSegment)super.subSegments.get(i)).xOffset, y + ((ConfigSegment)super.subSegments.get(i)).yOffset, maxWidth);
         ((ConfigSegment)super.subSegments.get(i)).guiControls = Subcontrols;
         controls.addAll(Subcontrols);
      }

      return controls;
   }
}
