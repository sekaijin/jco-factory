package com.sap.conn.jco.factory.jmx;

import java.util.Properties;

import javax.management.annotation.Description;
import javax.management.annotation.MBeanName;

@Description("JCo Connexion Properties")
@MBeanName("com.sap.conn.jco:type=connexion,name=%s")
public interface IConnexionMBean {
	@Description("sap JCo Destination Properties")
	public Properties getProperties() throws Exception;

	@Description("Destination connexion is alive (ping test)")
	public boolean isAlive();
}
