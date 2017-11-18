package com.sap.conn.jco.factory;

import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharEncoding;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.conn.idoc.IDocConversionException;
import com.sap.conn.idoc.IDocDocument;
import com.sap.conn.idoc.IDocDocumentList;
import com.sap.conn.idoc.IDocFactory;
import com.sap.conn.idoc.IDocParseException;
import com.sap.conn.idoc.IDocSyntaxException;
import com.sap.conn.idoc.IDocXMLProcessor;
import com.sap.conn.idoc.jco.JCoIDoc;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.factory.impl.JcoConnexionFactoryImpl;

public class SendTest
{
   private static final Logger logger = LoggerFactory.getLogger(SendTest.class);

   public static void main(String[] args) throws Exception{
      SendTest send = new SendTest();
      send.test();
   }

   @Test
   @Ignore
   public void test() throws Exception{

      DestinationDataProvider.startup();
      //DestinationDataProviderImpl.startup(4, "target");
      Properties prop = configure();
      JcoConnexionFactory connexionFactory = new JcoConnexionFactoryImpl();
      try( JCoConnexion connexion = connexionFactory.getConnexion("de2", prop);){
         JCoDestination destination = connexion.getDestination();
         destination.ping();
         logger.info(destination.getAttributes().toString());

         IDocDocument idoc = getIdoc(destination);

         String tid = destination.createTID();
         if(null != tid){
            JCoIDoc.send(idoc, IDocFactory.IDOC_VERSION_DEFAULT, destination, tid);
            destination.confirmTID(tid);
         }
         logger.info(tid);
         logger.info(idoc.getIDocNumber());
         logger.info(idoc.toString());
         assertNotNull("Not null TID", tid);
      }
      DestinationDataProvider.shutdown();
   }

   private Properties configure(){
      Properties prop = new Properties();

      prop.setProperty("jco.client.ashost", "nsigdb08.bbs.aphp.fr");
      prop.setProperty("jco.client.sysnr", "40");
      prop.setProperty("jco.client.client", "100");
      prop.setProperty("jco.client.user", "eaibatch");
      prop.setProperty("jco.client.passwd", "eaibatch");
      prop.setProperty("jco.client.lang", "FR");

      // Load balancing
      prop.setProperty("jco.client.gwhost", "nsigdb08.bbs.aphp.fr");
      prop.setProperty("jco.client.gwserv", "sapgw40");

      // Pooling
      prop.setProperty("jco.destination.pool_capacity", "10");
      // (seconds)
      prop.setProperty("jco.destination.expiration_check_period", "10");
      prop.setProperty("jco.destination.expiration_time", "10");
      return prop;
   }

   private IDocDocument getIdoc(JCoDestination destination) throws FileNotFoundException, URISyntaxException, IOException,
      IDocParseException, JCoException, IDocConversionException, IDocSyntaxException{

      final InputStream fileReader = getClass().getClassLoader().getResourceAsStream("idoc48603_specifique.xml");
      String data = IOUtils.toString(fileReader, CharEncoding.UTF_8);

      logger.info(data);

      String partnerType = "LS";
      String partnerNumber = "EAI";

      IDocDocument idoc = null;
      IDocXMLProcessor xmlProcessor = JCoIDoc.getIDocFactory().getIDocXMLProcessor();
      IDocDocumentList idocList = xmlProcessor.parse(JCoIDoc.getIDocRepository(destination), data);
      idoc = idocList.get(0);
      if(partnerType != null)
         idoc.setSenderPartnerType(partnerType);
      if(partnerNumber != null)
         idoc.setSenderPartnerNumber(partnerNumber);
      return idoc;
   }

}
