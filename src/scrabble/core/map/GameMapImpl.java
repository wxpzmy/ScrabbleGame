package scrabble.core.map;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import scrabble.core.dictionary.Dictionary;
import scrabble.core.exceptions.MapModifiedException;
import scrabble.core.tileflags.Strategy;
import scrabble.core.tileflags.StrategyHandler;
import scrabble.core.units.Brick;
import scrabble.core.units.PositionUnit;

/**
 * class invariant the size of X, Y and buffer is same
 * 
 * @author wxp
 * 
 */
public class GameMapImpl implements GameMap {

	private static final int ERR_EMPTY = -1;
	private static final int ERR_NOTCENTER = -2;
	private static final int ERR_SCATTER = -3;
	private static final int ERR_LEFT = -4;
	private static final int ERR_UNMATCH = -5;
	private static final int FINE = 0;
	private static final int DIM = 15;
	private int dim;
	private PositionUnit[][] grid;
	final private ArrayList<Brick> buffer;
	final private ArrayList<Integer> X;
	final private ArrayList<Integer> Y;
	private Dictionary dic;
	private boolean isFirst;
	private int wordMutiply;
	private int wordScore;

	public GameMapImpl() {
		buffer = new ArrayList<>();
		X = new ArrayList<>();
		Y = new ArrayList<>();
		this.wordMutiply = 1;
		this.wordScore = 0;
		isFirst = true;
		generateMap();
	}

	@Override
	public int calScore() {
		return wordScore * wordMutiply;
	}

	@Override
	public void commit() {
		for (int i = 0; i < buffer.size(); i++) {
			grid[X.get(i)][Y.get(i)].addBrick(buffer.get(i));
		}
	}

	@Override
	public void setDictionary(Dictionary dic) {
		this.dic = dic;

	}

	@Override
	public void reset() {
		buffer.clear();
		X.clear();
		Y.clear();
		wordMutiply = 1;
		wordScore = 0;
	}

	@Override
	public void setNotFirst() {
		if (isFirst) {
			isFirst = false;
		}
	}

	@Override
	public void setBrick(int x, int y, Brick b) throws MapModifiedException {
		if (!(checkValid(x, y)))
			throw new MapModifiedException("The Unexpected Error when place the brick!");
		buffer.add(b);
		X.add(x);
		Y.add(y);
	}

	/**
	 * preconditions: the size of buffer, X, Y is equal.
	 * 
	 * @throws MapModifiedException
	 */

	@Override
	public int check() throws MapModifiedException {
		// defensive check the functionality
		if ((buffer.size() != X.size()) || (X.size() != Y.size()))
			throw new MapModifiedException("Unexpected error for map buffer!");

		// no placement
		if (buffer.isEmpty())
			return ERR_EMPTY;

		// check the whether the first move cover the center
		if (isFirst) {
			boolean flag = false;
			for (int i = 0; i < X.size(); i++) {
				if ((X.get(i) == dim / 2) && (Y.get(i) == dim / 2)) {
					flag = true;
				}
			}
			if (!flag)
				return ERR_NOTCENTER;
		}

		// check if the number of target larger than 1 then one of coordinate
		// should be same
		boolean xIsSame = isSame(X);
		boolean yIsSame = isSame(Y);

		if (buffer.size() > 1) {
			if (!(xIsSame || yIsSame))
				return ERR_SCATTER;
		}

		// the only possibility now must contain word
		ArrayList<String> words = new ArrayList<>();

		int size = buffer.size();

		Counter counter = new Counter();

		loadWord(words, counter, xIsSame, yIsSame);

		// check the integrity of the letter
		if (counter.count != size) {
			return ERR_LEFT;
		}

		// check whether the words is in the dictionary
		boolean flag = true;

		for (int i = 0; i < words.size(); i++) {
			if (!dic.isMatched(words.get(i))) {
				flag = false;
				break;
			}
		}

		return flag ? FINE : ERR_UNMATCH;

	}

	@Override
	public int getDim() {
		return dim;
	}

	@Override
	public String getLabel(int x, int y) {
		int index = -1;
		if ((index = isInBuffer(x, y)) != -1) {
			return buffer.get(index).getUnitLabel();
		} else {
			return grid[x][y].getUnitLabel();
		}
	}

