package com.sap.conn.jco.factory.impl;

import java.lang.management.ManagementFactory;

import javax.annotation.PreDestroy;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.factory.JCoConnexion;
import com.sap.conn.jco.factory.jmx.mbean.impl.MBeanConnexion;

public class JCoConnexionImpl implements JCoConnexion
{
	private static final String NOT_REGISTERED = " not registered";
	private static final String MBEAN_NAME = "com.sap.conn.jco:type=connexion,name=";

	private String destinationName;
	private String mbeanName;
	private static final Logger logger = LoggerFactory.getLogger(JCoConnexionImpl.class);

	/**
	 * @throws JCoException  
	 */
	public JCoConnexionImpl(String destinationName) throws JCoException{
		super();
		this.destinationName = destinationName;
		this.mbeanName = MBEAN_NAME+destinationName;
		try
		{
			final ObjectName objectName = new ObjectName(mbeanName);
			final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			mbs.registerMBean(new MBeanConnexion(this), objectName);
		}
		catch (Exception e)
		{logger.warn(mbeanName+NOT_REGISTERED);}
	}

	/* (non-Javadoc)
	 * @see com.sap.conn.jco.factory.ICoConnexion#close()
	 */
	@Override
	@PreDestroy
	public void close() {
		try {
			JcoConnexionFactoryImpl.remove(destinationName);
		}catch(Exception e)
		{logger.warn(destinationName+NOT_REGISTERED);}
		try
		{
			final ObjectName objectName = new ObjectName(mbeanName);
			final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			mbs.unregisterMBean(objectName);
		}
		catch (Exception e)
		{logger.warn(mbeanName+NOT_REGISTERED);}
	}

	/* (non-Javadoc)
	 * @see com.sap.conn.jco.factory.ICoConnexion#getDestination()
	 */
	@Override
	public JCoDestination getDestination() throws JCoException{
		return JCoDestinationManager.getDestination(destinationName);
	}

}
