/**
 * Copyright  2017
 * 
 * All  right  reserved.
 *
 * Created  on  2017年5月30日 上午7:39:31
 *
 * @Package com.happygo.dlc.log  
 * @Title: Log4j2LuceneAppender.java
 * @Description: Log4j2LuceneAppender.java
 * @author sxp (1378127237@qq.com) 
 * @version 1.0.0 
 */
package com.happygo.dlc.logging;

import com.happgo.dlc.base.DLCException;
import com.happgo.dlc.base.DlcConstants;
import com.happgo.dlc.base.util.Assert;
import com.happgo.dlc.base.util.Strings;
import com.happygo.dlc.lucene.DocumentUtils;
import com.happygo.dlc.lucene.LuceneIndexWriter;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.lucene.document.*;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:Log4j2LuceneAppender
 *
 * @Description: Log4j2LuceneAppender.java
 * @author sxp (1378127237@qq.com)
 * @date:2017年5月30日 上午7:39:31
 *
 * <Lucene name="luceneAppender" ignoreExceptions="true" target="target/lucene/index" expiryTime="1296000">
 *      <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %class{36} %L %M - %msg%xEx%n"/>
 *      <IndexField name="logId" pattern="$${ctx:logId}" />
 *      <IndexField name="time" pattern="%d{UNIX_MILLIS}" type = "LongField"/>
 *      <IndexField name="level" pattern="%-5level" />
 *      <IndexField name="content" pattern="%class{36} %L %M - %msg%xEx%n" />
 * </Lucene>
 */

@Plugin(name = "Lucene", category = "Core", elementType = "appender", printObject = true)
public class Log4j2LuceneAppender extends AbstractAppender {
	/**
	 * index directory
	 */
	private final String target;

	/**
	 * Index expiration time (seconds)
	 */
	private final long expiryTime;

	/**
	 * LuceneIndexField[] the indexFields
	 */
	private final LuceneIndexField[] indexFields;

	/**
	 * Periodically clear the index and submit the IndexWriter thread pool
	 */
	private final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(2);

	/**
	 * LuceneIndexWriter corresponding to each index directory.
	 */
    public static final ConcurrentHashMap<String, LuceneIndexWriter> writerMap = new ConcurrentHashMap<String, LuceneIndexWriter>();
    
    /**
     * List<String> the indexFieldNameList 
     */
    public static final List<String> indexFieldNameList = new CopyOnWriteArrayList<>();

	/**
	 * Constructor com.happygo.dlc.log.LuceneAppender
	 * @param name
	 * @param ignoreExceptions
	 * @param filter
	 * @param layout
	 * @param target
	 * @param expiryTime
	 * @param indexFields
	 */
	protected Log4j2LuceneAppender(String name, boolean ignoreExceptions,
			Filter filter, Layout<? extends Serializable> layout,
			String target, long expiryTime, LuceneIndexField[] indexFields) {
		super(name, filter, layout, ignoreExceptions);
		this.target = target;
		this.expiryTime = expiryTime;
		this.indexFields = indexFields;
		//校验必填索引字段
		validateRequiredIndexFields(indexFields);
		initIndexFieldAndSetSystemProp();
		LuceneAppenderInitializer.init(target, this.expiryTime, writerMap, scheduledExecutor);
	}
	
	/**
	* @MethodName: initIndexFieldAndSetSystemProp
	* @Description: the getIndexFieldNames
	*/
	private void initIndexFieldAndSetSystemProp() {
		for (final LuceneIndexField luceneIndexField : indexFields) {
			if (!DlcConstants.SYSTEM_NAME.equalsIgnoreCase(luceneIndexField.getName())) {
				indexFieldNameList.add(luceneIndexField.getName());
			}
		}
		System.setProperty("dlc.log.util", "log4j2");
	}

