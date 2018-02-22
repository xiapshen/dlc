/**
* Copyright  2017
* 
* All  right  reserved.
*
* Created  on  2017年5月30日 下午6:38:53
*
* @Package com.happygo.dlc.lucene  
* @Title: DocumentUtils.java
* @Description: DocumentUtils.java
* @author sxp (1378127237@qq.com) 
* @version 1.0.0 
*/
package com.happygo.dlc.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import com.happgo.dlc.base.DLCException;
import com.happgo.dlc.base.util.Strings;

/**
 * ClassName:DocumentUtils
 * @Description: DocumentUtils.java
 * @author sxp (1378127237@qq.com) 
 * @date:2017年5月30日 下午6:38:53
 */
public class DocumentUtils {
	
	/**
	 * Constructor com.happygo.dlc.lucene.DocumentUtils
	 */
	private DocumentUtils() {}
	
	/**
	* @MethodName: addFieldByType
	* @Description: the addFieldByType
	* @param doc
	* @param type
	* @param name
	* @param value
	* @throws ClassNotFoundException
	*/
	public static void addFieldByType(Document doc, String type, String name,
			String value) throws ClassNotFoundException {
		if (Strings.isNotEmpty(type)) {
			Class<?> clazz = Class
					.forName("org.apache.lucene.document." + type);
			if (clazz == LongField.class) {
				doc.add(new LongField(name, Long.valueOf(value),
						Field.Store.YES));
			} else if (clazz == StringField.class) {
				doc.add(new StringField(name, value, Field.Store.YES));
			} else if (clazz == TextField.class) {
				doc.add(new TextField(name, value, Field.Store.YES));
			} else {
				throw new DLCException(type
						+ " type currently not supported.");
			}
		} else {
			doc.add(new TextField(name, value, Field.Store.YES));
		}
	}
}
