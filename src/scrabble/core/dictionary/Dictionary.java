package scrabble.core.dictionary;

public interface Dictionary{
	
	/**
	 * Check the word whether is in the dictionary
	 * @param word
	 * @return true if exits
	 */
	public boolean isMatched(String word);
	
}
