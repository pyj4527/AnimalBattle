package smarthome;

import java.awt.Image;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;

public class 승리엔딩 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public 승리엔딩() {
		this(createPreviewAnimals());
	}

	public 승리엔딩(List<Animal> animals) {
		Animal player = animals.get(0);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(null);
		contentPane.setPreferredSize(new Dimension(600, 420));
		setContentPane(contentPane);

		JLabel 배경 = new JLabel();

		ImageIcon icon = new ImageIcon(승리엔딩.class.getResource("/smarthome/image/승리엔딩1.png"));
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(600, 420, Image.SCALE_SMOOTH);

		JLabel 동물승리사진 = new JLabel("");
		동물승리사진.setBounds(215, 146, 171, 188);
		contentPane.add(동물승리사진);

		JButton 메인메뉴 = new JButton("메인메뉴로");
		메인메뉴.setBackground(Color.ORANGE);
		메인메뉴.setBounds(215, 344, 171, 51);
		contentPane.add(메인메뉴);

		배경.setIcon(new ImageIcon(changeImg));
		배경.setBounds(0, 0, 600, 420);
		contentPane.add(배경);

		pack();
		setLocationRelativeTo(null);

		메인메뉴.addActionListener(e -> {
			new start().setVisible(true);
			dispose();
		});

		if (player instanceof 코끼리) {
			동물승리사진.setIcon(new ImageIcon(승리엔딩.class.getResource("/smarthome/image/코끼리세레머니.jpg")));
		} else if (player instanceof 원숭이) {
			동물승리사진.setIcon(new ImageIcon(승리엔딩.class.getResource("/smarthome/image/원숭이세레머니.jpg")));
		} else if (player instanceof 타조) {
			동물승리사진.setIcon(new ImageIcon(승리엔딩.class.getResource("/smarthome/image/타조세레머니.jpg")));
		} else if (player instanceof 기린) {
			동물승리사진.setIcon(new ImageIcon(승리엔딩.class.getResource("/smarthome/image/기린세레머니.jpg")));
		} else if (player instanceof 알파카) {
			동물승리사진.setIcon(new ImageIcon(승리엔딩.class.getResource("/smarthome/image/알파카세레머니.jpg")));
		}
	}

	private static List<Animal> createPreviewAnimals() {
		List<Animal> animals = new ArrayList<Animal>();
		animals.add(new 코끼리());
		return animals;
	}
}