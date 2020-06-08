package com.creativemd.ingameconfigmanager.api.common.segment;

import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.controls.GuiButton;
import com.creativemd.creativecore.common.gui.controls.GuiStateButton;
import com.creativemd.ingameconfigmanager.api.common.segment.TitleSegment;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;

public class BooleanSegment extends TitleSegment {

   public BooleanSegment(String id, String Title, Boolean defaultValue) {
      super(id, Title, defaultValue);
   }

   public ArrayList createContainerControls(SubContainer gui, int x, int y, int maxWidth) {
      ArrayList controls = new ArrayList();
      return controls;
   }

   @SideOnly(Side.CLIENT)
   public ArrayList createGuiControls(SubGui gui, int x, int y, int maxWidth) {
      ArrayList controls = super.createGuiControls(gui, x, y, maxWidth);
      controls.add(new GuiStateButton(this.getID(), ((Boolean)super.value).toString().replace("f", "F").replace("t", "T"), x + maxWidth - 50, y, 40, 20, new String[]{"True", "False"}));
      return controls;
   }

   public String createPacketInformation(boolean isServer) {
      if(!isServer && super.guiControls != null && super.guiControls.size() == 2) {
         super.value = Boolean.valueOf(Boolean.parseBoolean(((GuiButton)super.guiControls.get(1)).caption));
      }

      return ((Boolean)super.value).toString();
   }

   public void receivePacketInformation(String input) {
      super.value = Boolean.valueOf(Boolean.parseBoolean(input));
   }

   public boolean contains(String search) {
      return super.contains(search) || ((Boolean)super.value).toString().contains(search);
   }
}
