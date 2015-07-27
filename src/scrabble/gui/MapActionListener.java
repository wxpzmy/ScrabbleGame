package scrabble.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import scrabble.core.game.Game;

public class MapActionListener implements ActionListener {
	
	private int x;
	private int y;
	private Game g;
	
	
	public MapActionListener(int x, int y, Game g){
		this.x = x;
		this.y = y;
		this.g = g;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
			g.mapUnitSelected(x, y);
	}

}
