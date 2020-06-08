package com.creativemd.ingameconfigmanager.api.common.branch;

import com.creativemd.ingameconfigmanager.api.common.segment.ConfigSegment;
import com.creativemd.ingameconfigmanager.api.core.InGameConfigManager;
import java.util.ArrayList;
import org.apache.logging.log4j.Logger;

public class ConfigSegmentCollection {

   private ArrayList segments;
   private Logger log;


   public ConfigSegmentCollection(ArrayList segment) {
      this.log = InGameConfigManager.logger;
      this.segments = segment;
   }

   public ConfigSegment getSegmentByID(String segmentID) {
      for(int i = 0; i < this.segments.size(); ++i) {
         if(((ConfigSegment)this.segments.get(i)).getID().equalsIgnoreCase(segmentID)) {
            return (ConfigSegment)this.segments.get(i);
         }
      }

      return null;
   }

   public Object getSegmentValue(String segmentID) {
      ConfigSegment segment = this.getSegmentByID(segmentID);
      return segment != null?segment.value:null;
   }

   public ArrayList asList() {
      return this.segments;
   }
}
