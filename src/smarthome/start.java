package smarthome;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import animal.Animal;
import animal.GameManager;

public class start extends JFrame {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new start().setVisible(true);
	}

	public start() {
		setTitle("플레이어 선택");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		JPanel contentPane = new BackgroundPanel(findAssetPath("동물레이스.png"));
		contentPane.setBorder(new EmptyBorder(18, 18, 18, 18));
		contentPane.setLayout(null);
		contentPane.setPreferredSize(new Dimension(760, 460));
		setContentPane(contentPane);

		JLabel titleLabel = new JLabel("플레이어 선택", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 28));
		titleLabel.setBounds(18, 18, 724, 42);
		contentPane.add(titleLabel);

		JPanel animalPanel = new JPanel(new GridLayout(1, 5, 12, 0));
		animalPanel.setOpaque(false);
		animalPanel.setBounds(18, 82, 724, 330);
		contentPane.add(animalPanel);

		for (Animal animal : GameManager.createDefaultAnimals()) {
			animalPanel.add(createAnimalChoicePanel(animal));
		}

		pack();
		setLocationRelativeTo(null);
	}

	private JPanel createAnimalChoicePanel(Animal animal) {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		JLabel imageLabel = new JLabel();
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setBounds(10, 16, 118, 180);
		setAnimalImage(imageLabel, animal, 118, 180);
		panel.add(imageLabel);

		JLabel nameLabel = new JLabel(animal.getName(), SwingConstants.CENTER);
		nameLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
		nameLabel.setBounds(10, 207, 118, 28);
		panel.add(nameLabel);

		JButton selectButton = new JButton("선택하기");
		selectButton.setBackground(Color.ORANGE);
		selectButton.setBounds(17, 260, 104, 40);
		selectButton.addActionListener(e -> openGame(animal.getName()));
		panel.add(selectButton);

		return panel;
	}

	private void openGame(String selectedAnimalName) {
		List<Animal> animals = GameManager.createAnimalsWithSelectedFirst(selectedAnimalName);
		new Main(animals).setVisible(true);
		dispose();
	}

	private void setAnimalImage(JLabel label, Animal animal, int width, int height) {
		ImageIcon icon = new ImageIcon(animal.getImagePath());
		if (icon.getIconWidth() <= 0) {
			label.setText(animal.getName());
			return;
		}
		Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
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
