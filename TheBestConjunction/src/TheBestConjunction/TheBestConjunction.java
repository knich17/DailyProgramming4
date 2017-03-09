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
 * @author Kane
 *
 */
public class TheBestConjunction {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HashSet<String> wordList = new HashSet<String>();
		
		//read and store word list
		try(BufferedReader br = new BufferedReader(new FileReader("wordlist.txt"))) {
		    String line = br.readLine();

		    while (line != null) {
		    	wordList.add(line.replace("\r\n",""));
		        line = br.readLine();
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//for every word in the word list
		for(int i = 1; true; i++) {
			int bestWords = 0;
			String bestWord = "";
			int minLength = i;
			//loop through the word, starting and the minimum subword length and increasing until subword is base word kinda thing
			for(String word : wordList) {
				String wordString = i + " - " + word + " - (";
				int position = 0;
				int currLength = minLength;
				int words = 0;
				while(currLength < word.length() && position+currLength <= word.length() && position < word.length()) {
					if (wordList.contains(word.substring(position, position+currLength))) {
						if (words == 0) {
							wordString += word.substring(position, position+currLength);
						} else {
							wordString += ", " + word.substring(position, position+currLength);
						}
						words++;
						position += currLength; 
						currLength = minLength;
					} else {
						currLength++;
					}
				}
				if(words > bestWords) {
					bestWords = words;
					bestWord = wordString + ") - numWords=" + words + "\n";
				}
			}
			if(bestWord == "") break;
			System.out.print(bestWord);
		}
	}

}
