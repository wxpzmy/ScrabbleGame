package scrabble.core.game;

import java.util.ArrayList;
import java.util.List;

public class GameContext {

	private final List<String> players;

	public GameContext() {
		this.players = new ArrayList<>();
	}

	public void addID(String id) {
		players.add(id);
	}

	public Game getGame() {
		Game g =  new GameImpl(players);
		players.clear();
		return g;
	}

}
