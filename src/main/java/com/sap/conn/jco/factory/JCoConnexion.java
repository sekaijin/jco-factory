package com.sap.conn.jco.factory;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;

public interface JCoConnexion extends AutoCloseable
{

   void close();

   JCoDestination getDestination() throws JCoException;

}
