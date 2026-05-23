package 혜주;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Play extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int MAX_DISTANCE = 30;

	private JPanel contentPane;
	private JFrame mainFrame;

	private JLabel attackerTitleLabel;
	private JLabel targetTitleLabel;
	private JLabel attackerImageLabel;
	private JLabel targetImageLabel;
	private JLabel attackNameLabel;
	private JLabel attackerNameLabel;
	private JLabel targetNameLabel;
	private JLabel attackerDistanceLabel;
	private JLabel targetDistanceLabel;
	private JProgressBar attackerProgressBar;
	private JProgressBar targetProgressBar;
	private JTextArea logTextArea;
	private JButton closeButton;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Play frame = new Play();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Play() {
		this(null);
	}

	public Play(JFrame mainFrame) {
		this.mainFrame = mainFrame;
		initialize();
		setBattleInfo("코끼리", "기린", "코로 때리기", 17, 15);
	}

	private void initialize() {
		setTitle("공격 장면");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 680);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		attackerTitleLabel = new JLabel("공격하는 동물");
		attackerTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		attackerTitleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 22));
		attackerTitleLabel.setBorder(new LineBorder(Color.BLACK, 2));
		attackerTitleLabel.setBounds(65, 55, 260, 34);
		contentPane.add(attackerTitleLabel);

		targetTitleLabel = new JLabel("공격당한 동물");
		targetTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		targetTitleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 22));
		targetTitleLabel.setBorder(new LineBorder(Color.BLACK, 2));
		targetTitleLabel.setBounds(620, 55, 270, 34);
		contentPane.add(targetTitleLabel);

		attackerImageLabel = new JLabel("공격 이미지");
		attackerImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		attackerImageLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
		attackerImageLabel.setBorder(new LineBorder(Color.BLACK, 2));
		attackerImageLabel.setBounds(60, 110, 275, 270);
		contentPane.add(attackerImageLabel);

		targetImageLabel = new JLabel("공격받음 이미지");
		targetImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		targetImageLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
		targetImageLabel.setBorder(new LineBorder(Color.BLACK, 2));
		targetImageLabel.setBounds(620, 110, 275, 270);
		contentPane.add(targetImageLabel);

		attackNameLabel = new JLabel("코로 때리기");
		attackNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		attackNameLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
		attackNameLabel.setBorder(new LineBorder(Color.BLACK, 2));
		attackNameLabel.setBounds(395, 200, 180, 65);
		contentPane.add(attackNameLabel);

		JLabel arrowLabel = new JLabel("▶");
		arrowLabel.setHorizontalAlignment(SwingConstants.CENTER);
		arrowLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 80));
		arrowLabel.setBounds(420, 265, 130, 80);
		contentPane.add(arrowLabel);

		attackerNameLabel = new JLabel("코끼리");
		attackerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		attackerNameLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
		attackerNameLabel.setBorder(new LineBorder(Color.BLACK, 2));
		attackerNameLabel.setBounds(145, 392, 95, 28);
		contentPane.add(attackerNameLabel);

		targetNameLabel = new JLabel("기린");
		targetNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		targetNameLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
		targetNameLabel.setBorder(new LineBorder(Color.BLACK, 2));
		targetNameLabel.setBounds(710, 392, 95, 28);
		contentPane.add(targetNameLabel);

		JLabel attackerDistanceTextLabel = new JLabel("거리");
		attackerDistanceTextLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
		attackerDistanceTextLabel.setBounds(70, 428, 44, 26);
		contentPane.add(attackerDistanceTextLabel);

		JLabel targetDistanceTextLabel = new JLabel("거리");
		targetDistanceTextLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
		targetDistanceTextLabel.setBounds(630, 428, 44, 26);
		contentPane.add(targetDistanceTextLabel);

		attackerProgressBar = new JProgressBar(0, MAX_DISTANCE);
		attackerProgressBar.setStringPainted(true);
		attackerProgressBar.setBounds(115, 430, 200, 22);
		contentPane.add(attackerProgressBar);

		targetProgressBar = new JProgressBar(0, MAX_DISTANCE);
		targetProgressBar.setStringPainted(true);
		targetProgressBar.setBounds(675, 430, 200, 22);
		contentPane.add(targetProgressBar);

		attackerDistanceLabel = new JLabel("17 / 30");
		attackerDistanceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		attackerDistanceLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
		attackerDistanceLabel.setBounds(265, 452, 60, 22);
		contentPane.add(attackerDistanceLabel);

		targetDistanceLabel = new JLabel("15 / 30");
		targetDistanceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		targetDistanceLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
		targetDistanceLabel.setBounds(825, 452, 60, 22);
		contentPane.add(targetDistanceLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 485, 905, 100);
		contentPane.add(scrollPane);

		logTextArea = new JTextArea();
		logTextArea.setEditable(false);
		logTextArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 18));
		logTextArea.setText("코끼리 공격(코로 때리기) -> 기린 거리 17 -> 15");
		scrollPane.setViewportView(logTextArea);

		closeButton = new JButton("close");
		closeButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
		closeButton.setBounds(835, 600, 105, 30);
		contentPane.add(closeButton);

		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mainFrame != null) {
					mainFrame.setVisible(true);
				}
				dispose();
			}
		});
	}

	public void setBattleInfo(String attackerName, String targetName, String attackName,
			int beforeTargetDistance, int afterTargetDistance) {
		int attackerDistance = Math.max(0, Math.min(MAX_DISTANCE, beforeTargetDistance));
		int targetDistance = Math.max(0, Math.min(MAX_DISTANCE, afterTargetDistance));

		attackerNameLabel.setText(attackerName);
		targetNameLabel.setText(targetName);
		attackNameLabel.setText(attackName);
		attackerImageLabel.setText(attackerName + " 공격");
		targetImageLabel.setText(targetName + " 피격");
		attackerProgressBar.setValue(attackerDistance);
		targetProgressBar.setValue(targetDistance);
		attackerProgressBar.setString(attackerDistance + " / " + MAX_DISTANCE);
		targetProgressBar.setString(targetDistance + " / " + MAX_DISTANCE);
		attackerDistanceLabel.setText(attackerDistance + " / " + MAX_DISTANCE);
		targetDistanceLabel.setText(targetDistance + " / " + MAX_DISTANCE);
		logTextArea.setText(attackerName + " 공격(" + attackName + ") -> "
				+ targetName + " 거리 " + beforeTargetDistance + " -> " + afterTargetDistance);
	}
}
