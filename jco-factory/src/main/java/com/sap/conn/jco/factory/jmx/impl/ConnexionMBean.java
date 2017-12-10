package com.sap.conn.jco.factory.jmx.impl;

import java.util.Properties;

import javax.management.annotation.AnnotatedMBean;
import javax.management.annotation.Description;
import javax.management.annotation.Name;

import com.sap.conn.jco.factory.impl.JCoConnexionImpl;
import com.sap.conn.jco.factory.jmx.IConnexionMBean;

public class ConnexionMBean extends AnnotatedMBean implements IConnexionMBean {

	private JCoConnexionImpl jCoConnexion;

	@Override
	public Properties getProperties() throws Exception {
		return jCoConnexion.getDestination().getProperties();
	}

	@Description("default JCoConnexion constructor")
	public ConnexionMBean(
			@Description("jCoConnexion implementation") @Name("jCoConnexion") JCoConnexionImpl jCoConnexion) {
		super(IConnexionMBean.class, jCoConnexion.getDestinationName());
		this.jCoConnexion = jCoConnexion;
	}

	@Override
	public boolean isAlive() {
		try {
			jCoConnexion.getDestination().ping();
			return true;
		} catch (Exception e) {
			return false;
		}

	}
}
