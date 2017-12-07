package com.sap.conn.jco.factory;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;

public interface JCoConnexion extends AutoCloseable
{

   @Override
   void close();

   JCoDestination getDestination() throws JCoException;

}
