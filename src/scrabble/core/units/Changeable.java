package scrabble.core.units;

public interface Changeable {
	
	/**
	 * modify the score based on different strategy
	 * @param score
	 * @return the new value;
	 */
	public int modifyScore(int score);

	
}
