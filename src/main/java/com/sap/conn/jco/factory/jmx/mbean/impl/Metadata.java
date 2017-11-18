package com.sap.conn.jco.factory.jmx.mbean.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;

import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Gestion de toute les méthadonées du MBean définies dans un fichier properties
 */
class Metadata extends AbstractMetadata
{

	//CHECKSTYLE:OFF: JavadocVariable
	private static final String OPERATION = ".operation";
	private static final String ATTRIBUTE = ".attribute";
	private static final String PARAMETER = ".parameter";
	private static final String PARAMETER_NAME = ".parameterName";
	private static final String M_BEAN_INFO_DESCRIPTION = "MBeanInfo.description";
	private static final String SEPPARATOR = "_";
	private static Logger log = LoggerFactory.getLogger(Metadata.class.getName());
	//CHECKSTYLE:ON: JavadocVariable

	/**
	 * liste des metadata
	 */
	private Properties metadata = null;

	public Metadata(){
      super();
   }

   public Metadata(String properties, Class<? extends AbstractMBean> clazz) throws Exception{
      super();
      this.init(properties, clazz);
   }

   void init(String properties, Class<? extends AbstractMBean> clazz) throws Exception{
		if (null == metadata) {
			URL url = clazz.getClassLoader().getResource(properties);
			metadata = new Properties();
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), CharEncoding.UTF_8));
			metadata.load(in); 
		}
	}

	@Override
	public String getParameterName(MBeanOperationInfo op, MBeanParameterInfo param, int sequence) {
		String key = join(op.getName(), param.getName());
		log.debug(key + PARAMETER_NAME);
		return metadata.getProperty(key + PARAMETER_NAME);
	}

	@Override
	public String getParameter(MBeanOperationInfo op, MBeanParameterInfo param, int sequence) {
		String key = join(op.getName(), param.getName());
		log.debug(key + PARAMETER);
		return metadata.getProperty(key + PARAMETER);
	}

	@Override
	public String getAttribute(MBeanAttributeInfo info) {
		String key = info.getName();
		log.debug(key + ATTRIBUTE);
		return metadata.getProperty(key + ATTRIBUTE);
	}

	@Override
	public String getOperation(MBeanOperationInfo info) {
		String key = info.getName();
		if (info.getSignature().length > 0) {
			key = join(key, info.getSignature().length);
		}
		log.debug(key + OPERATION);
		return metadata.getProperty(key + OPERATION);
	}

   @Override
public String getDescription() {
		return metadata.getProperty(M_BEAN_INFO_DESCRIPTION);
	}
	
	protected static String join(Object... items) {
		return StringUtils.join(items, SEPPARATOR);
	}
}
