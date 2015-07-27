package scrabble.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import scrabble.core.game.Game;
import scrabble.core.game.GameChangeListener;
import scrabble.core.game.GameContext;
import scrabble.core.players.Player;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GamePane extends JPanel implements GameChangeListener {

	private int dim;
	private Game g;
	private JButton[][] grid;
	private MainFrame parent;
	private final JLabel currentInfo;
	private final JLabel playerList;
	private final int max;
	private final JButton[] brickInHand;
	private final JButton pass;
	private final JButton move;
	private final JLabel hold;

	private static final long serialVersionUID = 5263751915973705615L;
	private static final String PATH = "assets/back.jpg";

	public GamePane(MainFrame f, final GameContext gi) {

		parent = f;
		g = gi.getGame();
		max = g.getMaxNumBrick();
		dim = g.getDim();
		g.addListener(this);
		currentInfo = new JLabel();
		currentInfo.setForeground(Color.YELLOW);
		playerList = new JLabel();
		this.setLayout(new BorderLayout());

		// set the info panel
		JPanel panelTitle = new JPanel();
		panelTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1,
				true));
		panelTitle.setPreferredSize(new Dimension(600, 30));
		panelTitle.add(currentInfo);
		panelTitle.setOpaque(false);
		add(panelTitle, BorderLayout.NORTH);

		// set the player list
		JPanel panelList = new JPanel();
		panelList.setForeground(Color.RED);
		panelList.setPreferredSize(new Dimension(60, 300));
		panelList.setBorder(BorderFactory
				.createLineBorder(Color.BLACK, 1, true));
		panelList.setOpaque(false);
		panelList.add(playerList);
		add(panelList, BorderLayout.WEST);

		// set the game panel
		JPanel game = new JPanel();
		game.setLayout(new GridLayout(dim, dim));

		grid = new JButton[dim][dim];
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				grid[i][j] = new JButton("  ");
				grid[i][j].setSize(30, 30);
				grid[i][j].setText(g.getUnitLabel(i, j));
				grid[i][j].addActionListener(new MapActionListener(i, j, g));
				grid[i][j].setEnabled(false);
				grid[i][j].setBackground(Color.YELLOW);
				game.add(grid[i][j]);
			}
		}

		game.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
		game.setOpaque(false);
		add(game, BorderLayout.CENTER);

		// set the bottom panel
		JPanel bottom = new JPanel();
		JLabel instruc = new JLabel("Your Brick :");
		instruc.setForeground(Color.CYAN);
		JLabel blank = new JLabel("         ");
		move = new JButton("MOVE");
		move.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				g.move();
			}
		});

		pass = new JButton("PASS");
		pass.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				g.pass();
			}

		});
		JButton quit = new JButton("QUIT");
		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				g.endGame();
			}
		});

		hold = new JLabel("HOLD:  ");
		hold.setForeground(Color.CYAN);
		bottom.add(hold);
		bottom.add(instruc);

		brickInHand = new JButton[max];

		for (int i = 0; i < max; i++) {
			brickInHand[i] = new JButton("  ");
			brickInHand[i].setSize(30, 30);
			brickInHand[i].addActionListener(new BrickSquareListener(i, g));
			bottom.add(brickInHand[i]);
		}
		bottom.add(blank);
		bottom.add(move);
		bottom.add(pass);
		bottom.add(quit);
		bottom.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
		bottom.setOpaque(false);

		add(bottom, BorderLayout.SOUTH);

		// set log panel
		JPanel panelChat = new JPanel();
		panelChat.setOpaque(false);
		panelChat.setPreferredSize(new Dimension(75, 500));
		/**
		 * panelChat.setLayout(new BoxLayout(panelChat, BoxLayout.Y_AXIS));
		 * JLabel chatTitle = new JLabel("History");
		 * chatTitle.setForeground(Color.MAGENTA);
		 * chatTitle.setAlignmentX(CENTER_ALIGNMENT); textScreen = new
		 * JTextArea(); textScreen.setEditable(false);
		 * textScreen.setText("Game Start"); JScrollPane screen = new
		 * JScrollPane(textScreen, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		 * JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		 * screen.setPreferredSize(new Dimension(75, 500));
		 * screen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1,
		 * true)); final JTextField input = new JTextField(20);
		 * input.setEnabled(false); input.setPreferredSize(new Dimension(75,
		 * 30)); JButton submit = new JButton("CLEAR");
		 * submit.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 *           textScreen.setText("   "); }
		 * 
		 *           }); submit.setAlignmentX(CENTER_ALIGNMENT);
		 *           panelChat.add(chatTitle); panelChat.add(screen);
		 *           panelChat.add(input); panelChat.add(submit);
		 * 
		 *           panelChat.setBorder(BorderFactory
		 *           .createLineBorder(Color.BLACK, 1, true));
		 *           panelChat.setOpaque(false);
		 */
		add(panelChat, BorderLayout.EAST);

		g.gameStart();
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		ImageIcon backgroundIcon = new ImageIcon(PATH);
		Image img = backgroundIcon.getImage();
		g.drawImage(img, 0, 0, null);
	}

	@Override
	public void gameEnd() {
		if (g.isEnd()) {
			JOptionPane.showMessageDialog(null,
					"The Winner is " + g.getHighestId());
		} else {
			JOptionPane.showMessageDialog(null, "Quit, the current winner is "
					+ g.getHighestId());
		}
		parent.remove(this);
		parent.setGameLoginPane();
		parent.setTitle("Game Select");
		parent.pack();
	}

	@Override
	public void updateCurrentInfo(Player p) {
		currentInfo.setText(p.getInfo());
	}

	@Override
	public void updatePlayers(List<Player> list) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("Players:");
		sb.append("<br/>");
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i).getId());
			sb.append("<br/>");
		}
		sb.append("</html>");

		playerList.setText(sb.toString());
	}

	@Override
	public void brickActionChange(boolean isWithdraw, int index) {
		if (isWithdraw) {
			setPossibleMove();
			updateHold(brickInHand[index].getText());
			brickInHand[index].setText("  ");
		}else{
			frozeTheMap();
			brickInHand[index].setText(g.getBrickInHandLabel(index));
			updateHold("  ");
		}
	}

	private void updateHold(String text) {
		hold.setText("HOLD:" + text);
	}

	private void frozeTheMap() {
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (g.isBrickInCurrentRound(i, j)) {
					grid[i][j].setEnabled(true);
					grid[i][j].setBackground(Color.RED);
				} else {
					grid[i][j].setEnabled(false);
					grid[i][j].setBackground(Color.YELLOW);
				}
			}
		}
		pass.setEnabled(true);
		move.setEnabled(true);

		for (int i = 0; i < max; i++) {
			if (g.brickAvailable(i)) {
				brickInHand[i].setEnabled(false);
				brickInHand[i].setBackground(Color.YELLOW);
			} else {
				brickInHand[i].setEnabled(true);
				brickInHand[i].setBackground(Color.RED);
			}
		}
	}

	private void setPossibleMove() {
		pass.setEnabled(false);
		move.setEnabled(false);
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (g.isAvailableInMap(i, j)) {
					grid[i][j].setEnabled(true);
					grid[i][j].setBackground(Color.GREEN);
				} else {
					grid[i][j].setEnabled(false);
					grid[i][j].setBackground(Color.YELLOW);
				}
			}
		}
		for (int i = 0; i < max; i++) {
			if (g.brickAvailable(i)) {
				brickInHand[i].setEnabled(true);
				brickInHand[i].setBackground(Color.GREEN);
			} else {
				brickInHand[i].setEnabled(false);
				brickInHand[i].setBackground(Color.YELLOW);
			}
		}

	}

	@Override
	public void mapSquareChange(int act, int x, int y) {
		if (act > 0) {
			frozeTheMap();
			grid[x][y].setText(g.getUnitLabel(x, y));
			updateHold("  ");
		} else {
			setPossibleMove();
			updateHold(grid[x][y].getText());
			grid[x][y].setText("  ");
		}
	}

	@Override
	public void updateMove(String info, int result) {
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				grid[i][j].setText(g.getUnitLabel(i, j));
			}
		}
		for (int i = 0; i < max; i++) {
			brickInHand[i].setText(g.getBrickInHandLabel(i));
		}

		if (result < 0) {
			frozeTheMap();
		}
		if (!info.equals("NULL")) {
			JOptionPane.showMessageDialog(null, info);
		}
	}

	@Override
	public void updateNewRound() {
		for (int i = 0; i < max; i++) {
			brickInHand[i].setText(g.getBrickInHandLabel(i));
		}
		frozeTheMap();
	}

}
