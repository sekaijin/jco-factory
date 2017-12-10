package com.sap.conn.idoc.jco;

import com.sap.conn.idoc.IDocDocument;
import com.sap.conn.idoc.IDocFactory;
import com.sap.conn.jco.JCoDestination;

public class JCoIDoc {

	public static void send(IDocDocument idoc, String idocVersionDefault, JCoDestination destination, String tid) {
		return;
	}

	public static IDocFactory getIDocFactory() {
		return new IDocFactory();
	}

	public static Object getIDocRepository(JCoDestination destination) {
		return null;
	}

}
