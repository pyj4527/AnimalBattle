package smarthome;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(null);
		contentPane.setPreferredSize(new Dimension(600, 420));
		setContentPane(contentPane);

		addSelectButton(contentPane, "코끼리", 22, 301, Color.BLUE);
		addSelectButton(contentPane, "원숭이", 138, 301, Color.GREEN);
		addSelectButton(contentPane, "타조", 253, 301, Color.ORANGE);
		addSelectButton(contentPane, "기린", 370, 301, new Color(128, 0, 128));
		addSelectButton(contentPane, "알파카", 485, 301, Color.PINK);

		JLabel backgroundLabel = new JLabel();
		ImageIcon icon = new ImageIcon(findImagePath("채연", "동물레이스.png"));
		Image image = icon.getImage().getScaledInstance(600, 420, Image.SCALE_SMOOTH);
		backgroundLabel.setIcon(new ImageIcon(image));
		backgroundLabel.setBounds(0, -10, 600, 420);
		contentPane.add(backgroundLabel);

		pack();
		setLocationRelativeTo(null);
	}

	private void addSelectButton(JPanel contentPane, String animalName, int x, int y, Color backgroundColor) {
		JButton selectButton = new JButton("선택하기");
		selectButton.setBackground(backgroundColor);
		selectButton.setBounds(x, y, 94, 40);
		selectButton.addActionListener(e -> openGame(animalName));
		contentPane.add(selectButton);
	}

	private void openGame(String selectedAnimalName) {
		List<Animal> animals = GameManager.createAnimalsWithSelectedFirst(selectedAnimalName);
		new Main(animals).setVisible(true);
		dispose();
	}

	private static String findImagePath(String folderName, String fileName) {
		File projectRootPath = new File(new File("src", folderName), fileName);
		if (projectRootPath.exists()) {
			return projectRootPath.getPath();
		}
		return new File(new File(folderName), fileName).getPath();
	}
}
