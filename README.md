# jco-factory
JCo factory to facilitate the creation of connections to SAP.

The SAPJCO3 library is made available by SAP.

Before establishing a connection, it is necessary to initialize the library.

You must provide a DestinationDataProvider class and initialize the environment with.

Only one DestinationDataProvider can be referenced by the Envirronement.


The connection information must be provided and associated with a "destination". Only then is it possible to communicate with SAP.

This project provides a factory that supports all the actions needed to establish a connection but also the release of resources.

In addition, it is designed to work with OSGI.

You must download the SAPJCO and SAPIDOC 3.x libraries to the SAP servers.

you can add these libraries to your repository. this is enough to compile the project.

        <dependency>
            <groupId>com.sap.conn</groupId>
            <artifactId>sapidoc</artifactId>
            <version>3.0.0</version>
        </dependency>
        <dependency>
            <groupId>com.sap.conn</groupId>
            <artifactId>sapjco</artifactId>
            <version>3.0.0</version>
        </dependency>
