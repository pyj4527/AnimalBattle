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

public class 패배엔딩 extends JFrame {
	private static final long serialVersionUID = 1L;

	public 패배엔딩() {
		this("코끼리");
	}

	public 패배엔딩(String animalName) {
		setTitle("루저 페이지");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		JPanel contentPane = new BackgroundPanel(findAssetPath("패배엔딩1.png"));
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setPreferredSize(new Dimension(600, 420));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel titleLabel = new JLabel("패배했습니다", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 30));
		titleLabel.setBounds(0, 42, 600, 44);
		contentPane.add(titleLabel);

		JLabel animalImageLabel = new JLabel("", SwingConstants.CENTER);
		animalImageLabel.setBounds(216, 132, 168, 168);
		setAnimalImage(animalImageLabel, animalName);
		contentPane.add(animalImageLabel);

		JLabel nameLabel = new JLabel(animalName, SwingConstants.CENTER);
		nameLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 20));
		nameLabel.setBounds(216, 306, 168, 30);
		contentPane.add(nameLabel);

		JButton mainMenuButton = new JButton("메인 메뉴로");
		mainMenuButton.setBackground(Color.BLUE);
		mainMenuButton.setForeground(Color.WHITE);
		mainMenuButton.setBounds(216, 352, 168, 42);
		mainMenuButton.addActionListener(e -> {
			new start().setVisible(true);
			dispose();
		});
		contentPane.add(mainMenuButton);

		pack();
		setLocationRelativeTo(null);
	}

	private void setAnimalImage(JLabel label, String animalName) {
		ImageIcon icon = new ImageIcon(Animal.findAnimalImagePath(animalName));
		if (icon.getIconWidth() <= 0) {
			label.setText(animalName);
			return;
		}
		Image image = icon.getImage().getScaledInstance(168, 168, Image.SCALE_SMOOTH);
		label.setIcon(new ImageIcon(image));
	}

	private static String findAssetPath(String fileName) {
		return findImagePath("채연", fileName);
	}

	private static String findImagePath(String folderName, String fileName) {
		File projectRootPath = new File(new File("src", folderName), fileName);
		if (projectRootPath.exists()) {
			return projectRootPath.getPath();
		}
		return new File(new File(folderName), fileName).getPath();
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
