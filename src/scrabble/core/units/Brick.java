package scrabble.core.units;

public class Brick implements Printable {

	private final Character letter;
	private final int score;
	private final Changeable strategy;

	public Brick(Character letter, int score) {
		this.letter = letter;
		this.score = score;
		this.strategy = randomStrategy();
	}

	@Override
	public int getScore() {
		return strategy.modifyScore(score);
	}

	@Override
	public Character getLetter() {
		return this.letter;
	}

	@Override
	public String getUnitLabel() {
		return this.getLetter().toString() + "/" + this.getScore();
	}

	private Changeable randomStrategy() {
		double numAddFive = 1 / 20;
		double numAddThree = 1 / 10;
		double numMinusTwo = 1 / 15;

		double t1 = numAddFive;
		double t2 = t1 + numAddThree;
		double t3 = t2 + numMinusTwo;

		double checker = Math.random();

		if ((checker > 0) && (checker <= t1)) {
			return new AddFiveScore();
		} else if ((checker > t1) && (checker <= t2)) {
			return new AddThreeScore();
		} else if ((checker > t2) && (checker <= t3)) {
			return new MinusTwoBrick();
		} else {
			return new NormalStrategy();
		}
	}

}
