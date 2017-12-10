package com.sap.conn.idoc;

public class IDocXMLProcessor {

    public IDocDocumentList parse(Object iDocRepository, String data) {
        return new IDocDocumentList(data);
    }

}
