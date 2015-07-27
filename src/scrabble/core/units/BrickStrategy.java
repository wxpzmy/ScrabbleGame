package scrabble.core.units;

public abstract class BrickStrategy implements Changeable {
	protected int add;
	protected String lab;
	
	@Override
	public int modifyScore(int score){
		int result = add + score;
		return ( result >= 0) ? result : 0;	
	}
	
}
