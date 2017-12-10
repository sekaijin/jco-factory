package com.sap.conn.jco;

import java.util.Properties;
import com.sap.conn.jco.rt.ConnectionAttributes;

public interface JCoDestination {

    public Properties getProperties();

    public void ping();

    public ConnectionAttributes getAttributes();

    public String createTID();

    public void confirmTID(String tid);

}
