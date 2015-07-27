package scrabble.core.dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class DictionaryImpl implements Dictionary {
	
	private static final String PATH = "assets/words.txt"; 
	private final Set<String> dic;

	public DictionaryImpl() {
		dic = new HashSet<>();
		loadWords();
	}

	private void loadWords() {
		try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
			String line = br.readLine();
			while (line != null) {
				this.dic.add(line);
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public boolean isMatched(String word) {
		if(word == null) return false;
		return dic.contains(word);
	}

	/**
	 * Just for the test public int size(){ return this.dic.size(); }
	 */
}
