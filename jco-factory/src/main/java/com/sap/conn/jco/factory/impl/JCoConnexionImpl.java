package com.sap.conn.jco.factory.impl;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.factory.JCoConnexion;
import com.sap.conn.jco.factory.jmx.impl.ConnexionMBean;

public class JCoConnexionImpl implements JCoConnexion
{
	private static final String NOT_CREATED = "Mbean not created";

	private String destinationName;
   private ConnexionMBean connexionMBean;
	private static final Logger logger = LoggerFactory.getLogger(JCoConnexionImpl.class);

	/**
	 * @throws JCoException  
	 */
	public JCoConnexionImpl(String destinationName) throws JCoException{
		super();
		this.destinationName = destinationName;
		try
		{
		   connexionMBean = new ConnexionMBean(this,destinationName);
		}
		catch (Exception e)
		{logger.warn(destinationName+NOT_CREATED);}
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
		{logger.warn(destinationName+NOT_CREATED);}
		if (null != connexionMBean) {
		   connexionMBean.unregister();
		}
	}

	/* (non-Javadoc)
	 * @see com.sap.conn.jco.factory.ICoConnexion#getDestination()
	 */
	@Override
	public JCoDestination getDestination() throws JCoException{
		return JCoDestinationManager.getDestination(destinationName);
	}

}
