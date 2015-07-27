package scrabble.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import scrabble.core.game.GameContext;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 3477805278617434321L;
	LoginPanel lp;
	GameLoginPane glp;

	public MainFrame() {
		GameContext cxt = new GameContext();
		lp = new LoginPanel(this, cxt);
		glp = new GameLoginPane(this, cxt);
	}

	public void setGameLoginPane() {
		this.add(glp);
	}

	public void setLoginPanel() {
		this.add(lp);
	}

	public void setGamePane(GamePane gp) {
		this.add(gp);
	}

	public void go() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainFrame frame = new MainFrame();
				frame.setLoginPanel();
				frame.setResizable(false);
				frame.setTitle("Login Panel");
				frame.pack();
				frame.setVisible(true);
				frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			}

		});
	}

}
