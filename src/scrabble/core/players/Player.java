package scrabble.core.players;

import scrabble.core.deck.Deck;
import scrabble.core.exceptions.MapModifiedException;
import scrabble.core.map.GameMap;
import scrabble.core.units.Brick;

public interface Player {

	/**
	 * 
	 * @return the basic information of a player
	 */
	public String getInfo();

	/**
	 * 
	 * @return the current player's score
	 */
	public int getScore();

	/**
	 * 
	 * @return the current player's level
	 */
	public int getLevel();

	/**
	 * 
	 * @return the current player's id
	 */
	public String getId();

	/**
	 * This method put the brick in hold at specific position.
	 * 
	 * @param map
	 * @param x
	 * @param y
	 * @param b
	 * @throws MapModifiedException
	 *             This exception should not happened. If happened, crash the
	 *             program as alert.
	 */
	void layBrick(GameMap map, int x, int y) throws MapModifiedException;

	/**
	 * This function will reload the bricks to the capacity if deck is not
	 * empty. else remain same.
	 * 
	 * @param d
	 */
	void fillTheBrickInHand(Deck d);

	/**
	 * commit move
	 * 
	 * @param map
	 * @throws MapModifiedException
	 * @return result indicator;
	 */
	int move(GameMap map) throws MapModifiedException;

	/**
	 * Preconditions: the index >= 0 && inHand < inHand.length
	 * 
	 * @return the label of brick from the inHand
	 */
	String getTargetBrickLabel(int index);

	/**
	 * This method will hold the target Brick if there is one. else will placed
	 * brick hold in target place;
	 * 
	 * @return true if the action is pick from inHand false if the action is place a
	 *         brick
	 * @throws MapModifiedException
	 *             bug detected, program failed
	 */
	boolean holdBrick(int index) throws MapModifiedException;

	/**
	 * 
	 * @return the max number of brick a player can hold
	 */
	int getMaxNumOfBrick();

	/**
	 * check the availability of target index in inHand.
	 * 
	 * @param index
	 * @return true if flag[i] == false
	 */
	public boolean brickAvailable(int index);

	/**
	 * This method should set hold brick directly from the game map
	 * 
	 * @param b
	 * @throws MapModifiedException
	 *             bug detected
	 */
	public void holdBrickFromMap(Brick b) throws MapModifiedException;
}
