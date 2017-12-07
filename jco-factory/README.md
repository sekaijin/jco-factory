# jco-factory
JCo factory to facilitate the creation of connections to SAP.

The SAPJCO3 library is made available by SAP.

Before establishing a connection, it is necessary to initialize the library.

You must provide a DestinationDataProvider class and initialize the environment with.

Only one DestinationDataProvider can be referenced by the Envirronement.


The connection information must be provided and associated with a "destination". Only then is it possible to communicate with SAP.

This project provides a factory that supports all the actions needed to establish a connection but also the release of resources.

In addition, it is designed to work with OSGI.

## prerequisites for runtime
You must download the SAPJCO and SAPIDOC 3.x libraries to the SAP servers.

This library depend on jmx-annotation project

## running test
See jco-factory-itest project

## running standalone

### install

- Add sapjco.jar sapidoc.jar and the native lib for your system on the classpath.

### coding your app

- at startup, call 'DestinationDataProvider.startup();'. you can use parameters to specify the trace level and trace path. default is 0, ".".

- at shut down, call 'DestinationDataProvider.shutdown();' to release all recources on SAP system.

- to establish a connection, use the java "try with resources" or think about closing the connections.

		try( JCoConnexion connexion = connexionFactory.getConnexion(DESTINATION_NAME, prop);){
			JCoDestination destination = connexion.getDestination();
			destination.ping();
		}


## running on karaf (osgi)

### install

- Put sapjco.jar sapidoc.jar and the native lib for your system on lib directory of your karaf.

- Add com.sap.* on org.osgi.framework.bootdelegation in custom.properties or config.properties.

- Restart karaf.

- Drop jco-factory-1.0.0-SNAPSHOT.jar in deploy folder. or install it via maven.

		install -s mvn:com.sap.conn/jco-factory/jco-factory-1.0.0-SNAPSHOT.jar
		
The DestinationDataProvider instance is automatically referenced by the environment.

The JcoConnexionFactory service is started.

### run
Define a connection

- get a JcoConnexionFactory reference.

- calls the 'getConnection' method with the DestinationName and Properties parameters.

Or use Blueprint with this template.

		<blueprint
			xmlns="http://www.osgi.org/xmlns/blueprint/v1.0.0"
			xmlns:cm="http://aries.apache.org/blueprint/xmlns/blueprint-cm/v1.0.0"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="http://www.osgi.org/xmlns/blueprint/v1.0.0 http://www.osgi.org/xmlns/blueprint/v1.0.0/blueprint.xsd">

			<manifest xmlns="http://karaf.apache.org/xmlns/deployer/blueprint/v1.0.0" xsi:schemaLocation="http://karaf.apache.org/xmlns/deployer/blueprint/v1.0.0 http://karaf.apache.org/xmlns/deployer/blueprint/v1.0.0">
			Export-Service = com.sap.conn.jco.factory.JCoConnexion
			Bundle-Name =  CUSTOMER :: SAP :: IE2
			Bundle-Version = 1.0.1
			Bundle-Vendor = AP-HP
			Bundle-Description = SAP Connexion IE2
			Bundle-SymbolicName = sap-ie2
			</manifest>
			<reference id="jcoFactory" timeout="20000" interface="com.sap.conn.jco.factory.JcoConnexionFactory" filter="(name=JcoConnexionFactory)" />

			<bean  factory-ref="jcoFactory"  id="sap-ie2" factory-method="getConnexion" destroy-method="close">
				<argument value="ie2" />
				<argument>
				   <props>
					  <prop key="jco.client.ashost">localhost</prop>
					  <prop key="jco.client.sysnr">40</prop>
					  <prop key="jco.client.client">100</prop>
					  <prop key="jco.client.user">testuser</prop>
					  <prop key="jco.client.passwd">testuser</prop>
					  <prop key="jco.client.lang">EN</prop>
					  <!-- Load balancing -->
					  <prop key="jco.client.gwhost">localhost</prop>
					  <prop key="jco.client.gwserv">sapgw40</prop>

					  <prop key="jco.destination.pool_capacity">10</prop>
					  <!-- (in secondes) -->
					  <prop key="jco.destination.expiration_check_period">10</prop>
					  <prop key="jco.destination.expiration_time">10</prop>
				   </props>
				</argument>
			</bean>

			<!-- use the connexion on bean or share it as service like this -->
			<service ref="sap-ie2" interface="com.sap.conn.jco.factory.JCoConnexion">
				<service-properties>
					<entry key="destinationName" value="IE2"/>
				</service-properties>
			</service>
		</blueprint>

## JMX

the properties of JCo and those of each connection are exposed via jmx.

<img src="https://github.com/sekaijin/jco-factory/raw/master/src/main/doc/images/jmx.png" />