package scrabble.core.game;

import scrabble.core.exceptions.MapModifiedException;

public interface Game {

	/**
	 * 
	 * @return the dimension of map in this game;
	 */
	int getDim();

	/**
	 * check the game is end the check condition is that the number of pass
	 * round is not smaller than the multiplication between the size of players
	 * and threshold value.
	 * 
	 * @return true if the game is end
	 */
	boolean isEnd();

	/**
	 * if the unit is covered by brick then return the label of brick else if
	 * the unit has special strategy then return the Name of Strategy else
	 * return the "  "
	 * 
	 * @param x
	 * @param y
	 * @return the label
	 */
	String getUnitLabel(int x, int y);

	/**
	 * this method will return the target brick's label
	 * 
	 * @param i
	 * @return the target of label
	 */
	String getBrickInHandLabel(int i);

	/**
	 * this method the current player will hold the target brick Preconditions
	 * the index should be smaller the max number of the brick
	 * 
	 * @param index
	 * @throws MapModifiedException if bug detected.
	 */
	void brickSelected(int index);

	/**
	 * this method will handle the select of a map unit
	 * 
	 * @param x
	 * @param y
	 * @throws MapModifiedException if bug detected
	 */
	void mapUnitSelected(int x, int y);

	/**
	 * The current player in game will pass this round.
	 * 
	 * @throws MapModifiedException
	 */
	void pass();

	/**
	 * The current player will commit this round
	 * 
	 * @throws MapModifiedException
	 *            
	 */
	void move();

	/**
	 * 
	 * @return the ids of the players with highest score.
	 */
	String getHighestId();

	/**
	 * This method will end the game
	 */
	void endGame();

	/**
	 * check the target position if available in map
	 * 
	 * @param i
	 * @param j
	 * @return true if available
	 */
	boolean isAvailableInMap(int i, int j);

	/**
	 * this method return the max number of the bricks in hand
	 * 
	 * @return num
	 */
	int getMaxNumBrick();

	/**
	 * check the target brick in inHands if available
	 * 
	 * @param index
	 * @return true if available
	 */
	boolean brickAvailable(int index);

	/**
	 * Check the target unit if placed in this round
	 * 
	 * @param i
	 * @param j
	 * @return true if target brick is placed in this round
	 */
	boolean isBrickInCurrentRound(int i, int j);

	/**
	 * register listener for game
	 * 
	 * @param l
	 */
	void addListener(GameChangeListener l);

	/**
	 * Precondition: the playerlist is not empty
	 * start a new game; set the current player.
	 */
	void gameStart();

}
