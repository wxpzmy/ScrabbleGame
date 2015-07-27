package scrabble.core.units;

import scrabble.core.tileflags.Strategy;


public class PositionUnit implements Printable {

	private Brick brick;
	private final Strategy se;
	
	public PositionUnit(int x, int y, Strategy st){
		this.se = st;
	}
	
	public PositionUnit(int x, int y){
		this(x, y, Strategy.NO);
	}
	
	@Override
	public int getScore() {
		return brick.getScore();
	}
	
	public void addBrick(Brick b){
		brick = b;
	}
	
	public Strategy getStrategy(){
		return se;
	}
	
	public boolean isEmpty(){
		return this.brick == null;
	}
	
	/**
	 * Precondition: isEmpty == false
	 * @return
	 */
	@Override
	public Character getLetter(){
		return this.brick.getLetter();
	}
	
	/**
	 * return the brick's label if it is occupied
	 * else  if the strategy is NO return "  "
	 *       else return the Strategy's label
	 * @return
	 */
	@Override
	public String getUnitLabel(){
		if (this.isEmpty()){
			if(se == Strategy.NO){
				return "  ";
			}else{
				return se.toString();
			}
		}else{
			return brick.getUnitLabel();
		}
	}
}
