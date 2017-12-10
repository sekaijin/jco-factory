package com.sap.conn.jco.factory;

import java.util.Properties;

import com.sap.conn.jco.JCoException;

public interface JcoConnexionFactory {

	JCoConnexion getConnexion(Properties properties) throws JCoException;

	JCoConnexion getConnexion(String destinationName, Properties properties) throws JCoException;

}
