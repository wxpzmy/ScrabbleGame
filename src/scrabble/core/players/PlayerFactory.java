package scrabble.core.players;

public class PlayerFactory {
	
	public static Player generatePlayer(String id){
		return new PlayerImpl(id);
	}
	
}
