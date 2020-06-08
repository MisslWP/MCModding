package com.creativemd.ingameconfigmanager.api.common.segment;

import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;

public abstract class ConfigSegment {

   protected ArrayList subSegments;
   public ConfigSegment parent;
   public Object value;
   private String ID;
   public ArrayList containerControls;
   @SideOnly(Side.CLIENT)
   public ArrayList guiControls;
   public int xOffset;
   public int yOffset;


   public ConfigSegment(String id, Object defaultValue) {
      this.ID = id;
      this.value = defaultValue;
      this.subSegments = new ArrayList();
      this.parent = null;
   }

   public ConfigSegment setOffset(int x, int y) {
      this.xOffset = x;
      this.yOffset = y;
      return this;
   }

   public ArrayList getSubSegments() {
      return this.subSegments;
   }

   public ConfigSegment addSubSegment(ConfigSegment subSegment) {
      this.subSegments.add(subSegment);
      subSegment.parent = this;
      return this;
   }

   public void onSubSegmentChanged(ConfigSegment segment) {}

   public void onSegmentLoaded(int x, int y, int maxWidth) {}

   @SideOnly(Side.CLIENT)
   public int getHeight() {
      int maxHeight = 0;

      int i;
      int tempHeight;
      for(i = 0; i < this.subSegments.size(); ++i) {
         tempHeight = ((ConfigSegment)this.subSegments.get(i)).yOffset + ((ConfigSegment)this.subSegments.get(i)).getHeight();
         if(tempHeight > maxHeight) {
            maxHeight = tempHeight;
         }
      }

      for(i = 0; i < this.guiControls.size(); ++i) {
         tempHeight = ((GuiControl)this.guiControls.get(i)).posY + ((GuiControl)this.guiControls.get(i)).height;
         if(tempHeight > maxHeight) {
            maxHeight = tempHeight;
         }
      }

      return maxHeight;
   }

   public abstract ArrayList createContainerControls(SubContainer var1, int var2, int var3, int var4);

   @SideOnly(Side.CLIENT)
   public abstract ArrayList createGuiControls(SubGui var1, int var2, int var3, int var4);

   public abstract String createPacketInformation(boolean var1);

   public abstract void receivePacketInformation(String var1);

   public ArrayList getAllSegments() {
      ArrayList segments = new ArrayList();
      segments.add(this);

      for(int i = 0; i < segments.size(); ++i) {
         segments.addAll(((ConfigSegment)segments.get(i)).getAllSegments());
      }

      return segments;
   }

   public String getID() {
      return this.ID;
   }

   public abstract boolean contains(String var1);
}
