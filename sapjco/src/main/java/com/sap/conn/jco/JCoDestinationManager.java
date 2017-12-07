package com.sap.conn.jco;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.rt.ConnectionAttributes;

public class JCoDestinationManager implements DestinationDataEventListener
{
   private static Map<String, JCoDestination> REGISTRY = new HashMap<>();
   private DestinationDataProvider destinationDataProvider;

   public JCoDestinationManager(DestinationDataProvider destinationDataProvider){
      this.destinationDataProvider = destinationDataProvider;
   }

   public static JCoDestination getDestination(String destinationName){
      return REGISTRY.get(destinationName);
   }

   @Override
   public void deleted(String destName){
      REGISTRY.remove(destName);      
   }

   @Override
   public void updated(String destName){
      REGISTRY.put(destName, new JCoDestination()
      {

         @Override
         public void ping(){}

         @Override
         public Properties getProperties(){
            return destinationDataProvider.getDestinationProperties(destName);
         }

         @Override
         public ConnectionAttributes getAttributes(){
            return new ConnectionAttributes(destinationDataProvider.getDestinationProperties(destName));
         }

         @Override
         public String createTID(){
            return destName+"13214564";
         }

         @Override
         public void confirmTID(String tid){}
      });
   }

}
