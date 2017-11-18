package com.sap.conn.jco.factory.jmx.mbean.impl;

import java.util.Properties;

import javax.management.NotCompliantMBeanException;

import com.sap.conn.jco.factory.impl.JCoConnexionImpl;
import com.sap.conn.jco.factory.jmx.mbean.IMBeanConnexion;

public class MBeanConnexion extends AbstractMBean implements IMBeanConnexion {

	/**
	 * nom du fichier de propriété contenant les metadata
	 */
	private static final String METADATA_PROPERTIES = "mbeanconnexion.metadata";

   private JCoConnexionImpl jCoConnexion;

	public Properties getProperties() throws Exception {
		return jCoConnexion.getDestination().getProperties();
	}

	public MBeanConnexion(JCoConnexionImpl jCoConnexion) throws NotCompliantMBeanException {
		super(IMBeanConnexion.class, METADATA_PROPERTIES);
		this.jCoConnexion = jCoConnexion;
	}

	public boolean ping() {
	   try {
	      jCoConnexion.getDestination().ping();
	      return true; 
	   }catch (Exception e) {
         return false;
      }
	   
	}
}
