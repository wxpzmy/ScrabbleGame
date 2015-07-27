package scrabble.core.units;

public interface Printable{

	/**
	 * get the score of the current brick.
	 * @return a integer of score.
	 */
	int getScore();
	
	/**
	 * return the label of the unit;
	 */
	public String getUnitLabel(); 
	
	/**
	 * 
	 * @return return the letter.
	 */
	public Character getLetter(); 
}
