package scrabble.core.units;

class MinusTwoBrick extends BrickStrategy implements Changeable {

	private static final int ADD = -2;

	protected MinusTwoBrick() {
		this.add = ADD;
	}
}
