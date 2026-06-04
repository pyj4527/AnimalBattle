package smarthome;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class 승리엔딩 extends JFrame {
	private static final long serialVersionUID = 1L;

	public 승리엔딩() {
		this("코끼리");
	}

	public 승리엔딩(String animalName) {
		setTitle("위너 페이지");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		JPanel contentPane = new BackgroundPanel("src/채연/승리엔딩1.png");
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(null);
		contentPane.setPreferredSize(new Dimension(600, 420));
		setContentPane(contentPane);

		JLabel animalImageLabel = new JLabel("");
		animalImageLabel.setBounds(215, 146, 171, 188);
		setEndingImage(animalImageLabel, animalName);
		contentPane.add(animalImageLabel);

		JButton mainMenuButton = new JButton("메인 메뉴로");
		mainMenuButton.setBackground(Color.ORANGE);
		mainMenuButton.setBounds(215, 344, 171, 51);
		mainMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new start().setVisible(true);
				dispose();
			}
		});
		contentPane.add(mainMenuButton);

		pack();
		setLocationRelativeTo(null);
	}

	private void setEndingImage(JLabel label, String animalName) {
		ImageIcon icon = null;
		if ("코끼리".equals(animalName)) {
			icon = new ImageIcon("src/채연/코끼리세레머니.jpg");
		} else if ("원숭이".equals(animalName)) {
			icon = new ImageIcon("src/채연/원숭이세레머니.jpg");
		} else if ("타조".equals(animalName)) {
			icon = new ImageIcon("src/채연/타조세레머니.jpg");
		} else if ("기린".equals(animalName)) {
			icon = new ImageIcon("src/채연/기린세레머니.jpg");
		} else if ("알파카".equals(animalName)) {
			icon = new ImageIcon("src/채연/알파카세레머니.jpg");
		}
		if (icon == null || icon.getIconWidth() <= 0) {
			label.setText(animalName);
			return;
		}
		Image image = icon.getImage().getScaledInstance(171, 188, Image.SCALE_SMOOTH);
		label.setIcon(new ImageIcon(image));
	}

	private static class BackgroundPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private final Image backgroundImage;

		private BackgroundPanel(String imagePath) {
			backgroundImage = imagePath == null || imagePath.trim().isEmpty()
					? null : new ImageIcon(imagePath).getImage();
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (backgroundImage != null) {
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		}
	}
}
