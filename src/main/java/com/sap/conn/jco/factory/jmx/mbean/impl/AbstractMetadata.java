package com.sap.conn.jco.factory.jmx.mbean.impl;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;

/**
 * Gestion de toute les méthadonées du MBean définies dans un fichier properties
 */
abstract class AbstractMetadata
{

	public abstract String getParameterName(MBeanOperationInfo op, MBeanParameterInfo param, int sequence);

	public abstract String getParameter(MBeanOperationInfo op, MBeanParameterInfo param, int sequence);

	public abstract String getAttribute(MBeanAttributeInfo info);

	public abstract String getOperation(MBeanOperationInfo info);

   public abstract String getDescription();
}
