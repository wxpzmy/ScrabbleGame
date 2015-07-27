package scrabble.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import scrabble.core.game.Game;

public class BrickSquareListener implements ActionListener {
	
	private Game game;
	private int index;

	public BrickSquareListener(int i, Game g) {
		this.index = i;
		this.game = g;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
			game.brickSelected(index);
	}

}
