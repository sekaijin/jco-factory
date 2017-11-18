package com.sap.conn.jco.factory.jmx.mbean.impl;

import java.io.IOException;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Associe un StandardMBean avec un fichier de proprités pour la gestion des métadonnées.
 *
 */
public abstract class AbstractMBean extends StandardMBean {

	private static final String CLASS_ACCESS_ERROR = "class access error: %s";
   private static final String CLASS_INSTANTIATION_ERROR = "class instantiation error: %s";
   private static final String OPEN_PROPERTIES_ERROR = "open properties error: %s";
   private static final String METADATA_ERROR = "metadata error: %s";
   private static final String METADATA_INIT_FAILED = "Metadata.init failed";
   private static Logger log = LoggerFactory.getLogger(AbstractMBean.class.getName());
	
	private AbstractMetadata metadata;

	/**
	 * Créé un MBean implémentant l'intraface iMbean, dont les métadonnées sont dans le fichier properties
	 * @param iMbean
	 * @param properties
	 * @throws NotCompliantMBeanException
	 */
	public AbstractMBean(Class<?> iMbean, String properties) throws NotCompliantMBeanException {
		super(iMbean);
		try {
		   metadata = new Metadata(properties, this.getClass());
		} catch (IOException e) {
         log.error(String.format(OPEN_PROPERTIES_ERROR, e.getMessage()));
			throw new NotCompliantMBeanException(METADATA_INIT_FAILED);
      }catch(Throwable e){
         log.error(String.format(METADATA_ERROR, e));
         throw new NotCompliantMBeanException(METADATA_INIT_FAILED);
		}
	}

   /**
    * Créé un MBean implémentant l'intraface iMbean, dont les métadonnées sont dans le fichier properties
    * @param iMbean
    * @param properties
    * @param metadata classe dérivée de Metadata à utiliser pour les métadata
    * @throws NotCompliantMBeanException
    */
	public AbstractMBean(Class<?> iMbean, String properties, Class<? extends Metadata> metadata) throws NotCompliantMBeanException {
      super(iMbean);
      try {
         Metadata md = metadata.newInstance();
         md.init(properties, this.getClass());
         this.metadata = md;
      } catch (IOException e) {
         log.error(String.format(OPEN_PROPERTIES_ERROR, e.getMessage()));
         throw new NotCompliantMBeanException(METADATA_INIT_FAILED);
      }catch(InstantiationException e){
         log.error(String.format(CLASS_INSTANTIATION_ERROR, e.getMessage()));
         throw new NotCompliantMBeanException(METADATA_INIT_FAILED);
      }catch(IllegalAccessException e){
         log.error(String.format(CLASS_ACCESS_ERROR, e.getMessage()));
         throw new NotCompliantMBeanException(METADATA_INIT_FAILED);
      }catch(Throwable e){
         log.error(String.format(OPEN_PROPERTIES_ERROR, e));
         throw new NotCompliantMBeanException(METADATA_INIT_FAILED);
      }
   }

   /**
    * Créé un MBean implémentant l'intraface iMbean, dont les métadonnées sont dans le fichier properties
    * @param iMbean
    * @param properties
    * @param metadata instance dérivée de AbstractMetadata à utiliser pour les métadata
    * @throws NotCompliantMBeanException
    */
   public AbstractMBean(Class<?> iMbean, String properties, AbstractMetadata metadata) throws NotCompliantMBeanException {
      super(iMbean);
      this.metadata = metadata;
	}

	/*
	 * Metadata
	 */

	@Override
	protected String getDescription(MBeanInfo info){
		return metadata.getDescription();
	}

	@Override
	protected String getDescription(MBeanOperationInfo info){
		return metadata.getOperation(info);
	}

	@Override
	protected int getImpact(MBeanOperationInfo info){
		return MBeanOperationInfo.ACTION;
	}

	@Override
	protected String getDescription(MBeanAttributeInfo info){
		return metadata.getAttribute(info);
	}

	@Override
	protected String getParameterName(MBeanOperationInfo op, MBeanParameterInfo param, int sequence){
		return metadata.getParameterName(op, param, sequence);
	}

	@Override
	protected String getDescription(MBeanOperationInfo op, MBeanParameterInfo param, int sequence){
		return metadata.getParameter(op, param, sequence);
	}
}
