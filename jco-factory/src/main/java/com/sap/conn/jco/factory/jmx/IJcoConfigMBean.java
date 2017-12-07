package com.sap.conn.jco.factory.jmx;

import javax.management.annotation.Description;
import javax.management.annotation.Impact;
import javax.management.annotation.MBeanName;
import javax.management.annotation.Name;

@Description("Configure JCo Lib")
@MBeanName("com.sap.conn.jco:name=JCo")
public interface IJcoConfigMBean
{
   
   @Description("Start tracing with level")
   @Impact(Impact.ACTION)
   public void startTracing(@Description("trace level") @Name("level")int level);

   @Description("Start tracing level : 5")
   @Impact(Impact.ACTION)
   public void startTracing();

   @Description("Stop tracing (level 0)")
   @Impact(Impact.ACTION)
   public void stopTracing();

   @Description("Trace level")
   @Impact(Impact.ACTION)
   public void setTraceLevel(int level);
   
   @Description("Trace level")
   @Impact(Impact.INFO)
   public int getTraceLevel();

   @Description("Trace path")
   public String getTracePath();

   @Description("JCo version")
   public String getJcoVersion();

}
