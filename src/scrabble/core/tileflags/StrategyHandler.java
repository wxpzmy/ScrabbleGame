package scrabble.core.tileflags;


public class StrategyHandler {
	
	public static int handle(Strategy se){
		if (se == null){
			return -1;
		}
		
		if (se.equals(Strategy.DL)){
			return 2;
		}
		
		if (se.equals(Strategy.TL)){
			return 3;
		}
		
		if (se.equals(Strategy.DW)){
			return -2;
		}
		
		if (se.equals(Strategy.TW)){
			return -3;
		}
		
		if ((se.equals(Strategy.ST))||(se.equals(Strategy.NO))){
			return 1;
		}
		
		return -1;
		
	}
}
