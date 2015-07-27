package scrabble.core.units;

class NormalStrategy extends BrickStrategy implements Changeable{
	
	private static final int ADD = 0;

    protected NormalStrategy(){
		this.add = ADD;
	}
}
