package scrabble.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import scrabble.core.game.GameContext;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class GameLoginPane extends JPanel {

	private static final long serialVersionUID = -6207042707915995890L;
	private static final String INSTRUCTION = "Please select which "
			+ "kind of game you want to play.";
	private static final String[] LABELS = { "Add more", "No, thanks" };
	private final JButton m1;
	private final JButton m2;
	private final JButton m3;
	private JLabel instruction;
	private MainFrame parent;
	private GameContext cxt;
	private final String PATH = "assets/Scrabble-Game-5.jpg";

	public GameLoginPane(MainFrame p, final GameContext cxt) {

		setForeground(new Color(153, 0, 204));
		parent = p;
		this.cxt = cxt;
		m1 = new JButton("Single Test");
		m1.setBackground(new Color(255, 204, 51));
		m1.setForeground(new Color(0, 0, 255));
		m1.setBounds(110, 46, 136, 25);
		m1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				moveToGamePane();
			}

		});

		m2 = new JButton("Multiplayers");
		m2.setBackground(new Color(255, 204, 51));
		m2.setForeground(new Color(255, 51, 0));
		m2.setBounds(110, 86, 136, 25);
		m2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int count = 0;
				while (count < 5) {
					int option = JOptionPane.showOptionDialog(null,
							"More Players?", "Option", 2, 3, null, LABELS, 1);
					if (option == 0) {
						String info = JOptionPane.showInputDialog(null,
								"Please enter id");
						if ((info == null) || (info.equals(""))) {
							JOptionPane.showMessageDialog(null, "Invalid ID");
						} else {
							cxt.addID(info);
							count++;
						}
					}else{
						break;
					}
				}
				moveToGamePane();
			}
		});

		m3 = new JButton("Remote Game");
		m3.setBackground(new Color(255, 204, 51));
		m3.setForeground(new Color(204, 0, 204));
		m3.setBounds(110, 128, 136, 25);
		m3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				moveToGamePane();
			}
		});
		m3.setVisible(false);

		instruction = new JLabel(INSTRUCTION);
		instruction.setForeground(new Color(0, 255, 255));
		instruction.setFont(new Font("Dialog", Font.ITALIC, 13));
		instruction.setBounds(0, 0, 363, 34);

		JButton btnLogout = new JButton("Logout");
		btnLogout.setBackground(new Color(255, 204, 51));
		btnLogout.setForeground(new Color(102, 102, 0));
		btnLogout.setBounds(246, 223, 136, 25);
		btnLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				backToOption();
			}

		});
		setLayout(null);
		add(instruction);
		add(m1);
		add(m2);
		add(m3);
		add(btnLogout);
		this.setPreferredSize(new Dimension(400, 250));
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		ImageIcon backgroundIcon = new ImageIcon(PATH);
		Image img = backgroundIcon.getImage();
		g.drawImage(img, 0, 0, null);
	}

	private void backToOption() {
		parent.remove(this);
		parent.setLoginPanel();
		parent.setTitle("Login Panel");
		parent.pack();
	}

	private void moveToGamePane() {
		parent.remove(this);
		parent.setGamePane(new GamePane(parent, cxt));
		parent.setTitle("Scrabble Game");
		parent.pack();
	}
}
