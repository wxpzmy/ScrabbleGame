package scrabble.core.deck;

import scrabble.core.units.Brick;

public interface Deck {

	/**
	 * This method withdraw a random brick in deck.
	 * @preconditions: the deck is not empty.
	 * @return the brick selected.
	 */
	public Brick pop();
	
	/**
	 * This method check whether the deck is empty.
	 * @return true if it is empty
	 */
	public boolean isEmpty();
}
