/**
 * Copyright  2017
 * 
 * All  right  reserved.
 *
 * Created  on  2017年5月29日 下午6:33:43
 *
 * @Package com.happygo.dlc.lucene  
 * @Title: LuceneIndexSearcher.java
 * @Description: LuceneIndexSearcher.java
 * @author sxp (1378127237@qq.com) 
 * @version 1.0.0 
 */
package com.happygo.dlc.lucene;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.happgo.dlc.base.DLCException;
import com.happgo.dlc.base.DlcConstants;
import com.happgo.dlc.base.util.Assert;

/**
 * ClassName:LuceneIndexSearcher
 * 
 * @Description: LuceneIndexSearcher.java
 * @author sxp (1378127237@qq.com)
 * @date:2017年5月29日 下午6:33:43
 */
public final class LuceneIndexSearcher {

	/**
	 * LuceneHighlighter the luceneHighlighter
	 */
	public LuceneHighlighter luceneHighlighter;

	/**
	 * Analyzer the analyzer
	 */
	public Analyzer analyzer;

	/**
	 * IndexSearcher the indexSearcher
	 */
	private IndexSearcher indexSearcher;

	/**
	 * Directory the directory
	 */
	private Directory directory;
	
	/**
	 * DirectoryReader the iDirectoryReader 
	 */
	private DirectoryReader iDirectoryReader;

	/**
	 * Constructor com.happygo.dlc.lucene.LuceneIndexSearcher
	 * 
	 * @param dirPath
	 * @param analyzer
	 */
	private LuceneIndexSearcher(String dirPath, Analyzer analyzer) {
		Assert.isNull(dirPath);

		try {
			directory = FSDirectory.open(Paths.get(dirPath));
			this.iDirectoryReader = DirectoryReader.open(directory);
			indexSearcher = new IndexSearcher(iDirectoryReader);
		} catch (IOException e) {
			throw new DLCException(e.getMessage(), e);
		}
		this.analyzer = analyzer;
	}

	/**
	 * @MethodName: indexSearcher
	 * @Description: the indexSearcher
	 * @param dirPath
	 * @param analyzer
	 * @return LuceneIndexSearcher
	 */
	public static LuceneIndexSearcher indexSearcher(String dirPath,
			Analyzer analyzer) {
		return new LuceneIndexSearcher(dirPath, analyzer);
	}
	
	/**
	* @MethodName: search
	* @Description: the search
	* @param keyWord
	* @param minTermFreq
	* @param minDocFreq
	* @return
	* @return ScoreDoc[]
	*/
	public ScoreDoc[] search(String keyWord, int minTermFreq, int minDocFreq, String preTag,
			String postTag, int fragmentSize) {
		Query query = DlcQueryParser.parse(keyWord, minTermFreq, minDocFreq, iDirectoryReader, analyzer);
		SortField sortField = new SortField(DlcConstants.DLC_TIME, 
				SortField.Type.LONG, true);
		Sort sort = new Sort(sortField);
		ScoreDoc[] scoreDocs = null;
		try {
			scoreDocs = indexSearcher.search(query, Integer.MAX_VALUE, sort).scoreDocs;
		} catch (IOException e) {
			throw new DLCException(e.getMessage(), e);
		}
		return scoreDocs;
	}

	/**
	 * @MethodName: fuzzySearch
	 * @Description: the fuzzySearch
	 * @param fieldName
	 * @param text
	 * @param preTag
	 * @param postTag
	 * @param fragmentSize
	 * @return ScoreDoc[]
	 */
	public ScoreDoc[] fuzzySearch(String fieldName, String text, String preTag,
			String postTag, int fragmentSize) {
		Term term = new Term(fieldName, text);
		Query query = new FuzzyQuery(term);
		luceneHighlighter = LuceneHighlighter.highlight(preTag, postTag, query,
				fragmentSize);
		ScoreDoc[] scoreDocs;
		try {
			scoreDocs = indexSearcher.search(query, Integer.MAX_VALUE).scoreDocs;
		} catch (IOException e) {
			throw new DLCException(e.getMessage(), e);
		}
		return scoreDocs;
	}
	
	/**
	* @MethodName: phraseSearch
	* @Description: the phraseSearch
	* @param fieldName
	* @param text
	* @param preTag
	* @param postTag
	* @param fragmentSize
	* @return ScoreDoc[]
	*/
	public ScoreDoc[] phraseSearch(String fieldName, String text, String preTag,
			String postTag, int fragmentSize) {
		Term term = new Term(fieldName, text);
		PhraseQuery.Builder builder = new PhraseQuery.Builder();
		builder.add(term);
		PhraseQuery query = builder.build();
		luceneHighlighter = LuceneHighlighter.highlight(preTag, postTag, query,
				fragmentSize);
		ScoreDoc[] scoreDocs;
		try {
			SortField sortField = new SortField(DlcConstants.DLC_TIME, 
					SortField.Type.LONG, true);
			Sort sort = new Sort(sortField);
			scoreDocs = indexSearcher.search(query, Integer.MAX_VALUE, sort).scoreDocs;
		} catch (IOException e) {
			throw new DLCException(e.getMessage(), e);
		}
		return scoreDocs;
	}
	
	/**
	* @MethodName: matchAllDocsSearch
	* @Description: the matchAllDocsSearch
	* @return ScoreDoc[]
	*/
	public ScoreDoc[] matchAllDocsSearch() {
		MatchAllDocsQuery query = new MatchAllDocsQuery();
		ScoreDoc[] scoreDocs;
		try {
			SortField sortField = new SortField(DlcConstants.DLC_TIME, 
					SortField.Type.LONG, true);
			Sort sort = new Sort(sortField);
			scoreDocs = indexSearcher.search(query, Integer.MAX_VALUE, sort).scoreDocs;
		} catch (IOException e) {
			throw new DLCException(e.getMessage(), e);
		}
		return scoreDocs;
	}

	/**
	 * @MethodName: multiFieldSearch
	 * @Description: the multiFieldSearch
	 * @param queryStrs
	 * @param fields
	 * @param occurs
	 * @param preTag
	 * @param postTag
	 * @param fragmentSize
	 * @return ScoreDoc[]
	 */
	public ScoreDoc[] multiFieldSearch(String[] queryStrs, String[] fields,
			Occur[] occurs, String preTag, String postTag, int fragmentSize) {
		ScoreDoc[] scoreDocs;
		try {
			Query query = MultiFieldQueryParser.parse(queryStrs, fields,
					occurs, analyzer);
			luceneHighlighter = LuceneHighlighter.highlight(preTag, postTag,
					query, fragmentSize);
			scoreDocs = indexSearcher.search(query, Integer.MAX_VALUE).scoreDocs;
		} catch (Exception e) {
			throw new DLCException(e.getMessage(), e);
		}
		return scoreDocs;
	}
	
	/**
	 * @MethodName: hitDocument
	 * @Description: the hitDocument
	 * @param scoreDoc
	 * @return Document
	 */
	public Document hitDocument(ScoreDoc scoreDoc) {
		try {
			return indexSearcher.doc(scoreDoc.doc);
		} catch (IOException e) {
			throw new DLCException(e.getMessage(), e);
		}
	}
}
