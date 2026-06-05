package smarthome;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import animal.Animal;
import animal.기린;
import animal.알파카;
import animal.원숭이;
import animal.코끼리;
import animal.타조;

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

		JButton 코끼리선택 = new JButton("선택하기");
		코끼리선택.setBackground(Color.BLUE);
		코끼리선택.setBounds(22, 301, 94, 40);
		contentPane.add(코끼리선택);

		JButton 원숭이선택 = new JButton("선택하기");
		원숭이선택.setBackground(Color.GREEN);
		원숭이선택.setBounds(138, 301, 94, 40);
		contentPane.add(원숭이선택);

		JButton 타조선택 = new JButton("선택하기");
		타조선택.setBackground(Color.ORANGE);
		타조선택.setBounds(253, 301, 94, 40);
		contentPane.add(타조선택);

		JButton 기린선택 = new JButton("선택하기");
		기린선택.setBackground(new Color(128, 0, 128));
		기린선택.setBounds(370, 301, 94, 40);
		contentPane.add(기린선택);

		JButton 알파카선택 = new JButton("선택하기");
		알파카선택.setBackground(Color.PINK);
		알파카선택.setBounds(485, 301, 94, 40);
		contentPane.add(알파카선택);

		코끼리선택.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Main(createPlayers("코끼리", "원숭이", "타조", "기린", "알파카")).setVisible(true);
				dispose();
			}
		});

		원숭이선택.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Main(createPlayers("원숭이", "코끼리", "타조", "기린", "알파카")).setVisible(true);
				dispose();
			}
		});

		타조선택.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Main(createPlayers("타조", "코끼리", "원숭이", "기린", "알파카")).setVisible(true);
				dispose();
			}
		});

		기린선택.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Main(createPlayers("기린", "코끼리", "원숭이", "타조", "알파카")).setVisible(true);
				dispose();
			}
		});

		알파카선택.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Main(createPlayers("알파카", "코끼리", "원숭이", "타조", "기린")).setVisible(true);
				dispose();
			}
		});

		JLabel backgroundLabel = new JLabel();
		ImageIcon icon = createImageIcon("/images/start/동물레이스.png");
		Image image = icon.getImage().getScaledInstance(600, 420, Image.SCALE_SMOOTH);
		backgroundLabel.setIcon(new ImageIcon(image));
		backgroundLabel.setBounds(0, -10, 600, 420);
		contentPane.add(backgroundLabel);

		pack();
		setLocationRelativeTo(null);
	}

	private List<Animal> createPlayers(String first, String second, String third, String fourth, String fifth) {
		List<Animal> players = new ArrayList<Animal>();
		players.add(createPlayer(first));
		players.add(createPlayer(second));
		players.add(createPlayer(third));
		players.add(createPlayer(fourth));
		players.add(createPlayer(fifth));
		return players;
	}

	private Animal createPlayer(String animalName) {
		if ("코끼리".equals(animalName)) {
			return new 코끼리();
		}
		if ("원숭이".equals(animalName)) {
			return new 원숭이();
		}
		if ("타조".equals(animalName)) {
			return new 타조();
		}
		if ("기린".equals(animalName)) {
			return new 기린();
		}
		if ("알파카".equals(animalName)) {
			return new 알파카();
		}
		return new 코끼리();
	}

	private static ImageIcon createImageIcon(String resourcePath) {
		java.net.URL imageUrl = start.class.getResource(resourcePath);
		if (imageUrl == null) {
			return new ImageIcon();
		}
		return new ImageIcon(imageUrl);
	}
}
