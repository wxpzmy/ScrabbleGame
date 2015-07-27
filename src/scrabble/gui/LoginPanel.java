package scrabble.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import scrabble.core.game.GameContext;

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 3181871979499516458L;

	private static final String PATH = "assets/Scrabble-Game-5.jpg";

	private final String FOOT = "Copywrite: Michael WU";
	private final String WEL = "Scrabble Game!";
	private final String LOG = "Please login: ";
	private final String BUTTON_INFO = "Login";

	private JLabel wel;
	private JLabel foot;
	private JTextField login;
	private JLabel instruction;
	private JButton button;
	private MainFrame parent;
	private GameContext starter;

	public LoginPanel(MainFrame f, final GameContext cxt) {
		parent = f;
		starter = cxt;
		setSize(new Dimension(377, 200));
		wel = new JLabel(WEL);
		wel.setForeground(Color.BLUE);
		wel.setAlignmentX(CENTER_ALIGNMENT);
		wel.setFont(new Font(null, Font.BOLD, 26));
		instruction = new JLabel(LOG);
		instruction.setForeground(new Color(102, 255, 255));
		instruction.setAlignmentX(CENTER_ALIGNMENT);
		foot = new JLabel(FOOT);
		foot.setForeground(new Color(255, 102, 0));
		foot.setAlignmentX(RIGHT_ALIGNMENT);
		login = new JTextField(20);
		login.setForeground(new Color(0, 0, 0));
		login.setAlignmentX(CENTER_ALIGNMENT);
		button = new JButton(BUTTON_INFO);
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String id = login.getText();
				
				if ((id == null) || (id.equals(""))) {
					JOptionPane.showMessageDialog(null,
							"The id cannot be empty!");
				} else {
					starter.addID(id);
					changeToGameLoginPane();
					login.setText("");
				}
			}		
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(149)
					.addComponent(instruction, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(94, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(65, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(login, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(wel))
					.addGap(65))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(135)
					.addComponent(button, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(124, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(190, Short.MAX_VALUE)
					.addComponent(foot)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(wel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(instruction)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(login, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(43)
					.addComponent(button)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(foot)
					.addContainerGap(51, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
	
	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		ImageIcon backgroundIcon = new ImageIcon(PATH);
		Image img = backgroundIcon.getImage();
		g.drawImage(img, 0, 0, null);
	}
	
	private void changeToGameLoginPane() {
		parent.remove(this);
		parent.setGameLoginPane();
		parent.setTitle("Game Select");
		parent.pack();
	}
	
}
