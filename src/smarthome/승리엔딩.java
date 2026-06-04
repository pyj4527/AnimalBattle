package smarthome;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import animal.Animal;

public class 승리엔딩 extends JFrame {
	private static final long serialVersionUID = 1L;

	public 승리엔딩() {
		this("코끼리");
	}

	public 승리엔딩(String animalName) {
		setTitle("위너 페이지");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		JPanel contentPane = new BackgroundPanel(findAssetPath("승리엔딩1.png"));
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(null);
		contentPane.setPreferredSize(new Dimension(600, 420));
		setContentPane(contentPane);

		JLabel titleLabel = new JLabel("승리했습니다!", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 30));
		titleLabel.setBounds(0, 28, 600, 48);
		contentPane.add(titleLabel);

		JLabel animalImageLabel = new JLabel("", SwingConstants.CENTER);
		animalImageLabel.setBounds(215, 110, 171, 188);
		setEndingImage(animalImageLabel, animalName);
		contentPane.add(animalImageLabel);

		JLabel nameLabel = new JLabel(animalName, SwingConstants.CENTER);
		nameLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 22));
		nameLabel.setBounds(215, 306, 171, 32);
		contentPane.add(nameLabel);

		JButton mainMenuButton = new JButton("메인 메뉴로");
		mainMenuButton.setBackground(Color.ORANGE);
		mainMenuButton.setBounds(215, 350, 171, 42);
		mainMenuButton.addActionListener(e -> {
			new start().setVisible(true);
			dispose();
		});
		contentPane.add(mainMenuButton);

		pack();
		setLocationRelativeTo(null);
	}

	private void setEndingImage(JLabel label, String animalName) {
		String endingImagePath = findAssetPath(animalName + "세레머니.jpg");
		ImageIcon icon = new ImageIcon(endingImagePath);
		if (icon.getIconWidth() <= 0) {
			icon = new ImageIcon(Animal.findAnimalImagePath(animalName));
		}
		if (icon.getIconWidth() <= 0) {
			label.setText(animalName);
			return;
		}
		Image image = icon.getImage().getScaledInstance(171, 188, Image.SCALE_SMOOTH);
		label.setIcon(new ImageIcon(image));
	}

	private static String findAssetPath(String fileName) {
		// TODO: 승리 배경/세레머니 사진 경로를 여기에 넣으세요.
		return "";
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
