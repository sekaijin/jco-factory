package com.sap.conn.idoc;

public class IDocFactory
{

   public static final String IDOC_VERSION_DEFAULT = null;

   public IDocXMLProcessor getIDocXMLProcessor(){
      return new IDocXMLProcessor();
   }

}
