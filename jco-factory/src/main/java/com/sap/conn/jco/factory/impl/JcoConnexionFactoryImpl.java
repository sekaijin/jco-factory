package com.sap.conn.jco.factory.impl;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.sap.conn.jco.JCoDestination;
//import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.factory.Constants;
import com.sap.conn.jco.factory.DestinationDataProvider;
import com.sap.conn.jco.factory.JCoConnexion;
import com.sap.conn.jco.factory.JcoConnexionFactory;

public class JcoConnexionFactoryImpl implements JcoConnexionFactory {
   private static final String DESTINATION_NAME_PROPERTY = "destinationName";
   private static final String DESTINATION_NAME_KO = String.format(Constants.REGDEST_PPTY_OK, DESTINATION_NAME_PROPERTY);

   private static final Logger logger = LoggerFactory.getLogger(JcoConnexionFactoryImpl.class);

   private static Map<String,JCoConnexion> destinationRegistry = new ConcurrentHashMap<>();

   private static synchronized DestinationDataProvider getDestinationDataProvider() {
      return DestinationDataProvider.getInstance();
   }

   /* (non-Javadoc)
    * @see com.sap.conn.jco.factory.impl.JcoConnexionFactory#getConnexion(java.util.Properties)
    */
   @Override
   public JCoConnexion getConnexion(Properties properties) throws JCoException{
      String destinationName = properties.getProperty(DESTINATION_NAME_PROPERTY);
      if(destinationName == null)
         throw new JCoException(101, 
            DESTINATION_NAME_KO); 

      return getConnexion(destinationName, properties);
   }

   /* (non-Javadoc)
    * @see com.sap.conn.jco.factory.impl.JcoConnexionFactory#getConnexion(java.lang.String, java.util.Properties)
    */
   @Override
   public JCoConnexion getConnexion(String destinationName, Properties properties) throws JCoException{
      try{         
         getDestinationDataProvider().replaceProperties(destinationName, properties);
         JCoConnexion dest = new JCoConnexionImpl(destinationName);
         destinationRegistry.put(destinationName, dest);
         logger.debug(String.format(Constants.REGDEST_CREATE_OK, destinationName));
         return dest;

      }catch(JCoException e){
         logger.error(e.getMessage());
         throw e;        
      }
   }  

   public static void remove(String destinationName){
      getDestinationDataProvider().replaceProperties(destinationName, null);
   }	
}
