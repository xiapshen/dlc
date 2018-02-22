/**
 * Copyright  2017
 * 
 * All  right  reserved.
 *
 * Created  on  2017年8月9日 下午7:05:38
 *
 * @Package com.happygo.dlc.lucene  
 * @Title: DlcQueryParser.java
 * @Description: DlcQueryParser.java
 * @author sxp (1378127237@qq.com) 
 * @version 1.0.0 
 */
package com.happygo.dlc.lucene;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.WildcardQuery;

import com.happgo.dlc.base.DLCException;
import com.happgo.dlc.base.DlcConstants;
import com.happgo.dlc.base.util.DateUtils;

/**
 * ClassName:DlcQueryParser
 * 
 * @Description: DlcQueryParser.java
 * @author sxp (1378127237@qq.com)
 * @date:2017年8月9日 下午7:05:38
 */
public class DlcQueryParser {

	/**
	 * Constructor com.happygo.dlc.lucene.DlcQueryParser
	 */
	private DlcQueryParser() {
	}

	/**
	 * @MethodName: parse
	 * @Description: the parse
	 * @see format ---> 1、level:INFO；2、level:INFO AND time:[2017-08-09 20:12 TO
	 *      2017-08-09 21:12]
	 * @param queryStr
	 * @param minTermFreq
	 * @param minDocFreq
	 * @param indexReader
	 * @param analyzer
	 * @return Query
	 */
	public static Query parse(String queryStr, int minTermFreq, int minDocFreq,
			IndexReader indexReader, Analyzer analyzer) {
		if (queryStr.equals(DlcConstants.SYMBOY_ANY)) {
			return getMatchAllQuery();
		}

		if (!queryStr.matches("([\\s\\S]*(AND|OR|and|or)[\\s\\S]*)|([\\s\\S]*:[\\s\\S]*)")) {
			return getMoreLikeThisQuery(queryStr, minTermFreq, minDocFreq,
					indexReader, analyzer);
		}
		
		String[] queryStrs = queryStr.split("(\\s+AND\\s+|\\s+OR\\s+|\\s+and\\s+|\\s+or\\s+)");
		return getBooleanQuery(queryStr, queryStrs, minTermFreq, minDocFreq, indexReader, analyzer);
	}

	/**
	 * @MethodName: getMatchAllQuery
	 * @Description: the getMatchAllQuery
	 * @return Query
	 */
	private static Query getMatchAllQuery() {
		MatchAllDocsQuery query = new MatchAllDocsQuery();
		return query;
	}

	/**
	 * @MethodName: getMoreLikeThisQuery
	 * @Description: the getMoreLikeThisQuery
	 * @param queryStr
	 * @param minTermFreq
	 * @param minDocFreq
	 * @param indexReader
	 * @param analyzer
	 * @return Query
	 */
	private static Query getMoreLikeThisQuery(String queryStr, int minTermFreq,
			int minDocFreq, IndexReader indexReader, Analyzer analyzer) {
		MoreLikeThis mlt = new MoreLikeThis(indexReader);
		mlt.setMinTermFreq(minTermFreq);
		mlt.setMinDocFreq(minDocFreq);
		mlt.setAnalyzer(analyzer);
		Reader reader = new StringReader(queryStr);
		String fieldName = DlcConstants.DLC_CONTENT;
		mlt.setFieldNames(new String[] { fieldName });
		Query mltQuery = null;
		try {
			mltQuery = mlt.like(fieldName, reader);
		} catch (IOException e) {
			throw new DLCException("create morelikethis query error", e);
		}
		return mltQuery;
	}

	/**
	 * @MethodName: getRangeQuery
	 * @Description: the getRangeQuery
	 * @param fieldName
	 * @param start
	 * @param end
	 * @return Query
	 */
	private static Query getRangeQuery(String fieldName, long start, long end,
			final boolean minInclusive, final boolean maxInclusive) {
		NumericRangeQuery<Long> rangeQuery = NumericRangeQuery.newLongRange(
				fieldName, start, end, minInclusive, maxInclusive);
		return rangeQuery;
	}
	
	/**
	* @MethodName: getWildcardQuery
	* @Description: the getWildcardQuery
	* @param fieldName
	* @param queryStr
	* @return Query
	*/
	private static Query getWildcardQuery(String fieldName, String queryStr) {
		Term wildTerm = new Term(fieldName, queryStr);
		return new WildcardQuery(wildTerm);
	}


