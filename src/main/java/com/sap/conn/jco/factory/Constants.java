package com.sap.conn.jco.factory;

/**
 * Traces de la librairie SAP
 */
public abstract class Constants {

   public static final String DDP_REG_OK 	= "[DDP_REG_OK]DestinationDataProvider registered";	
   public static final String DDP_REG_KO 	= "[DDP_REG_KO]Error registering DestinationDataProvider";

   public static final String DDP_UNREG_OK  = "[DDP_UNREG_OK]DestinationDataProvider unregistered";   
   public static final String DDP_UNREG_KO  = "[DDP_UNREG_KO]Error unregistering DestinationDataProvider";

   public static final String DDP_DDPTIES    = "[DDP_DDPTIES]getDestinationProperties(destination='%s')";
   public static final String DDP_SETDDEL    = "[DDP_SETDDEL]setDestinationDataEventListener()";
   public static final String DDP_SE         = "[DDP_SE]supportsEvents()";
   public static final String DDP_REPLPPTIES = "[DDP_REPLPPTIES]replaceProperties(destination='%s')";

   public static final String REGDEST_PPTY_OK   = "[REGDEST_PPTY_OK] Property '%s' not found";
   public static final String REGDEST_CREATE_OK = "[REGDEST_CREATE_OK] '%s' Created";
}
