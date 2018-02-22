/**
* Copyright  2017
* 
* All  right  reserved.
*
* Created  on  2017年5月30日 下午6:44:15
*
* @Package com.happgo.dlc.base.util  
* @Title: Strings.java
* @Description: Strings.java
* @author sxp (1378127237@qq.com) 
* @version 1.0.0 
*/
package com.happgo.dlc.base.util;

/**
 * ClassName:Strings
 * @Description: Strings.java
 * @author sxp (1378127237@qq.com) 
 * @date:2017年5月30日 下午6:44:15
 */
public class Strings {
	
	/**
	 * Constructor com.happgo.dlc.base.Strings
	 */
	private Strings() {}
	
	/**
	* @MethodName: isNotEmpty
	* @Description: the isNotEmpty
	* @param str
	* @return boolean
	*/
	public static boolean isNotEmpty(String str) {
		return (str == null || "".equals(str)) ? false : true;
	}
	
	/**
	* @MethodName: isEmpty
	* @Description: the isEmpty
	* @param str
	* @return boolean
	*/
	public static boolean isEmpty(String str) {
		return (str == null || "".equals(str)) ? true : false;
	}
	
	/**
	* @MethodName: cutLatersubString
	* @Description: the cutLatersubString
	* @param source
	* @param firstToken
	* @return String
	*/
	public static String cutLatersubString(String source, String firstToken) {
		int firstTokenPosition = source.indexOf(firstToken);
		return source.substring(0, firstTokenPosition);
	}
	
	/**
	* @MethodName: fillPreAndPostTagOnTargetString
	* @Description: the method fillPreAndPostTagOnTargetString
	* @param preTag
	* @param postTag
	* @param target
	* @param source
	* @return String
	*/
	public static String fillPreAndPostTagOnTargetString(String preTag,
			String postTag, String target, String source) {
		StringBuilder sb = new StringBuilder();
		int preTagIndex;
		int postTagIndex;
		while(source.length() > 0) {
			preTagIndex = source.indexOf(target);
			if (preTagIndex == -1) {
				break;
			}
			postTagIndex = target.length() + preTagIndex;
			sb.append(source.substring(0, preTagIndex))
			  .append(preTag)
			  .append(source.substring(preTagIndex, postTagIndex))
			  .append(postTag);
			source = source.substring(postTagIndex);
		}
		if (source.length() > 0) {
			sb.append(source);
		}
		return sb.toString();
	}
}
