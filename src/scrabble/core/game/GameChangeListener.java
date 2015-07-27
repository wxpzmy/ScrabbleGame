package scrabble.core.game;

import java.util.List;

import scrabble.core.players.Player;

public interface GameChangeListener {

	/**
	 * Called when the game end
	 * 
	 */
	public void gameEnd();

	/**
	 * update the current player's Info
	 * 
	 * @param p
	 */
	public void updateCurrentInfo(Player p);

	/**
	 * update the players list
	 * 
	 * @param list
	 */
	public void updatePlayers(List<Player> list);

	/**
	 * 
	 * @param result
	 *            the type of action the player make, 0 if withdraw and -1 if
	 *            placement
	 * @param index
	 *            the index of the target brick
	 */
	public void brickActionChange(boolean result, int index);

	/**
	 * 
	 * @param act
	 *            if act > 0 then do the placement if act < 0 do the withdraw
	 * @param x
	 * @param y
	 */
	public void mapSquareChange(int act, int x, int y);

	/**
	 * update the Movement
	 * 
	 * @param info
	 */
	public void updateMove(String info, int result);

	/**
	 * update the gui for next round
	 */
	public void updateNewRound();

}
