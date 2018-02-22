/**
 * Copyright  2017
 * <p>
 * All  right  reserved.
 * <p>
 * Created  on  2017年6月1日 下午4:12:19
 *
 * @Package com.happygo.dlc.ignite.task
 * @Title: DlcKeywordSearchTask.java
 * @Description: DlcKeywordSearchTask.java
 * @author sxp (1378127237@qq.com)
 * @version 1.0.0
 */
package com.happygo.dlc.ignite.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;

import com.happgo.dlc.base.DlcConstants;
import com.happgo.dlc.base.bean.DlcLog;
import com.happgo.dlc.base.util.CollectionUtils;
import com.happgo.dlc.base.util.Strings;
import com.happygo.dlc.logging.util.DlcLogUtils;
import com.happygo.dlc.lucene.LuceneIndexSearcher;
import com.happygo.dlc.lucene.LuceneIndexWriter;

/**
 * ClassName:DlcKeywordSearchTask
 * 
 * @author sxp (1378127237@qq.com)
 * @Description: DlcIgniteTask.java
 * @date:2017年6月1日 下午4:12:19
 */
public class DlcKeywordSearchTask {

	/**
	 * The field LOGEER
	 */
	private static final Log LOGGER = LogFactory
			.getLog(DlcKeywordSearchTask.class);

	/**
	 * @MethodName: keyWordSearch
	 * @Description: the keyWordSearch
	 * @param keyWord
	 * @return List<DlcLog>
	 */
	public List<DlcLog> keyWordSearch(final String keyWord) {
		Map<String, LuceneIndexWriter> writeMap = DlcLogUtils.getWriteMap();
		Map.Entry<String, LuceneIndexWriter> entry = CollectionUtils
				.getFirstEntry(writeMap);
		final String targetPath = entry.getKey();
		// 如果索引文件夹不存在，直接返回
		if (!new File(targetPath).exists()) {
			return null;
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.info(">>> Search keyWord '" + keyWord
					+ "' on this node from target path '" + targetPath + "'");
		}
		CJKAnalyzer analyzer = new CJKAnalyzer();
		LuceneIndexSearcher indexSearcher = LuceneIndexSearcher.indexSearcher(
				targetPath, analyzer);
		ScoreDoc[] scoreDocs = indexSearcher.search(keyWord, 1, 1,
				DlcConstants.DLC_HIGHLIGHT_PRE_TAG,
				DlcConstants.DLC_HIGHLIGHT_POST_TAG,
				DlcConstants.DLC_FRAGMENT_SIZE);
		return buildDlcLogs(keyWord, scoreDocs, indexSearcher, analyzer);
	}

	/**
	 * @param scoreDocs
	 * @param iSearcher
	 * @param analyzer
	 * @return List<DlcLog>
	 * @MethodName: buildDlcLogs
	 * @Description: the buildDlcLogs
	 */
	public static List<DlcLog> buildDlcLogs(String keyWord,
			ScoreDoc[] scoreDocs, LuceneIndexSearcher iSearcher,
			Analyzer analyzer) {
		if (scoreDocs == null || scoreDocs.length == 0) {
			return null;
		}
		List<DlcLog> dlcLogs = new ArrayList<>();
		DlcLog dlcLog = null;
		Document doc = null;
		for (final ScoreDoc scoreDoc : scoreDocs) {
			doc = iSearcher.hitDocument(scoreDoc);
			String content = doc.get(DlcConstants.DLC_CONTENT);
			if ((!keyWord
					.matches("([\\s\\S]*(AND|OR|and|or)[\\s\\S]*)|([\\s\\S]*:[\\s\\S]*)")
					|| !keyWord.matches("[\\s\\S]*:[\\s\\S]*"))
					&& !DlcConstants.SYMBOY_ANY.equals(keyWord)) {
				content = Strings.fillPreAndPostTagOnTargetString(
						DlcConstants.DLC_HIGHLIGHT_PRE_TAG,
						DlcConstants.DLC_HIGHLIGHT_POST_TAG, keyWord, content);
			}
			String level = doc.get(DlcConstants.DLC_LEVEL);
			long time = (doc.getField(DlcConstants.DLC_TIME)) == null ? 0
					: (Long) doc.getField(DlcConstants.DLC_TIME).numericValue();
			String hostIp = doc.get(DlcConstants.DLC_HOST_IP);
			String systemName = doc.get(DlcConstants.SYSTEM_NAME);
			dlcLog = new DlcLog(content, level, time, hostIp, systemName);
			dlcLogs.add(dlcLog);
		}
		return dlcLogs;
	}
}
