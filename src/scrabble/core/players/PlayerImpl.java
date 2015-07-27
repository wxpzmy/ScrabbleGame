package scrabble.core.players;

import java.util.List;

import scrabble.core.deck.Deck;
import scrabble.core.exceptions.MapModifiedException;
import scrabble.core.map.GameMap;
import scrabble.core.units.Brick;

public class PlayerImpl implements Player {

	protected final int MAX_BRICK = 8;

	protected String id;
	protected int level;
	protected int score;
	protected int max;

	protected Brick[] inHand;
	protected boolean[] flags;
	protected Brick hold;

	public PlayerImpl(String id) {
		this.max = MAX_BRICK;
		this.id = id;
		this.level = 1;
		this.score = 0;
		this.inHand = new Brick[max];
		this.flags = new boolean[max];
	}

	@Override
	public String getInfo() {
		return "Current Player: " + id + "    Level:  " + level + "    Score :  " + score;
	}

	@Override
	public int getScore() {
		return this.score;
	}

	@Override
	public int getLevel() {
		return this.level;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void layBrick(GameMap map, int x, int y) throws MapModifiedException {
		map.setBrick(x, y, hold);
		hold = null;
	}

	@Override
	public void fillTheBrickInHand(Deck d) {
		for (int i = 0; i < max; i++) {
			if (!flags[i]) {
				if (!d.isEmpty()) {
					inHand[i] = d.pop();
					flags[i] = true;
				}
			}
		}
	}

	@Override
	public int move(GameMap map) throws MapModifiedException {
		int result = map.check();
		if (result >= 0) {
			int temp = map.calScore();
			score += temp;
			updateLevel();
			map.commit();
		} else {
			List<Brick> buffer = map.getBuffer();

			for (int i = 0, j = 0; i < buffer.size(); i++) {
				while (flags[j])
					j++;
				inHand[j] = buffer.get(i);
				flags[j] = true;
			}
		}

		map.reset();

		return result;
	}

	@Override
	public String getTargetBrickLabel(int i) {
		if (flags[i]) {
			return inHand[i].getUnitLabel();
		} else {
			return "  ";
		}
	}

	@Override
	public int getMaxNumOfBrick() {
		return max;
	}

	@Override
	public boolean holdBrick(int index) throws MapModifiedException {
		if (flags[index]) {
			hold = inHand[index];
			flags[index] = false;
			inHand[index] = null;
			return true;
		} else {
			if (hold != null) {
				inHand[index] = hold;
			} else {
				throw new MapModifiedException("the current hold is null!");
			}
			flags[index] = true;
			hold = null;
			return false;
		}
	}

	@Override
	public boolean brickAvailable(int index) {
		return !flags[index];
	}

	@Override
	public void holdBrickFromMap(Brick b) throws MapModifiedException {
		if (hold != null) {
			throw new MapModifiedException("Hold a brick failed, the hold now should be null");
		} else {
			hold = b;
		}
	}

	protected void updateLevel() {
		if (score < 20) {
			level = 1;
		}
		if (score >= 20) {
			level = 2;
		}
		if (score >= 60) {
			level = 3;
		}
		if (score >= 120) {
			level = 4;
		}
		if (score >= 200) {
			level = 5;
		}
	}
}
