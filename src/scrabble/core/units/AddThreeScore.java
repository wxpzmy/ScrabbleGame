package scrabble.core.units;

class AddThreeScore extends BrickStrategy implements Changeable{
	
	private static final int ADD = 3;
	
	protected AddThreeScore(){
		this.add = ADD;
	}
}
