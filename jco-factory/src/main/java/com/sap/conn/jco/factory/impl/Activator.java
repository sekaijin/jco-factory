package com.sap.conn.jco.factory.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.sap.conn.jco.factory.DestinationDataProvider;

public class Activator implements BundleActivator {
	@Override
	public void start(BundleContext context) {
		DestinationDataProvider.startup();
	}

	@Override
	public void stop(BundleContext context) {
		DestinationDataProvider.shutdown();
	}

}
