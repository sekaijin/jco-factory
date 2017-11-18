package com.sap.conn.jco.factory.jmx.mbean.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.management.NotCompliantMBeanException;

import com.sap.conn.jco.JCo;
import com.sap.conn.jco.factory.jmx.mbean.IMBeanJcoConfig;

public class MBeanJcoConfig extends AbstractMBean implements IMBeanJcoConfig
{

	private static final String UNKNOWN = "unknown";

	/**
	 * nom du fichier de propriété contenant les metadata
	 */
	private static final String METADATA_PROPERTIES = "mbeanjco.metadata";

	// jco.trace_level de 0 à 10
	private static final String JCO_TRACE_LEVEL = "jco.trace_level"; 
	// jco.trace_path ne s'applique pas aux fichiers CPIC
	private static final String JCO_TRACE_PATH = "jco.trace_path";

	private String defaultPath;


	public MBeanJcoConfig() throws NotCompliantMBeanException{
		super(IMBeanJcoConfig.class, METADATA_PROPERTIES);
	}

	/**
	 * @throws IOException  
	 */
	public MBeanJcoConfig(int tracelevel, String path) throws NotCompliantMBeanException, IOException{
		this();
		JCo.setTrace(tracelevel, path);
		this.defaultPath = path;
	}

	@Override
	public void startTracing() {
		startTracing(5);
	}

	@Override
	public void startTracing(int level) {
		setTraceLevel(level);
		return;
	}

	@Override
	public void stopTracing() {
		setTraceLevel(0);
	}

	@Override
	public void setTraceLevel(int level){
		try {
			JCo.setTrace(level, getPath());
		} catch(Exception e) {
			return;
		}
	}

	private String getPath(){
		if(null == JCo.getTracePath()) {
			return defaultPath;
		}
		return JCo.getTracePath();
	}

	@Override
	public int getTraceLevel(){
		try {
			return JCo.getTraceLevel();
		} catch(Exception e) {
			return 0;
		}
	}

	public void setProperties(Properties properties) {
		setTracePath(properties.getProperty(JCO_TRACE_PATH));
		if (null != properties.getProperty(JCO_TRACE_LEVEL)) {
			setTraceLevel(Integer.parseInt(properties.getProperty(JCO_TRACE_LEVEL)));
		}
	}

	public void setTracePath(String tracePath) {
		if (null != tracePath) {
			File repTracePath = new File(tracePath);
			if(!repTracePath.exists()){
				repTracePath.mkdirs();
			}
		}
		try {
			JCo.setTrace(getTraceLevel(), tracePath);
		} catch (Exception e) {
			return;
		}
	}

	public void load(FileInputStream inputStream) throws IOException {
		Properties properties = new Properties();
		properties.load(inputStream);
		inputStream.close();
		setProperties(properties);
	}

	public void load(String fileName, Class<?> clazz) throws IOException{
		load(clazz.getClassLoader().getResource(fileName));
	}

	private void load(URL resource) throws FileNotFoundException, IOException{
		load(resource.getPath());
	}

	public void load(String fileName) throws FileNotFoundException, IOException{
		try(FileInputStream fis = new FileInputStream(fileName)) {
			load(fis);
		}
	}

	@Override
	public String getJcoVersion() {
		try {
			return JCo.getVersion();
		} catch(Exception e) {
			return UNKNOWN;
		}
	}

	@Override
	public String getTracePath(){
		return getPath();
	}

}
