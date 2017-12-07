package com.sap.conn.idoc;

public class IDocDocumentList
{

   private String data;

   public IDocDocumentList(String data){
      this.data = data;
   }

   public IDocDocument get(int i){
      IDocDocument idoc = new IDocDocument(data);
      return idoc;
   }

}
