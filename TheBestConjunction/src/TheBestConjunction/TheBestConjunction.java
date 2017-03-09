/**
 * 
 */
package TheBestConjunction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author Kane Nicholson
 */
public class TheBestConjunction {
	static HashSet<String> wordList;
	private static final int STARTING_MINSIZE = 1;
	private static final char SEPERATOR = ' ';

	/**
	 * Finds and prints the best conjunction combinations for each minSize 
	 * until no words can be found that contain viable subwords.
	 * @param args N/A
	 */
	public static void main(String[] args) {
		wordList = getWordList("wordlist.txt");

		for(int i = STARTING_MINSIZE; true; i++) {		//for every min Length
			int bestWords = 0;
			String bestWord = "";
			int minLength = i;
			for(String word : wordList) {	//for every word in the list
				//replace bestword if current word contains more conjunctions
				String subWord = findSubword(word, minLength);
				int subWords = charCount(subWord, SEPERATOR);
				if (subWords > bestWords) {
					//cleanup string (remove trailing space, convert space seperators to ", ", add original word etc
					subWord = subWord.substring(0, subWord.length()-1);
					subWord = subWord.replace(SEPERATOR + "", ", ");
					bestWords = subWords;
					bestWord = word + " (" + bestWords + ": " + subWord + ")";
				}
			}
			
			//print bestWord if it's not blank, then break if the word had no conjunctions
			if(bestWord == "") break;
			System.out.print("minSize " + minLength + ": " + bestWord + "\n");
			if(bestWords == 1) break;
		}
	}
	
	/**
	 * Recursive algorithm to find best combination of subwords/conjunctions
	 * @param word = Word to find conjunctions of
	 * @param minLength = Minimum length of subword/conjunction	
	 * @return = A String of the original word but with a space after each subword/conjunction (including at the end)
	 */
	public static String findSubword(String word, int minLength) {
		if (word.length() < minLength) return null;	//no subwords if string is too short
		
		//Create array to store potential conjunctions of this word, array index corresponds with the end of the first conjunction
		String[] subwords = new String[word.length()+1];
		//loops from the min length to just past the end of the word, i = conjunction end index
		for(int i = minLength; i < word.length()+1; i++) {
			//If the word at the current length is a real word, add it, then it's conjunctions (recursively, seperated by spaces), to the array
			if(wordList.contains(word.substring(0, i))) {
				subwords[i] = word.substring(0, i) + SEPERATOR;
				String subword = findSubword(word.substring(i, word.length()), minLength);
				if(subword != null) subwords[i] += subword;
			}
		}
		
		//Find the best word in the array (the one with the most words/spaces/seperators)
		int bestIndex = 0;
		int bestWords = 0;
		for(int i = minLength; i < word.length()+1; i++) {
			if(subwords[i] == null) continue;
			int wordCount = charCount(subwords[i], SEPERATOR);
			if(wordCount >= bestWords) {
				bestWords = wordCount;
				bestIndex = i;
			}
		}
		
		//return the best word from the array (will be NULL if word has no conjunctions as default best index's element will be null)
		return subwords[bestIndex];
	}
	
	//Returns an int representing the number of char seperators in a given string
	public static int charCount(String word, char seperator) {
		if(word == null) return 0;
		int wordCount = 0;
		for(int j = 0; j < word.length(); j++) {
			if(word.charAt(j) == seperator) wordCount++;
		}
		return wordCount;
	}
	
	//Returns a String HashSet containing every non-empty line in the given file
	public static HashSet<String> getWordList(String fileName) {
		HashSet<String> wordList = new HashSet<String>();
		
		//read and store word list
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
		    String line = br.readLine();

		    while (line != null) {
		    	if (!line.isEmpty()) wordList.add(line.replace("\r\n",""));
		        line = br.readLine();
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return wordList;
	}

}
