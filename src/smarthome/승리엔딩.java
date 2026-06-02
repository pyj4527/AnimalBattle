package smarthome;

import java.awt.Image;
import java.awt.EventQueue;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					승리엔딩 frame = new 승리엔딩();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public 승리엔딩() {
		this("코끼리");
	}

	public 승리엔딩(String animalName) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(null);
		contentPane.setPreferredSize(new Dimension(600, 420));
		setContentPane(contentPane);

		JLabel 배경 = new JLabel();

		ImageIcon icon = new ImageIcon("src/채연/승리엔딩1.png");
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

		메인메뉴.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new start().setVisible(true);
				dispose();
			}
		});

		setWinAnimalImage(animalName);
	}

	private void setWinAnimalImage(String animalName) {
		JLabel 동물승리사진 = (JLabel) contentPane.getComponent(0);
		if ("코끼리".equals(animalName)) {
			setScaledImage(동물승리사진, "src/채연/코끼리세레머니.jpg");
		} else if ("원숭이".equals(animalName)) {
			setScaledImage(동물승리사진, "src/채연/원숭이세레머니.jpg");
		} else if ("타조".equals(animalName)) {
			setScaledImage(동물승리사진, "src/채연/타조세레머니.jpg");
		} else if ("기린".equals(animalName)) {
			setScaledImage(동물승리사진, "src/채연/기린세레머니.jpg");
		} else if ("알파카".equals(animalName)) {
			setScaledImage(동물승리사진, "src/채연/알파카세레머니.jpg");
		}
	}

	private void setScaledImage(JLabel label, String imagePath) {
		ImageIcon icon = new ImageIcon(imagePath);
		Image image = icon.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		label.setIcon(new ImageIcon(image));
	}
}
