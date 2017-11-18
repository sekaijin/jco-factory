package com.sap.conn.jco.factory.jmx.mbean;

import java.util.Properties;

public interface IMBeanConnexion
{
	public Properties getProperties() throws Exception;
	public boolean ping();
}
