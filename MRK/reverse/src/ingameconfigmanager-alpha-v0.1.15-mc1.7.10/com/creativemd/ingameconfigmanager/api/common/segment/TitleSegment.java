package com.creativemd.ingameconfigmanager.api.common.segment;

import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.controls.GuiLabel;
import com.creativemd.ingameconfigmanager.api.common.segment.ConfigSegment;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;

public abstract class TitleSegment extends ConfigSegment {

   public String title;


   public TitleSegment(String id, String title, Object defaultValue) {
      super(id, defaultValue);
      this.title = title;
   }

   @SideOnly(Side.CLIENT)
   public ArrayList createGuiControls(SubGui gui, int x, int y, int maxWidth) {
      ArrayList controls = new ArrayList();
      controls.add(new GuiLabel(this.title, x + 10, y + 5));
      return controls;
   }

   public boolean contains(String search) {
      return this.title.contains(search);
   }
}
