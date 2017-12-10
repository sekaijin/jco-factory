package com.sap.conn.idoc;

public class IDocDocument {

	private String data;

	public IDocDocument(String data) {
		this.data = data;
	}

	public String getIDocNumber() {
		return "1235498644";
	}

	public void setSenderPartnerType(String partnerType) {
	}

	public void setSenderPartnerNumber(String partnerNumber) {
	}

	@Override
	public String toString() {
		return data;
	}

}
