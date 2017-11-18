package com.sap.conn.jco.factory;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Properties;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.Environment;
import com.sap.conn.jco.factory.jmx.mbean.impl.MBeanJcoConfig;

public class DestinationDataProvider implements com.sap.conn.jco.ext.DestinationDataProvider {
   private static final String DEFAULT_LOG_PATH = "data/log";
   private static final Logger logger = LoggerFactory.getLogger(DestinationDataProvider.class);
   private static final String MBEAN_NAME = "com.sap.conn.jco:name=JCo";
   private static final String MBEAN_NOT_REGISTERED = MBEAN_NAME+" not registered";

   private static DestinationDataProvider destinationDataProvider = null;

   private DestinationDataEventListener listener;
   private HashMap<String, Properties> registry = new HashMap<>();

   private DestinationDataProvider() {
      super();
   }

   public static DestinationDataProvider getInstance() {
      return destinationDataProvider;
   }

   public static void startup() {
      startup(0, DEFAULT_LOG_PATH);
   }

   public static void startup(int tracelevel) {
      startup(tracelevel, DEFAULT_LOG_PATH);
   }

   public static void startup(int tracelevel, String tracePath) {
      destinationDataProvider = new DestinationDataProvider();
      try{
         Environment.registerDestinationDataProvider(destinationDataProvider);
         logger.debug(Constants.DDP_REG_OK);
      }
      catch(IllegalStateException providerAlreadyRegisteredException){
         logger.error(Constants.DDP_REG_KO, providerAlreadyRegisteredException);
         throw providerAlreadyRegisteredException; 
      }   
      try
      {
         final ObjectName objectName = new ObjectName(MBEAN_NAME);
         final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
         mbs.registerMBean(new MBeanJcoConfig(tracelevel, tracePath), objectName);
      }
      catch (Exception e)
      {logger.warn(MBEAN_NOT_REGISTERED, e);}
      return ;
   }

   public static void shutdown() {
      if (null != destinationDataProvider) {
         try{
            Environment.unregisterDestinationDataProvider(destinationDataProvider);
            destinationDataProvider= null;
            logger.debug(Constants.DDP_UNREG_OK);
         }
         catch(IllegalStateException providerAlreadyRegisteredException){
            logger.warn(Constants.DDP_UNREG_KO, providerAlreadyRegisteredException);
         }               
      }
      try
      {
         final ObjectName objectName = new ObjectName(MBEAN_NAME);
         final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
         mbs.unregisterMBean(objectName);
      }
      catch (Exception e)
      {logger.warn(MBEAN_NOT_REGISTERED, e);}
      return;
   }

   @Override
   public Properties getDestinationProperties(String destinationName) {
      logger.debug(String.format(Constants.DDP_DDPTIES, destinationName));
      return registry.get(destinationName);
   }

   //An implementation supporting events has to retain the eventListener instance provided
   //by the JCo runtime. This listener instance shall be used to notify the JCo runtime
   //about all changes in destination configurations.
   @Override
   public void setDestinationDataEventListener(DestinationDataEventListener eventListener){
      logger.debug(String.format(Constants.DDP_SETDDEL));
      this.listener = eventListener;
   }

   @Override
   public boolean supportsEvents(){
      logger.debug(String.format(Constants.DDP_SE));
      return true;
   }

   //implementation that saves the properties in a very secure way
   public void replaceProperties(String destName, Properties properties){
      logger.debug(String.format(Constants.DDP_REPLPPTIES, destName));

      synchronized(registry){
         if(properties==null){
            if(registry.remove(destName)!=null)
               listener.deleted(destName);
         }else{
            registry.put(destName, properties);
            listener.updated(destName); // create or updated
         }
      }
   }
}

