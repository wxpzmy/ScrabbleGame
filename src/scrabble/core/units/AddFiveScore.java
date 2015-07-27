package scrabble.core.units;

class AddFiveScore extends BrickStrategy implements Changeable {
	
	private static final int ADD = 5;
	
	protected AddFiveScore(){
		this.add = ADD;
	}

}

