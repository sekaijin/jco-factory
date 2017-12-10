package com.sap.conn.jco.ext;

import com.sap.conn.jco.JCoDestinationManager;

public class Environment {

	public static void registerDestinationDataProvider(DestinationDataProvider destinationDataProvider) {
		DestinationDataEventListener eventListener = new JCoDestinationManager(destinationDataProvider);
		destinationDataProvider.setDestinationDataEventListener(eventListener);
	}

	public static void unregisterDestinationDataProvider(DestinationDataProvider destinationDataProvider) {
		// TODO Auto-generated method stub

	}

}
