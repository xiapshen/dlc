/**
 * Copyright  2017
 * 
 * All  right  reserved.
 *
 * Created  on  2017年5月29日 下午7:00:00
 *
 * @Package com.happygo.dlc.lucene  
 * @Title: LuceneHighlighterf.java
 * @Description: LuceneHighlighter.java
 * @author sxp (1378127237@qq.com) 
 * @version 1.0.0 
 */
package com.happygo.dlc.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

import com.happgo.dlc.base.DLCException;
import com.happgo.dlc.base.util.Assert;

/**
 * ClassName:LuceneHighlighter
 * 
 * @Description: LuceneHighlighter.java
 * @author sxp (1378127237@qq.com)
 * @date:2017年5月29日 下午7:00:46
 */
public class LuceneHighlighter {

	/**
	 * Highlighter the highlighter
	 */
	private Highlighter highlighter;

	/**
	 * Constructor com.happygo.dlc.lucene.LuceneHighlighter
	 * 
	 * @param preTag
	 * @param postTag
	 * @param query
	 * @param fragmentSize
	 */
	private LuceneHighlighter(String preTag, String postTag, Query query,
			int fragmentSize) {
		Assert.isNull(preTag);
		Assert.isNull(postTag);
		Assert.isNull(query);

		Formatter formatter = new SimpleHTMLFormatter(preTag, postTag);
		Scorer scorer = new QueryScorer(query);
		highlighter = new Highlighter(formatter, scorer);
		Fragmenter fragmenter = new SimpleFragmenter(fragmentSize);
		highlighter.setTextFragmenter(fragmenter);
	}

	/**
	 * @MethodName: highlight
	 * @Description: the highlight
	 * @param preTag
	 * @param postTag
	 * @param query
	 * @param fragmentSize
	 * @return LuceneHighlighter
	 */
	public static LuceneHighlighter highlight(String preTag, String postTag,
			Query query, int fragmentSize) {
		return new LuceneHighlighter(preTag, postTag, query, fragmentSize);
	}

	/**
	 * @MethodName: getBestFragment
	 * @Description: the getBestFragment
	 * @param analyzer
	 * @param doc
	 * @param fieldName
	 * @return String
	 */
	public String getBestFragment(Analyzer analyzer, Document doc,
			String fieldName) {
		try {
			return highlighter.getBestFragment(analyzer,
					fieldName, doc.get(fieldName));
		} catch (Exception e) {
			throw new DLCException(e.getMessage(), e);
		}
	}

	/**
	 * @MethodName: getBestFragment
	 * @Description: the getBestFragment
	 * @param analyzer
	 * @param fieldName
	 * @param text
	 * @return String
	 */
	public String getBestFragment(Analyzer analyzer, String fieldName,
			String text) {
		try {
			return highlighter.getBestFragment(analyzer,
					fieldName, text);
		} catch (Exception e) {
			throw new DLCException(e.getMessage(), e);
		}
	}
}
