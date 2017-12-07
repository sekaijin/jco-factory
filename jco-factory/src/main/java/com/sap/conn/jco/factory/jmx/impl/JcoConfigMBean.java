package com.sap.conn.jco.factory.jmx.impl;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.management.NotCompliantMBeanException;
import javax.management.annotation.AnnotatedMBean;
import javax.management.annotation.Description;
import javax.management.annotation.Name;

import com.sap.conn.jco.JCo;
import com.sap.conn.jco.factory.jmx.IJcoConfigMBean;

public class JcoConfigMBean extends AnnotatedMBean implements IJcoConfigMBean
{

	private static final String UNKNOWN = "unknown";

	// jco.trace_level de 0 à 10
	private static final String JCO_TRACE_LEVEL = "jco.trace_level"; 
	// jco.trace_path ne s'applique pas aux fichiers CPIC
	private static final String JCO_TRACE_PATH = "jco.trace_path";

	private String defaultPath;

	/**
	 * @throws IOException  
	 */
	@Description("default JcoConfig constructor")
	public JcoConfigMBean(
	   @Description("trace level") @Name("level")int tracelevel, 
	   @Description("trace path")  @Name("path") String path)
	      throws NotCompliantMBeanException, IOException{
	   super (IJcoConfigMBean.class);
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
