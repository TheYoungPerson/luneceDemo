/**
 * 
 */
package com.l;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

/**
 * @author Administrator
 *
 */
public class LuceneTester {

	/**
	 * @param args
	 */
	
	String indexDir = "F:\\Lucene\\Index";
	String dataDir = "F:\\Lucene\\Data";
	Indexer indexer;
	Searcher searcher;
	   
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LuceneTester tester;
	    try {
	    	tester = new LuceneTester();
	        tester.createIndex();
	        tester.Search("tom");
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	}
	
	@SuppressWarnings("unused")
	private void createIndex() throws IOException{
		indexer = new Indexer(indexDir);
		
		int numIndexed;
		
		long timeStart = System.currentTimeMillis();
		
		numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
		
		long timeEnd = System.currentTimeMillis();
		
		indexer.close();
		
		System.out.println(numIndexed + " File indexed,time taken: " + (timeEnd - timeStart) + "ms");

	}
	
	@SuppressWarnings("unused")
	private void Search(String searchQuery) throws IOException, ParseException{
		searcher = new Searcher(indexDir);
		
		long timeStart = System.currentTimeMillis();
		
		TopDocs hits = searcher.Search(searchQuery);
		
		long timeEnd = System.currentTimeMillis();
		
		System.out.println(hits.totalHits + " documents found time: " + (timeStart - timeEnd) + "ms");
		
		for(ScoreDoc scoreDoc : hits.scoreDocs){
			Document document = searcher.getDocument(scoreDoc);
			
			System.out.println("File: " + document.get(LuceneConstants.FILE_PATH));
		}
		
		searcher.close();
	}

}
