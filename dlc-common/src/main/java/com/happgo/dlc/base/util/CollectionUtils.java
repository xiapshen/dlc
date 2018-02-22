/**
 * Copyright  2017
 * 
 * All  right  reserved.
 *
 * Created  on  2017年6月13日 下午9:33:20
 *
 * @Package com.happgo.dlc.base.util  
 * @Title: CollectionUtils.java
 * @Description: CollectionUtils.java
 * @author sxp (1378127237@qq.com) 
 * @version 1.0.0 
 */
package com.happgo.dlc.base.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * ClassName:CollectionUtils
 * 
 * @Description: CollectionUtils.java
 * @author sxp (1378127237@qq.com)
 * @date:2017年6月13日 下午9:33:20
 */
public class CollectionUtils {

	/**
	 * Constructor com.happgo.dlc.base.util.CollectionUtils
	 */
	private CollectionUtils() {
	}

	/**
	 * @MethodName: getFirstEntry
	 * @Description: the getFirstEntry
	 * @param map
	 * @return Entry<K, V>
	 */
	public static <K, V> Entry<K, V> getFirstEntry(Map<K, V> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		for (Entry<K, V> entry : map.entrySet()) {
			return entry;
		}
		return null;
	}

	/**
	* @MethodName: split
	* @Description: the split
	* @param original
	* @param partitionSize
	* @param listImplementation
	* @return List<List<T>>
	*/
	public static final <T> List<List<T>> split(final List<T> original,
			final int partitionSize,
			final Class<? extends List> listImplementation) {
		if (partitionSize <= 0) {
			throw new IllegalArgumentException(
					"maxListSize must be greater than zero");
		}

		final T[] elements = (T[]) original.toArray();
		final int maxChunks = (int) Math.ceil(elements.length
				/ (double) partitionSize);

		final List<List<T>> lists = new ArrayList<List<T>>(maxChunks);
		for (int i = 0; i < maxChunks; i++) {
			final int from = i * partitionSize;
			final int to = Math.min(from + partitionSize, elements.length);
			final T[] range = Arrays.copyOfRange(elements, from, to);

			lists.add(createSublist(range, listImplementation));
		}

		return lists;
	}

	/**
	 * @MethodName: split
	 * @Description: the split
	 * @param original
	 * @param partitionSize
	 * @return List<List<T>>
	 */
	public static final <T> List<List<T>> split(final List<T> original,
			final int partitionSize) {
		return split(original, partitionSize, ArrayList.class);
	}

	/**
	 * @MethodName: createSublist
	 * @Description: the createSublist
	 * @param elements
	 * @param listImplementation
	 * @return List<T>
	 */
	private static <T> List<T> createSublist(final T[] elements,
			final Class<? extends List> listImplementation) {
		List<T> sublist;
		final List<T> asList = Arrays.asList(elements);
		try {
			sublist = listImplementation.newInstance();
			sublist.addAll(asList);
		} catch (final InstantiationException e) {
			sublist = asList;
		} catch (final IllegalAccessException e) {
			sublist = asList;
		}
		return sublist;
	}
}
