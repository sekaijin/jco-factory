package com.sap.conn.jco.ext;

public interface DestinationDataEventListener
{

   public void deleted(String destName);

   public void updated(String destName);

}
