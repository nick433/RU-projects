package search;

import java.io.*;
import java.util.*;

/**
 * This class encapsulates an occurrence of a keyword in a document. It stores the
 * document name, and the frequency of occurrence in that document. Occurrences are
 * associated with keywords in an index hash table.
 * 
 * @author Sesh Venugopal
 * 
 */
class Occurrence {
	/**
	 * Document in which a keyword occurs.
	 */
	String document;
	
	/**
	 * The frequency (number of times) the keyword occurs in the above document.
	 */
	int frequency;
	
	/**
	 * Initializes this occurrence with the given document,frequency pair.
	 * 
	 * @param doc Document name
	 * @param freq Frequency
	 */
	public Occurrence(String doc, int freq) {
		document = doc;
		frequency = freq;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "(" + document + "," + frequency + ")";
	}
}

/**
 * This class builds an index of keywords. Each keyword maps to a set of documents in
 * which it occurs, with frequency of occurrence in each document. Once the index is built,
 * the documents can searched on for keywords.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in descending
	 * order of occurrence frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash table of all noise words - mapping is from word to itself.
	 */
	HashMap<String,String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashMap<String,String>(100,2.0f);
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.put(word,word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeyWords(docFile);
			mergeKeyWords(kws);  //check if empty and skip
		}
		
	}

	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeyWords(String docFile) 
	throws FileNotFoundException {
		HashMap<String,Occurrence> h = new HashMap<String,Occurrence>();
		// Occurrence o = new Occurrence();
		Scanner f = new Scanner(new File(docFile));
		while(f.hasNextLine()){
			StringTokenizer s = new StringTokenizer(f.nextLine()); 
			while(s.hasMoreTokens()){
				String st = s.nextToken();
				String str = getKeyWord(st);					
				if(str == null){ }
				else{ 
					if(h.containsKey(str))
						h.get(str).frequency++;
					else h.put(str, new Occurrence(docFile, 1));
															
				}
				
			}

		}
		
		
		
		return h;
	}    //    System.out.println();
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeyWords(HashMap<String,Occurrence> kws) {
		if(kws.isEmpty()){ return; }
		for (String key : kws.keySet()) {
			if(keywordsIndex.containsKey(key)){
				keywordsIndex.get(key).add(kws.get(key)); 
				insertLastOccurrence(keywordsIndex.get(key));  
			}
			else{
				ArrayList<Occurrence> balls = new ArrayList<Occurrence>();


				balls.add(kws.get(key));
				keywordsIndex.put(key, balls);
				insertLastOccurrence(keywordsIndex.get(key));
			}
			
		}
	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * TRAILING punctuation, consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyWord(String word) {
		
		word = word.toLowerCase();
		int size = word.length();
		if(size == 1) return null;
		if(noiseWords.containsKey(word)){   //unnecessary 
				return null;
		}
		while(Character.isLetter(word.charAt(size-1)) == false){	
			if(Character.isDigit(word.charAt(size-1))){
				return null;
			}
			else if(word.charAt(size-1) == '.' || word.charAt(size-1) == ',' || word.charAt(size-1) == '?'
			|| word.charAt(size-1) == ':' || word.charAt(size-1) == ';' || word.charAt(size-1) == '!' ){
				word = word.substring(0, size-1);
				size = word.length(); //reassign size because it changed
			}	
			else{ return null; }
		}
		int i = 0;
		while(i<word.length()){
			if(Character.isLetter(word.charAt(i)) == false){
				return null;
			}
			i++;
		}
		if(noiseWords.containsKey(word)){
			return null;
	}		
		return word;
	}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * same list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion of the last element
	 * (the one at index n-1) is done by first finding the correct spot using binary search, 
	 * then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {

		if(occs.size() == 1) return null;
		
		ArrayList<Integer> A = new ArrayList<Integer>();
		Occurrence hold = occs.get(occs.size()-1);
		int hi = occs.size() -2;
		int lo = 0;
		int mid = (lo+hi)/2;
		occs.remove(occs.size()-1);
		if(occs.size()==1){
			A.add(0);
			if(hold.frequency > occs.get(0).frequency){
				occs.add(0,hold);
			}
			else{ occs.add(1,hold); }
			return A;
		}
		if(occs.size() == 2 && occs.get(1).frequency < hold.frequency && occs.get(0).frequency > hold.frequency){
			occs.add(1,hold);
			A.add(0);
			return A;

		}
		while(true){
			
			A.add(mid);
			if(hi == 0 && occs.size() == 2){
				A.add(0,1);
				if(hold.frequency >= occs.get(0).frequency){
					occs.add(0,hold);
					return A;
				}
				else{ occs.add(1,hold); 
				return A; }
			}
			
			if(hi == lo || mid == 0 || lo == mid && occs.size() > 1){ //may eed to gt rid of lo == mid
				if(lo == hi){
					if(occs.get(lo).frequency < hold.frequency || occs.get(lo).frequency < hold.frequency){
						occs.add(lo,hold);
					}
					else { occs.add(lo+1,hold); }
					return A;
				}
				else if(occs.get(0).frequency < hold.frequency){
					occs.add(0,hold);
					return A;	
				}	
				else if(occs.get(mid).frequency < hold.frequency){
					occs.add(mid,hold); return A;
				}
				else if(occs.get(mid).frequency < hold.frequency){
					occs.add(mid,hold); return A;
				}
			}
			
			if(hold.frequency == occs.get(mid).frequency){
				occs.add(mid,hold);
				return A;
			}
			if(hold.frequency > occs.get(mid).frequency){
				hi = mid -1;
				mid = (lo+hi)/2;
			} else{ lo = mid+1;;
				mid = (lo+hi)/2;
				
			}
		
		}	
	} //return a list of the indexes visited with search
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of occurrence frequencies. (Note that a
	 * matching document will only appear once in the result.) Ties in frequency values are broken
	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
	 * also with the same frequency f1, then doc1 will appear before doc2 in the result. 
	 * The result set is limited to 5 entries. If there are no matching documents, the result is null.
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of NAMES of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matching documents,
	 *         the result is null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
	 
        ArrayList<String> result = new ArrayList<String>(5);
        ArrayList<Occurrence> key = new ArrayList<Occurrence>();
        ArrayList<Occurrence> key2 = new ArrayList<Occurrence>();
        key = keywordsIndex.get(kw1);
        key2 = keywordsIndex.get(kw2);
       
        if ((key == null && key2 == null)){
        	return null;
        }       
        Occurrence O;
        if(key != null && key2 != null){
	        while (!key.isEmpty() && !key2.isEmpty() && result.size() < 5) {
	        		int freq = 0;
	        		int q = 0;
	        		while (q < key.size()) {
	        			if (freq < key.get(q).frequency) freq = key.get(q).frequency;
	        			q++;
	        		}
	        		q = 0;
	        		while (q < key2.size()) {
	        			if (freq < key2.get(q).frequency) 
	        				freq = key2.get(q).frequency;
	        			q++;
	        		}
	        		int i = 0;
	    		    while(i < key.size()) {
	                        O = key.get(i);
	                        if (!result.contains(O.document) && O.frequency == freq) {
	                                result.add(O.document);
	                                key.remove(i);
	                        } else if (O.frequency == freq) {
	                                key.remove(i);
	                        }
	                        i++;
	                }
	    		    int j = 0;
	                while(j < key2.size()) {
	                        O = key2.get(j);
	                        if (!result.contains(O.document) && O.frequency == freq) {
	                                result.add(O.document);
	                                key2.remove(j);
	                        } else if (O.frequency == freq) {
	                                key2.remove(j);
	                        }
	                        j++;
	                }
	        }
	        return result;
        }
        if(key != null){
        	 while (!key.isEmpty() && result.size() < 5) {
	        		int freq = 0;
	        		int q = 0;
	        		while (q < key.size()) {
	        			if (freq < key.get(q).frequency) freq = key.get(q).frequency;
	        			q++;
	        		}
	        		int n = 0;
	    		    while(n < key.size()) {
	                        O = key.get(n);
	                        if (!result.contains(O.document) && O.frequency == freq) {
	                                result.add(O.document);
	                                key.remove(n);
	                        }else if (O.frequency == freq) {
	                                key.remove(n);
	                        }
	                        n++;
	                }
        	 }  
	     return result;	    
        }
        if(key2 != null){
        	 while (!key2.isEmpty() && result.size() < 5) {
	        		int freq = 0;
	        		int q = 0;
	        		while (q < key2.size()) {
	        			if (freq < key2.get(q).frequency) 
	        				freq = key2.get(q).frequency;
	        			q++;
	        		}
	        		int k = 0;
	                while(k < key2.size()) {
	                        O = key2.get(k);
	                        if (!result.contains(O.document) && O.frequency == freq) {
	                                result.add(O.document);
	                                key2.remove(k);
	                        } else if (O.frequency == freq) {
	                                key2.remove(k);
	                        }
	                        k++;
	                }
        	 }
        }
        return result;
        }
	       
}