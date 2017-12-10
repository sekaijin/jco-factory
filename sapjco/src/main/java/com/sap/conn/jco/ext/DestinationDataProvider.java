package com.sap.conn.jco.ext;

import java.util.Properties;

public interface DestinationDataProvider {

	Properties getDestinationProperties(String destinationName);

	void setDestinationDataEventListener(DestinationDataEventListener eventListener);

	boolean supportsEvents();

}
