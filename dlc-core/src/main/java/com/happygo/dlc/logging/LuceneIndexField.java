/**
 * Copyright  2017
 * 
 * All  right  reserved.
 *
 * Created  on  2017年5月30日 上午7:39:56
 *
 * @Package com.happygo.dlc.log  
 * @Title: LuceneIndexField.java
 * @Description: LuceneIndexField.java
 * @author sxp (1378127237@qq.com) 
 * @version 1.0.0 
 */
package com.happygo.dlc.logging;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.filter.AbstractFilterable;
import org.apache.logging.log4j.core.layout.PatternLayout;

/**
 * ClassName:LuceneIndexField
 * 
 * @Description: LuceneIndexField.java
 * @author sxp (1378127237@qq.com)
 * @date:2017年5月30日 上午7:39:56
 */

@Plugin(name = "IndexField", category = "Core", printObject = true)
public class LuceneIndexField {

	/**
	 * String the name 
	 */
	private final String name;
	
	/**
	 * PatternLayout the layout 
	 */
	private final PatternLayout layout;
	
	/**
	 * String the type 
	 */
	private final String type;

	/**
	 * Constructor com.happygo.dlc.log.LuceneIndexField
	 * @param name
	 * @param layout
	 * @param type
	 */
	private LuceneIndexField(final String name, final PatternLayout layout,
			final String type) {
		this.name = name;
		this.layout = layout;
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public PatternLayout getLayout() {
		return this.layout;
	}

	public String getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{name=" + this.name + ", layout=" + this.layout + " }";
	}

	/**
	* @MethodName: newBuilder
	* @Description: the newBuilder
	* @return Builder
	*/
	@SuppressWarnings("all")
	@PluginBuilderFactory
	public static Builder newBuilder() {
		return new Builder();
	}

	/**
	 * ClassName:Builder
	 * @Description: LuceneIndexField.java
	 * @author sxp (1378127237@qq.com) 
	 * @date:2017年5月30日 上午8:12:22
	 */
	public static class Builder<B extends Builder<B>> extends
			AbstractFilterable.Builder<B> implements
			org.apache.logging.log4j.core.util.Builder<LuceneIndexField> {

		/**
		 * Configuration the configuration 
		 */
		@PluginConfiguration
		private Configuration configuration;

		/**
		 * String the name 
		 */
		@PluginBuilderAttribute
		@Required(message = "No name provided")
		private String name;

		/**
		 * String the pattern 
		 */
		@PluginBuilderAttribute
		@Required(message = "No pattern provided")
		private String pattern;

		/**
		 * String the type 
		 */
		@PluginBuilderAttribute
		private String type;

		public B withName(final String name) {
			this.name = name;
			return this.asBuilder();
		}

		public B withPattern(final String pattern) {
			this.pattern = pattern;
			return this.asBuilder();
		}

		public B withType(final String type) {
			this.type = type;
			return this.asBuilder();
		}

		/* (non-Javadoc)
		 * @see org.apache.logging.log4j.core.util.Builder#build()
		 */
		public LuceneIndexField build() {
			final PatternLayout layout = PatternLayout.newBuilder()
					.withPattern(pattern).withConfiguration(configuration)
					.build();
			return new LuceneIndexField(name, layout, type);
		}
	}
}
