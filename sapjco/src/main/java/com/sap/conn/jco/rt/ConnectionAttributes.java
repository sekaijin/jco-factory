package com.sap.conn.jco.rt;

import java.util.Properties;

public class ConnectionAttributes extends Properties {
	private static final long serialVersionUID = -7228021881970457099L;

	public ConnectionAttributes(Properties properties) {
		this.putAll(properties);
	}
}
