package scrabble.core.deck;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import scrabble.core.units.Brick;

import java.util.Set;

/**
 * This Class generate a Deck based on the input size and statistical result
 * from dictionary.
 * 
 * @author wxp
 * 
 */
class DeckGenerator {

	private final Map<Character, Integer> statmap;
	private final Map<Character, Integer> numberMap;
	private final Map<Character, Integer> valueMap;
	
	private Set<Brick> set;

	private static final String dicPath = "assets/words.txt";
	private static final int OFFSET = 2;

	private final int size;

	public DeckGenerator(int size) {
		this.size = size;
		statmap = new HashMap<>();
		numberMap = new HashMap<>();
		valueMap = new HashMap<>();
		set = new HashSet<>();
		generateSet();
	}

	private void generateSet() {

		try (BufferedReader br = new BufferedReader(new FileReader(dicPath))) {
			String line = br.readLine();
			while (line != null) {
				generateStatMap(line);
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		generateNumMap();
		generateScoreMap();

		makeDeck();
	}

	/**
	 * make a deck based on the map generated.
	 */
	private void makeDeck() {
		for(Entry<Character, Integer> entry : valueMap.entrySet()){
			Character temp = entry.getKey();
			int val = (int) entry.getValue();
			int number = numberMap.get(temp); 
			for(int i = 0; i < number; i++){
				set.add(new Brick(temp, val));
			}	
		}
	}

	/**
	 * Generate a score map which is used for generate brick.
	 */
	private void generateScoreMap() {
		int base = statmap.get('e');
		int score = 0;

		for (Entry<Character, Integer> e : statmap.entrySet()) {
			int temp = (int) (1.0 / (1.0 * (int) (e.getValue()) / base));
			if (temp >= 30) {
				score = 5;
			} else if ((temp < 30) && (temp >= 10)) {
				score = 4;
			} else if ((temp >= 5) && (temp < 10)) {
				score = 3;
			} else if ((temp > 1) && (temp < 5)) {
				score = 2;
			} else {
				score = 1;
			}
			valueMap.put(e.getKey(), new Integer(score));
		}

	}

	/**
	 * Generate the number of brick we produce for each kind of character
	 */
	private void generateNumMap() {
		int sum = 0;
		int sumAfter = 0;

		for (Entry<Character, Integer> e : statmap.entrySet()) {
			sum += (int) e.getValue();
		}

		for (Entry<Character, Integer> e : statmap.entrySet()) {
			int temp = (int) (1.0 * (int) (e.getValue()) / sum
					* (size - OFFSET * 26) + OFFSET);
			sumAfter += temp;
			numberMap.put(e.getKey(), new Integer(temp));
		}

		int left = size - sumAfter;

		Integer addon = left / 5;
		Integer blank = left - addon * 5;

		char[] aeiou = { 'a', 'e', 'i', 'o', 'u' };

		for (int i = 0; i < aeiou.length; i++) {
			numberMap.put(aeiou[i], numberMap.get(aeiou[i]) + addon);
		}
		numberMap.put('a',numberMap.get('a') + blank);
	}

	/**
	 * generate character count for each character in dictionary The key in the
	 * map is the character and value is the total number it appeared
	 * 
	 * @param line
	 */
	private void generateStatMap(String line) {
		char[] ca = line.toCharArray();
		for (int i = 0; i < ca.length; i++) {
			Character temp = (Character) ca[i];
			if (Character.isLetter(temp)) {
				if (statmap.containsKey(temp)) {
					statmap.put(temp, statmap.get(temp) + 1);
				} else {
					statmap.put(temp, 1);
				}
			}
		}

	}

	public Set<Brick> generateDeck() {
		return this.set;
	}

}