	/**
	* @MethodName: getBooleanQuery
	* @Description: the getBooleanQuery
	* @param queryExpression
	* @param queryStrs
	* @param minTermFreq
	* @param minDocFreq
	* @param indexReader
	* @param analyzer
	* @return Query
	*/
	private static Query getBooleanQuery(String queryExpression, String[] queryStrs, int minTermFreq, int minDocFreq,
			IndexReader indexReader, Analyzer analyzer) {
		Builder booleanQueryBuilder = new BooleanQuery.Builder();
		String queryStr;
		List<String> multiValue = new ArrayList<>();
		List<String> multiField = new ArrayList<>();
		List<BooleanClause.Occur> mutilsOccur = new ArrayList<>();
		for (int i = 0; i < queryStrs.length; i++) {
			queryStr = queryStrs[i];
			if (!queryStr.contains(DlcConstants.SYMBOY_COLON)) {
				continue;
			}
			int symboyColonIndx = queryStr.indexOf(DlcConstants.SYMBOY_COLON);
			String fieldName = queryStr.substring(0, symboyColonIndx).trim();
			String value = queryStr.substring(symboyColonIndx + 1).trim();
			boolean isBracketRange = value
					.startsWith(DlcConstants.SYMBOY_START_BRACKET)
					&& value.endsWith(DlcConstants.SYMBOY_END_BRACKET);
			boolean isBraceRange = value
					.startsWith(DlcConstants.SYMBOY_START_BRACE)
					&& value.endsWith(DlcConstants.SYMBOY_END_BRACE);
			if (isBracketRange || isBraceRange) {
				value = isBracketRange ? value
						.replace(DlcConstants.SYMBOY_START_BRACKET, "")
						.replace(DlcConstants.SYMBOY_END_BRACKET, "")
						.toUpperCase() : value
						.replace(DlcConstants.SYMBOY_START_BRACE, "")
						.replace(DlcConstants.SYMBOY_END_BRACE, "")
						.toUpperCase();
				String[] rangeValueArray = value.split(DlcConstants.CHARACTER_TO);
				String startRangeValue = rangeValueArray[0].trim();
				String endRangeValue = rangeValueArray[1].trim();
				long start = DateUtils.isDate(startRangeValue) == null ? Long.valueOf(startRangeValue)
						: DateUtils.isDate(startRangeValue).getTime();
				long end = DateUtils.isDate(endRangeValue) == null ? Long.valueOf(endRangeValue)
						: DateUtils.isDate(endRangeValue).getTime();
				Query numericRangeQuery = getRangeQuery(fieldName, start, end,
						isBracketRange ? true : false, isBracketRange ? true
								: false);
				BooleanClause booleanClause = isOrBoolean(i, queryExpression, queryStrs) ? 
						new BooleanClause(numericRangeQuery, Occur.SHOULD) : 
						new BooleanClause(numericRangeQuery, Occur.MUST);
				booleanQueryBuilder.add(booleanClause);
			} 
			else if (value.contains(DlcConstants.SYMBOY_ANY) || value.contains("?")) {
				Query wildcardQuery = getWildcardQuery(fieldName, value);
				BooleanClause booleanClause = isOrBoolean(i, queryExpression, queryStrs) ? 
						new BooleanClause(wildcardQuery, Occur.SHOULD) : 
						new BooleanClause(wildcardQuery, Occur.MUST);
				booleanQueryBuilder.add(booleanClause);
			} 
			else if (fieldName.equals(DlcConstants.DLC_CONTENT)) {
				Query moreLikeThisQuery = getMoreLikeThisQuery(queryExpression, minTermFreq, minDocFreq, indexReader, analyzer);
				BooleanClause booleanClause = isOrBoolean(i, queryStr, queryStrs) ? 
						new BooleanClause(moreLikeThisQuery, Occur.SHOULD) : 
						new BooleanClause(moreLikeThisQuery, Occur.MUST);
				booleanQueryBuilder.add(booleanClause);
			}
			else {
				multiValue.add(value);
				multiField.add(fieldName);
				Occur occur = isOrBoolean(i, queryExpression, queryStrs) ? Occur.SHOULD : Occur.MUST;
				mutilsOccur.add(occur);
			}
		}
		if (!multiField.isEmpty()) {
			Query mutiFieldQuery = null;
			try {
				mutiFieldQuery = MultiFieldQueryParser.parse(multiValue.toArray(new String[multiValue.size()]), 
						multiField.toArray(new String[multiField.size()]),
						mutilsOccur.toArray(new BooleanClause.Occur[mutilsOccur.size()]), analyzer);
			} catch (ParseException e) {
				throw new DLCException("MutiFieldQuery parse exception!", e);
			}
			booleanQueryBuilder.add(new BooleanClause(mutiFieldQuery,
					Occur.MUST));
		}
		return booleanQueryBuilder.build();
	}
	
	/**
	* @MethodName: isOrBoolean
	* @Description: the isOrBoolean
	* @param conditionIndex
	* @param queryExpression
	* @param queryStrs
	* @return boolean
	*/
	private static boolean isOrBoolean(int conditionIndex,
			String queryExpression, String[] queryStrs) {
		boolean isOrBoolean = true;
		if (conditionIndex + 1 >= queryStrs.length) {
			return false;
		}
		StringBuilder regexBuilder = new StringBuilder();
		String condition;
		String temp;
		String replaceStr;
		for (int i = 0; i <= conditionIndex; i++) {
			condition = queryStrs[i];
			temp = condition + "(\\s+AND[\\s\\S]*|\\s+and[\\s\\S]*)";
			regexBuilder.append(temp);
			int start = 0;
			int end = temp.length();
			int tempLength = temp.length();
			if (queryExpression.matches(regexBuilder.toString())) {
				replaceStr = condition + "(\\s+AND\\s+|\\s+and\\s+)";
				if (i == 0) {
					regexBuilder.replace(start, end, replaceStr);
				} else {
					end = regexBuilder.length();
					start = end - tempLength;
					regexBuilder.replace(start, end, replaceStr);
				}
				isOrBoolean = false;
			} else {
				replaceStr = condition + "(\\s+OR\\s+|\\s+or\\s+)";
				if (i == 0) {
					regexBuilder.replace(start, end, replaceStr);
				} else {
					end = regexBuilder.length();
					start = end - tempLength;
					regexBuilder.replace(start, end, replaceStr);
				}
			}
		}
		return isOrBoolean;
	}
}