package smarthome;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;

public class 패배엔딩 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					패배엔딩 frame = new 패배엔딩();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public 패배엔딩() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setPreferredSize(new Dimension(600, 420));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ImageIcon icon = new ImageIcon(패배엔딩.class.getResource("/smarthome/image/패배엔딩1.png"));
		Image img = icon.getImage();
		Image resizedImg = img.getScaledInstance(600, 420, Image.SCALE_SMOOTH);
		
		JLabel 동물패배사진 = new JLabel("New label");
		동물패배사진.setBounds(213, 160, 168, 168);
		contentPane.add(동물패배사진);
		
		JButton 메인메뉴 = new JButton("메인메뉴로");
		메인메뉴.setBackground(Color.BLUE);
		메인메뉴.setBounds(213, 353, 168, 42);
		contentPane.add(메인메뉴);

		JLabel 패배배경 = new JLabel(new ImageIcon(resizedImg));
		패배배경.setBounds(0, 0, 600, 420);
		contentPane.add(패배배경);

		pack();
		setLocationRelativeTo(null);
	}
}