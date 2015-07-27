package scrabble.core.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import scrabble.core.deck.Deck;
import scrabble.core.deck.DeckImpl;
import scrabble.core.dictionary.Dictionary;
import scrabble.core.dictionary.DictionaryImpl;
import scrabble.core.exceptions.MapModifiedException;
import scrabble.core.map.GameMap;
import scrabble.core.map.GameMapImpl;
import scrabble.core.players.Player;
import scrabble.core.players.PlayerFactory;

public class GameImpl implements Game {

	protected static final int MAX_BRICK = 8;
	protected static final int PASS_THRESHOLD = 2;
	protected final List<GameChangeListener> listeners;
	protected final List<Player> players;
	protected Player current;
	protected final GameMap map;
	protected final Dictionary dic;
	protected final Deck deck;
	protected int pass;

	public GameImpl(List<String> playerids) {
		listeners = new ArrayList<>();
		players = new ArrayList<>();
		for (int i = 0; i < playerids.size(); i++) {
			players.add(PlayerFactory.generatePlayer(playerids.get(i)));		
		}
		map = new GameMapImpl();
		dic = new DictionaryImpl();
		map.setDictionary(dic);
		deck = new DeckImpl();
		pass = 0;
	}

	@Override
	public void addListener(GameChangeListener l) {
		this.listeners.add(l);
	}

	@Override
	public void gameStart() {
		if (!players.isEmpty()) {
			current = players.get(0);
			notifyChangePlayer();
			notifyUpdateList();
			startNewRound();
		}else{
			System.exit(-1);
		}
	}

	@Override
	public void move(){
		String info = null;
		int moveReturn = 0;
		
		try {
			moveReturn = current.move(map);
		} catch (MapModifiedException e) {
			System.exit(-1);
			e.printStackTrace();
		}
		
		if (moveReturn >= 0) {
			info = "Success!";
			changePlayer();
			startNewRound();
			if (pass > 0)
				pass--;
		} else {
			info = map.feebackError(moveReturn);
		}
		
		notifyMove(info, moveReturn);
	}

	@Override
	public void pass(){
		if (isEnd()) {
			endGame();
			return;
		}
		try {
			current.move(map);
		} catch (MapModifiedException e) {
			System.exit(-1);
			e.printStackTrace();
		}
		
		notifyMove("NULL", -1);
		changePlayer();
		startNewRound();
		pass++;
	}

	@Override
	public void endGame() {
		notifyEndGame();
	}

	@Override
	public boolean isEnd() {
		return pass >= (players.size() * PASS_THRESHOLD);
	}

	@Override
	public int getDim() {
		return this.map.getDim();
	}

	@Override
	public String getUnitLabel(int x, int y) {
		return this.map.getLabel(x, y);
	}

	@Override
	public String getBrickInHandLabel(int i) {
		return this.current.getTargetBrickLabel(i);
	}

	@Override
	public void brickSelected(int index){
		boolean result = true;
		try {
			result = current.holdBrick(index);
		} catch (MapModifiedException e) {
			System.exit(-1);
			e.printStackTrace();
		}
		notifyBrickAction(result, index);
	}

	@Override
	public void mapUnitSelected(int x, int y){
		int act = 0;
		if (map.isAvilable(x, y)) {
			try {
				current.layBrick(map, x, y);
			} catch (MapModifiedException e) {
				System.exit(-1);
				e.printStackTrace();
			}
			act = 1;
		} else {
			try {
				current.holdBrickFromMap(map.remove(x, y));
			} catch (MapModifiedException e) {
				System.exit(-1);
				e.printStackTrace();
			}
			act = -1;
		}
		notifyMapSelection(act, x, y);
	}

	@Override
	public boolean isAvailableInMap(int i, int j) {
		return map.isAvilable(i, j);
	}

	@Override
	public int getMaxNumBrick() {
		return MAX_BRICK;
	}

	@Override
	public boolean brickAvailable(int index) {
		return current.brickAvailable(index);
	}

	@Override
	public boolean isBrickInCurrentRound(int i, int j) {
		return map.isInBuffer(i, j) >= 0;
	}

	@Override
	public String getHighestId() {
		String names = "";
		Queue<Player> players = getHighestPlayer();
		while (!players.isEmpty()) {
			names += players.poll().getId();
			names += "  ";
		}
		return names;
	}

	protected void startNewRound() {
		current.fillTheBrickInHand(deck);
		notifyNewRound();
	}

	protected void changePlayer() {
		int index = players.indexOf(current);
		index = (index + 1) % players.size();
		current = players.get(index);
		map.setNotFirst();
		notifyChangePlayer();
	}

	protected Queue<Player> getHighestPlayer() {
		int max = 0;
		Queue<Player> result = new LinkedList<>();
		for (int i = 0; i < players.size(); i++) {
			int temp = players.get(i).getScore();
			if (temp >= max) {
				if (temp > max) {
					max = temp;
					while (!result.isEmpty()) {
						result.poll();
					}
				}
				result.add(players.get(i));
			}
		}
		return result;
	}

	protected void notifyUpdateList() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).updatePlayers(players);
		}
	}

	protected void notifyNewRound() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).updateNewRound();
		}
	}

	protected void notifyMove(String info, int result) {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).updateMove(info, result);
		}
	}

	protected void notifyEndGame() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).gameEnd();
		}
	}

	protected void notifyChangePlayer() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).updateCurrentInfo(this.current);
		}
	}

	protected void notifyBrickAction(boolean result, int index) {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).brickActionChange(result, index);
		}
	}

	protected void notifyMapSelection(int act, int x, int y) {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).mapSquareChange(act, x, y);
		}
	}


}
