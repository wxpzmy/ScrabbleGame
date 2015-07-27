package scrabble.core.deck;

import java.util.Set;
import scrabble.core.units.Brick;

public class DeckImpl implements Deck {
	
	private static final int SIZE =150;
	
	private final Set<Brick> pool;
	
	public DeckImpl(){
		this.pool = new DeckGenerator(SIZE).generateDeck();
	}

	@Override
	public Brick pop() {
		int number = (int) (Math.random() * pool.size());
		int count = 0;
		Brick result = null;
		for (Brick b : pool) {
			if (count == number) {
				result = b;
				break;
			}
			count++;
		}
		
		pool.remove(result);
		return result;
	}

	@Override
	public boolean isEmpty() {
		return pool.size() == 0;
	}

}
