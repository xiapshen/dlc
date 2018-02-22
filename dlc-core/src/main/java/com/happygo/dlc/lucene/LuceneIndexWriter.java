/**
 * Copyright  2017
 * 
 * All  right  reserved.
 *
 * Created  on  2017年5月29日 上午10:23:02
 *
 * @Package com.happygo.dlc.lucene  
 * @Title: LuceneIndexWriter.java
 * @Description: LuceneIndexWriter.java
 * @author sxp (1378127237@qq.com) 
 * @version 1.0.0 
 */
package com.happygo.dlc.lucene;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.happgo.dlc.base.DLCException;
import com.happgo.dlc.base.util.Assert;

/**
 * ClassName:LuceneIndexWriter
 * 
 * @Description: LuceneIndexWriter.java
 * @author sxp (1378127237@qq.com)
 * @date:2017年5月29日 下午7:08:30
 */
public final class LuceneIndexWriter {

	/**
	 * IndexWriter the indexWriter
	 */
	private IndexWriter indexWriter;

	/**
	 * Directory the directory
	 */
	private Directory directory;

	/**
	 * Constructor com.happygo.dlc.lucene.LuceneIndexWriter
	 * 
	 * @param analyzer
	 * @param dirPath
	 */
	private LuceneIndexWriter(Analyzer analyzer, String dirPath) {
		Assert.isNull(analyzer);
		Assert.isNull(dirPath);

		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		try {
			directory = FSDirectory.open(Paths.get(dirPath));
			indexWriter = new IndexWriter(directory, indexWriterConfig);
		} catch (IOException e) {
			throw new DLCException("initial LuceneIndexWriter failed! exception message is:" + e.getMessage(), e);
		}
	}

	/**
	 * @MethodName: indexWriter
	 * @Description: the indexWriter
	 * @param analyzer
	 * @param dirPath
	 * @return LuceneIndexWriter
	 */
	public static LuceneIndexWriter indexWriter(Analyzer analyzer,
			String dirPath) {
		return new LuceneIndexWriter(analyzer, dirPath);
	}

	/**
	 * @MethodName: addField
	 * @Description: the addField
	 * @param doc
	 * @param name
	 * @param value
	 * @param fieldType
	 */
	@Deprecated
	public void addField(Document doc, String name, String value,
			FieldType fieldType) {
		Field field = new Field(name, value, fieldType);
		doc.add(field);
	}

	/**
	 * @MethodName: addDocument
	 * @Description: the addDocument
	 * @param doc
	 */
	public void addDocument(Document doc) {
		try {
			indexWriter.addDocument(doc);
		} catch (IOException e) {
			throw new DLCException("Add document is failed! exception message is:" + e.getMessage(), e);
		}
	}
	
	/**
	* @MethodName: numRamDocs
	* @Description: the numRamDocs
	* @return int
	*/
	public int numRamDocs() {
		return indexWriter.numDocs();
	}
	
	/**
	* @MethodName: commit
	* @Description: the commit
	*/
	public void commit() {
		try {
			indexWriter.commit();
		} catch (IOException e) {
			throw new DLCException("LuceneIndexWriter commit failed! exception message is:" + e.getMessage(), e);
		}
	}
	
	/**
	* @MethodName: deleteDocuments
	* @Description: the deleteDocuments
	* @param query
	*/
	public void deleteDocuments(Query query) {
		try {
			indexWriter.deleteDocuments(query);
		} catch (IOException e) {
			throw new DLCException("LuceneIndexWriter delete index failed! exception message is:" + e.getMessage(), e);
		}
	}

	/**
	 * @MethodName: close
	 * @Description: the close
	 */
	public void close() {
		try {
			indexWriter.close();
			directory.close();
		} catch (IOException e) {
			throw new DLCException("Close indexWriter failed! exception message is:" + e.getMessage(), e);
		}
	}
}