	@Override
	public int isInBuffer(int x, int y) {
		for (int i = 0; i < buffer.size(); i++) {
			if ((X.get(i) == x) && (Y.get(i) == y)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public boolean isAvilable(int i, int j) {
		return grid[i][j].isEmpty() && (isInBuffer(i, j) < 0);
	}

	@Override
	public Brick remove(int x, int y) throws MapModifiedException {
		int index = isInBuffer(x, y);
		if ((index < 0) || (index >= buffer.size())) {
			throw new MapModifiedException("There is no buffered brick in this position");
		} else {
			Brick ans = buffer.get(index);
			buffer.remove(index);
			X.remove(index);
			Y.remove(index);
			return ans;
		}
	}

	@Override
	public String feebackError(int result) {
		switch (result) {
		case (ERR_EMPTY):
			return "No Action. Please Select Pass if you want";
		case (ERR_NOTCENTER):
			return "The first round must cross the center";
		case (ERR_SCATTER):
			return "The letters you put should be in a line";
		case (ERR_LEFT):
			return "Some letter is unused";
		case (ERR_UNMATCH):
			return "Invalid word!";
		}
		return null;
	}

	@Override
	public List<Brick> getBuffer() {
		return this.buffer;
	}

	/**
	 * The basic mode is given: The map should be squared. There are several
	 * kinds of special tiles in map: # ST: the center of the map. # DL: the
	 * score of the letter brick in that tile will be doubled. # TL: the score
	 * of the letter brick in that tile will be tripled. # DW: the score of the
	 * total word which cover that tile will be doubled. # TL: The same effect
	 * with DW except that the score value of the word will be tripled! # XX:
	 * ALL the letter brick in both direction will be removed. # NO: None #
	 * Apart from that, the dimension of the map should be odd and bigger than
	 * 9. # Actually there are three options for you in this project: 9 15 19. #
	 * There are only 4 TW tiles at each corner of the map. # (0, 0), (0,
	 * dim-1), (dim-1, 0), (dim-1, dim-1) # The ST must be in the center of the
	 * map. (dim/2, dim/2) # There are only 4 DW will be placed in # (dim/2
	 * +offset, dim/2-offset) # (dim/2 -offset, dim/2+offset) # (dim/2 -offset,
	 * dim/2-offset) # (dim/2 +offset, dim/2+offset) # where the offset is the
	 * dim/4 # The DL will fill the other diagonal position. # Then TL will be
	 * placed: # (dim/2 +offset, dim/2) # (dim/2 -offset, dim/2) # (dim/2,
	 * dim/2-offset) # (dim/2, dim/2+offset) # After that, we will pick 4 random
	 * position to set XX # At last, fill the rest of all with NO
	 * 
	 * @param mode
	 */
	private void generateMap() {

		this.dim = DIM;

		grid = new PositionUnit[dim][dim];

		// generate TW
		grid[0][0] = new PositionUnit(0, 0, Strategy.TW);
		grid[0][dim - 1] = new PositionUnit(0, dim - 1, Strategy.TW);
		grid[dim - 1][0] = new PositionUnit(dim - 1, 0, Strategy.TW);
		grid[dim - 1][dim - 1] = new PositionUnit(dim - 1, dim - 1, Strategy.TW);

		// generate ST
		grid[dim / 2][dim / 2] = new PositionUnit(dim / 2, dim / 2, Strategy.ST);

		// generate DW
		grid[dim / 2 + dim / 4][dim / 2 - dim / 4] = new PositionUnit(dim / 2 + dim / 4, dim / 2 - dim / 4,
				Strategy.DW);

		grid[dim / 2 - dim / 4][dim / 2 + dim / 4] = new PositionUnit(dim / 2 - dim / 4, dim / 2 + dim / 4,
				Strategy.DW);

		grid[dim / 2 + dim / 4][dim / 2 + dim / 4] = new PositionUnit(dim / 2 + dim / 4, dim / 2 + dim / 4,
				Strategy.DW);

		grid[dim / 2 - dim / 4][dim / 2 - dim / 4] = new PositionUnit(dim / 2 - dim / 4, dim / 2 - dim / 4,
				Strategy.DW);

		// generate TL
		grid[dim / 2 + dim / 4][dim / 2] = new PositionUnit(dim / 2 + dim / 4, dim / 2, Strategy.TL);

		grid[dim / 2 - dim / 4][dim / 2] = new PositionUnit(dim / 2 - dim / 4, dim / 2, Strategy.TL);

		grid[dim / 2][dim / 2 + dim / 4] = new PositionUnit(dim / 2, dim / 2 + dim / 4, Strategy.TL);

		grid[dim / 2][dim / 2 - dim / 4] = new PositionUnit(dim / 2, dim / 2 - dim / 4, Strategy.TL);

		// generate DL
		for (int i = 0, j = 0; i < dim; i++, j++) {
			if (grid[i][j] == null) {
				grid[i][j] = new PositionUnit(i, j, Strategy.DL);
			}
		}

		for (int i = dim - 1, j = 0; j < dim; i--, j++) {
			if (grid[i][j] == null) {
				grid[i][j] = new PositionUnit(i, j, Strategy.DL);
			}
		}

		// generate others
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (grid[i][j] == null) {
					grid[i][j] = new PositionUnit(i, j);
				}
			}
		}

	}

	/**
	 * @param x
	 *            the coordinate of x
	 * @param y
	 *            the coordinate of y
	 * @return true if the PositionUnit[x][y] is valid which means that the
	 *         position is in the map and not occupied by other brick
	 */
	private boolean checkValid(int x, int y) {
		if (!inBound(x))
			return false;
		if (!inBound(y))
			return false;
		return grid[x][y].isEmpty();
	}

	private void loadWord(ArrayList<String> words, Counter counter, boolean xIsSame, boolean yIsSame) {
		if (xIsSame) {
			scanWords(words, counter, true);
		}

		if (yIsSame) {
			scanWords(words, counter, false);
		}

	}

	private void scanWords(ArrayList<String> words, Counter counter, boolean isHorizontal) {
		Deque<Character> dq = new ArrayDeque<>();
		int x = X.get(0);
		int y = Y.get(0);
		int xStart = x;
		int yStart = y;

		Character temp = null;
		while ((temp = fetchLetter(x, y, counter, isHorizontal)) != ' ') {
			dq.addFirst(temp);
			if (isHorizontal) {
				if (!inBound(--y)) {
					break;
				}
			} else {
				if (!inBound(--x)) {
					break;
				}
			}
		}

		if (isHorizontal) {
			y = ++yStart;
		} else {
			x = ++xStart;
		}

		if (inBound(x) && (inBound(y))) {
			while ((temp = fetchLetter(x, y, counter, isHorizontal)) != ' ') {
				dq.addLast(temp);
				if (isHorizontal) {
					if (!inBound(++y)) {
						break;
					}
				} else {
					if (!inBound(++x)) {
						break;
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		while (!dq.isEmpty()) {
			sb.append(dq.pollFirst());
		}
		words.add(sb.toString());

	}

	private boolean inBound(int i) {
		return (i >= 0) && (i < dim);
	}

	/**
	 * 
	 * @param x
	 *            the position on x
	 * @param y
	 *            the position on y
	 * @param counter
	 *            the counter object which passed by reference
	 * @param xDirection
	 *            choose the direction
	 * @return the ' ' there are no character on (x, y); else return the letter
	 */
	private Character fetchLetter(int x, int y, final Counter counter, boolean xDirection) {
		int score = 0;
		Character result = null;
		if (grid[x][y].isEmpty()) {
			if (xDirection) {
				if (Y.indexOf(y) >= 0) {
					result = buffer.get(Y.indexOf(y)).getLetter();
					score = buffer.get(Y.indexOf(y)).getScore();
					counter.count++;
				}
			} else {
				if (X.indexOf(x) >= 0) {
					result = buffer.get(X.indexOf(x)).getLetter();
					score = buffer.get(Y.indexOf(y)).getScore();
					counter.count++;
				}
			}

		} else {
			result = grid[x][y].getLetter();
			score = grid[x][y].getScore();
		}

		int strategy = StrategyHandler.handle(grid[x][y].getStrategy());

		if (strategy > 0) {
			score *= strategy;
		} else {
			wordMutiply *= Math.abs(strategy);
		}

		wordScore += score;

		return (result == null) ? ' ' : result;
	}

	/**
	 * preconditions: the al should not be empty
	 * 
	 * @param al
	 * @return true if all elements in this array list is same.
	 */
	private boolean isSame(ArrayList<Integer> al) {
		if (al.size() == 1)
			return true;
		int last = al.get(0);
		for (int i = 1; i < al.size(); i++) {
			if (last != al.get(i))
				return false;
			last = al.get(i);
		}
		return true;

	}

	private class Counter {
		int count = 0;
	}

}
