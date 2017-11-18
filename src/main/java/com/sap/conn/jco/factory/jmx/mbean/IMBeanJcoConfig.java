package com.sap.conn.jco.factory.jmx.mbean;

public interface IMBeanJcoConfig
{

   public void startTracing(int level);
   public void startTracing();
   public void stopTracing();

   public void setTraceLevel(int level);
   public int getTraceLevel();
   public String getTracePath();

   public String getJcoVersion();

}
