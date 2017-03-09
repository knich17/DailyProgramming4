# Day 4 - The Best Conjunction
[Found at /r/dailyprogrammer](https://www.reddit.com/r/dailyprogrammer/comments/5yaiin/20170308_challenge_305_intermediate_the_best/)

## Problem Description
Your job is to find the best conjunction—that is, find the word with the most sub-words inside of it given a list of English words. Some example include:
* Something (3 words: So, me, thing)
* Awesomeness (3 words: awe, some, ness)

## Solution
The main challenge was developing an algorithm to search a word and return *the best* set of viable sub-words/conjunctions that can be found within the original word.

Originally an algorithm was created that simply searches a string by continuously increasing the subword size until a subword is or isn't found then moves onto the next subword. 

This worked poorly as if you're considering the word "sotto", if you begin with "so", you will find that there is no word or combination of words to create "tto". You must continue the search in order to get the conjunction of "sot" and "to".

The final solution is a recursive algorithm that selects the best word combination. In the "sotto" example, it would find the word "so", but continue searching and also find "sot-to", at which point it will return the latter as it contains more words. The code for this algorithm is shown below.

```java
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
```

## Learned
Refresher on Java though I used no abstract classes, also a refresher on how to design a recursive algorithm and Java string manipulation.