	/**
	 * @MethodName: validateRequiredIndexFields
	 * @Description: the validateRequiredIndexFields
	 * @param indexFields
	 */
	private void validateRequiredIndexFields(LuceneIndexField[] indexFields) {
		List<String> nameOfIndexFields = new ArrayList<>(indexFields.length);
		for (final LuceneIndexField luceneIndexField : indexFields) {
			nameOfIndexFields.add(luceneIndexField.getName());
		}
		boolean requiredFieldOfContent = (nameOfIndexFields.indexOf(DlcConstants.DLC_CONTENT) == -1);
		if (requiredFieldOfContent) {
			throw new DLCException("LuceneIndexField '" + DlcConstants.DLC_CONTENT + "' is required!");
		}
		boolean requiredFieldOfSystemName = (nameOfIndexFields.indexOf(DlcConstants.SYSTEM_NAME) == -1);
		if (requiredFieldOfSystemName) {
			throw new DLCException("LuceneIndexField '" + DlcConstants.SYSTEM_NAME + "' is required!");
		}
		boolean requiredFieldOfTime = (nameOfIndexFields.indexOf(DlcConstants.DLC_TIME) == -1);
		if (requiredFieldOfTime) {
			throw new DLCException("LuceneIndexField '" + DlcConstants.DLC_TIME + "' is required!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.logging.log4j.core.Appender#append(org.apache.logging.log4j
	 * .core.LogEvent)
	 */
	public void append(LogEvent event) {
		if (indexFields == null || indexFields.length == 0) {
			return;
		}

		LuceneIndexWriter indexWriter = LuceneAppenderInitializer.initLuceneIndexWriter(target, writerMap);
		Assert.isNull(indexWriter);
		Document doc = new Document();
		doc.add(new LongField("timestamp", event.getTimeMillis(),
				Field.Store.YES));
		try {
			String hostIp = InetAddress.getLocalHost().getHostAddress();
			doc.add(new TextField(DlcConstants.DLC_HOST_IP, hostIp,
					Field.Store.YES));
			for (final LuceneIndexField field : indexFields) {
				String name = field.getName();
				String value = DlcConstants.DLC_CONTENT.equals(name) ? field
						.getLayout().toSerializable(event) : ((event
						.getThrown() == null) ? field.getLayout()
						.toSerializable(event) : Strings.cutLatersubString(
						field.getLayout().toSerializable(event), " "));
				if (Strings.isEmpty(value) || value.matches("[$]\\{.+\\}")) {
					continue;
				}
				value = value.trim();
				String type = field.getType();
				DocumentUtils.addFieldByType(doc, type, name, value);
				if (DlcConstants.DLC_TIME.equals(name)) {
					doc.add(new NumericDocValuesField(name, Long.valueOf(value)));
				}
			}
		} catch (UnknownHostException e) {
			LOGGER.warn("<<<=== Do not get the node hostIp ===>>>");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			if (!ignoreExceptions()) {
				throw new AppenderLoggingException(e);
			}
		}
		indexWriter.addDocument(doc);
		indexWriter.commit();
	}

	/**
	* @MethodName: newBuilder
	* @Description: the newBuilder
	* @return B
	*/
	@PluginBuilderFactory
    public static <B extends Builder<B>> B newBuilder() {
        return new Builder<B>().asBuilder();
    }

	/**
	 * ClassName:Builder
	 * @Description: LuceneAppender.java
	 * @author sxp (1378127237@qq.com)
	 * @date:2017年5月30日 下午6:16:50
	 */
	public static class Builder<B extends Builder<B>> extends
			AbstractAppender.Builder<B> implements
			org.apache.logging.log4j.core.util.Builder<Log4j2LuceneAppender> {

		/**
		 * LuceneIndexField[] the indexField
		 */
		@PluginElement("IndexField")
		@Required(message = "No IndexField provided")
		private LuceneIndexField[] indexField;

		/**
		 * Integer the expiryTime
		 */
		@PluginBuilderAttribute
		private long expiryTime;

		/**
		 * String the target
		 */
		@PluginBuilderAttribute
		@Required(message = "No target provided")
		private String target;

		/**
		* @MethodName: withTarget
		* @Description: the withTarget
		* @param target
		* @return B
		*/
		public B withTarget(final String target) {
			this.target = target;
			return this.asBuilder();
		}

		/**
		* @MethodName: withExpiryTime
		* @Description: the withExpiryTime
		* @param expiryTime
		* @return B
		*/
		public B withExpiryTime(final long expiryTime) {
			this.expiryTime = expiryTime;
			return this.asBuilder();
		}

		/**
		* @MethodName: withIndexField
		* @Description: the withIndexField
		* @param indexField
		* @return B
		*/
		public B withIndexField(final LuceneIndexField... indexField) {
			this.indexField = indexField;
			return this.asBuilder();
		}

		/* (non-Javadoc)
		 * @see org.apache.logging.log4j.core.util.Builder#build()
		 */
		public Log4j2LuceneAppender build() {
			return new Log4j2LuceneAppender(getName(), isIgnoreExceptions(),
					getFilter(), this.getLayout(), this.target,
					this.expiryTime, this.indexField);
		}
	}

	/* (non-Javadoc)
	 * @see org.apache.logging.log4j.core.filter.AbstractFilterable#start()
	 */
	@Override
	public final void start() {
		if (null == writerMap.get(target)) {
			LOGGER.error(
					"No LuceneIndexWriter set for the appender named [{}].",
					this.getName());
		}
		super.start();
	}

	/* (non-Javadoc)
	 * @see org.apache.logging.log4j.core.filter.AbstractFilterable#stop(long, java.util.concurrent.TimeUnit)
	 */
	@Override
	public boolean stop(final long timeout, final TimeUnit timeUnit) {
		setStopping();
		boolean stopped = super.stop(timeout, timeUnit, false);
		LuceneIndexWriter indexWriter = writerMap.get(target);
		if (null != indexWriter) {
			indexWriter.close();
			writerMap.remove(target);
		}
		setStopped();
		return stopped;
	}
}
