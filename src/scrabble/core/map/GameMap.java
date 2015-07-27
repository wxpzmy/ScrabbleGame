package scrabble.core.map;

import java.util.List;

import scrabble.core.dictionary.Dictionary;
import scrabble.core.exceptions.MapModifiedException;
import scrabble.core.units.Brick;

public interface GameMap {
	/**
	 * This method will place the brick in buffer.
	 * 
	 * @param x
	 * @param y
	 * @param b Brick selected
	 * @throws MapModifiedException
	 *             This exception shouldn't happen. if happened, the program
	 *             should crash
	 */
	public void setBrick(int x, int y, Brick b)
			throws MapModifiedException;

	/**
	 * preconditions: the dic must be set.
	 * 
	 * @return true if new bricks add on the map is valid.
	 * @throws MapModifiedException
	 *             This exception should crash the program.
	 */
	public int check() throws MapModifiedException;

	/**
	 * 
	 * @return the Score in this round.
	 */
	public int calScore();

	/**
	 * pop the brick from buffer to the map
	 */
	public void commit();

	/**
	 * set the isFirst to false;
	 */
	public void setNotFirst();

	/**
	 * set the dictionary for checking.
	 * 
	 * @param dic
	 */
	public void setDictionary(Dictionary dic);

	/**
	 * return the dimension of the map
	 * 
	 * @return
	 */
	public int getDim();

	/**
	 * 
	 * @param x
	 * @param y
	 * @return the label in gird[x][y]
	 */
	public String getLabel(int x, int y);

	/**
	 * check the position in map if available
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isAvilable(int i, int j);

	/**
	 * 
	 * @param x
	 * @param y
	 * @return the index of the Brick searched if exited. else return -1
	 */

	int isInBuffer(int x, int y);

	/**
	 * This method should find a brick in buffer
	 * 
	 * @param x
	 * @param y
	 * @return the target brick founded
	 * @throws MapModifiedException
	 *             This method should find the brick else bug detected.
	 */
	public Brick remove(int x, int y) throws MapModifiedException;

	/**
	 * clear bricks in buffer
	 */
	public void reset();

	/**
	 * return the information about the error
	 * 
	 * @param result
	 * @return
	 */
	public String feebackError(int result);

	/**
	 * 
	 * @return buffer of the map
	 */
	public List<Brick> getBuffer();
}
