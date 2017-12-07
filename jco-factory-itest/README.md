# jco-factory-itest
integration testing fo JCo factory

## prerequisites
You must download the SAPJCO and SAPIDOC 3.x libraries from the SAP servers.

But you must also directly reference SAPJCO3 and SAPIDOC3 in the classpath. Because SAPJCO can not be renamed to work. see the JCO documentation about this.

you can add these libraries to your project lib dir. this is enough to run the test.

		<dependency>
			<groupId>com.sap.conn</groupId>
			<artifactId>sapidoc3</artifactId>
			<version>3.0.0</version>
			<scope>system</scope>
			<systemPath>/project/libs/sapidoc3.jar</systemPath>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>com.sap.conn</groupId>
			<artifactId>sapjco3</artifactId>
			<version>3.0.0</version>
			<scope>system</scope>
			<systemPath>/project/libs/sapjco3.jar</systemPath>
			<optional>true</optional>
		</dependency>

## running test

Adapt the config to your SAP server.

	private Properties configure(){
		Properties prop = new Properties();

		prop.setProperty("jco.client.ashost", "localhost");
		prop.setProperty("jco.client.sysnr", "40");
		prop.setProperty("jco.client.client", "100");
		prop.setProperty("jco.client.user", "testuser");
		prop.setProperty("jco.client.passwd", "testuser");
		prop.setProperty("jco.client.lang", "EN");

		// Load balancing
		prop.setProperty("jco.client.gwhost", "localhost");
		prop.setProperty("jco.client.gwserv", "sapgw40");

		// Pooling
		prop.setProperty("jco.destination.pool_capacity", "10");
		// (seconds)
		prop.setProperty("jco.destination.expiration_check_period", "10");
		prop.setProperty("jco.destination.expiration_time", "10");
		return prop;
	}



