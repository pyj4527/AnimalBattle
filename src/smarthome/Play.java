package smarthome;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
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
	private static final String DEFAULT_ATTACK_NAME = "공격";
	private static final String ATTACK_IMAGE_DIR = "src" + File.separator + "혜주"
			+ File.separator + "images" + File.separator + "attack";
	private static final String HIT_IMAGE_DIR = "src" + File.separator + "혜주"
			+ File.separator + "images" + File.separator + "hit";

	private JPanel contentPane;
	private JFrame mainFrame;

	private JLabel attackerTitleLabel;
	private JLabel targetTitleLabel;
	private JLabel attackerImageLabel;
	private JLabel targetImageLabel;
	private JLabel attackNameLabel;
	private JLabel attackerNameLabel;
	private JLabel targetNameLabel;
	private JProgressBar attackerProgressBar;
	private JProgressBar targetProgressBar;
	private JTextArea logTextArea;
	private JButton arrowButton;
	private JButton closeButton;

	private String pendingSituationMessage;
	private String pendingAttackerName;
	private String pendingTargetName;
	private String pendingAttackName;
	private int pendingBeforeTargetDistance;
	private int pendingAfterTargetDistance;
	private boolean attackFinished;

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

		targetTitleLabel = new JLabel("공격당하는 동물");
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

		attackNameLabel = new JLabel("");
		attackNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		attackNameLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
		attackNameLabel.setBorder(null);
		attackNameLabel.setBounds(395, 200, 180, 65);
		contentPane.add(attackNameLabel);

		arrowButton = new JButton("▶");
		arrowButton.setHorizontalAlignment(SwingConstants.CENTER);
		arrowButton.setVerticalAlignment(SwingConstants.CENTER);
		arrowButton.setFont(new Font("Malgun Gothic", Font.BOLD, 68));
		arrowButton.setBorderPainted(false);
		arrowButton.setContentAreaFilled(false);
		arrowButton.setFocusPainted(false);
		arrowButton.setOpaque(false);
		arrowButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
		arrowButton.setBounds(408, 193, 140, 105);
		contentPane.add(arrowButton);

		arrowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playAttack();
			}
		});

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

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 485, 905, 100);
		scrollPane.setBorder(new LineBorder(Color.BLACK, 2));
		contentPane.add(scrollPane);

		logTextArea = new JTextArea();
		logTextArea.setEditable(false);
		logTextArea.setBackground(Color.WHITE);
		logTextArea.setForeground(Color.BLACK);
		logTextArea.setCaretColor(Color.BLACK);
		logTextArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 16));
		logTextArea.setLineWrap(true);
		logTextArea.setWrapStyleWord(true);
		logTextArea.setText("");
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

	public void showAttack(String attackerName, int attackerDistance, String targetName,
			int beforeTargetDistance, int afterTargetDistance) {
		setBattleInfo("공격이 발생했습니다.", attackerName, targetName,
				getAttackName(attackerName), attackerDistance, beforeTargetDistance, afterTargetDistance);
	}

	public void showPlayerAttack(String selectedAnimalName, int selectedDistance, String targetName,
			int beforeTargetDistance, int afterTargetDistance) {
		setBattleInfo("내 캐릭터가 공격했습니다.", selectedAnimalName, targetName,
				getAttackName(selectedAnimalName), selectedDistance, beforeTargetDistance, afterTargetDistance);
	}

	public void showPlayerAttack(String selectedAnimalName, int selectedDistance, String targetName, String attackName,
			int beforeTargetDistance, int afterTargetDistance) {
		setBattleInfo("내 캐릭터가 공격했습니다.", selectedAnimalName, targetName,
				attackName, selectedDistance, beforeTargetDistance, afterTargetDistance);
	}

	public void showPlayerAttack(String selectedAnimalName, String targetName, String attackName,
			int beforeTargetDistance, int afterTargetDistance) {
		setBattleInfo("내 캐릭터가 공격했습니다.", selectedAnimalName, targetName,
				attackName, beforeTargetDistance, beforeTargetDistance, afterTargetDistance);
	}

	public void showEnemyAttack(String randomAnimalName, int randomAnimalDistance, String selectedAnimalName,
			int beforeSelectedDistance, int afterSelectedDistance) {
		setBattleInfo("내 캐릭터가 공격받았습니다.", randomAnimalName, selectedAnimalName,
				getAttackName(randomAnimalName), randomAnimalDistance, beforeSelectedDistance, afterSelectedDistance);
	}

	public void showEnemyAttack(String randomAnimalName, int randomAnimalDistance, String selectedAnimalName,
			String attackName, int beforeSelectedDistance, int afterSelectedDistance) {
		setBattleInfo("내 캐릭터가 공격받았습니다.", randomAnimalName, selectedAnimalName,
				attackName, randomAnimalDistance, beforeSelectedDistance, afterSelectedDistance);
	}

	public void showEnemyAttack(String randomAnimalName, String selectedAnimalName, String attackName,
			int beforeSelectedDistance, int afterSelectedDistance) {
		setBattleInfo("내 캐릭터가 공격받았습니다.", randomAnimalName, selectedAnimalName,
				attackName, beforeSelectedDistance, beforeSelectedDistance, afterSelectedDistance);
	}

	public void setBattleInfo(String situationMessage, String attackerName, String targetName, String attackName,
			int attackerDistanceValue, int beforeTargetDistance, int afterTargetDistance) {
		int attackerDistance = Math.max(0, Math.min(MAX_DISTANCE, attackerDistanceValue));
		int beforeDistance = Math.max(0, Math.min(MAX_DISTANCE, beforeTargetDistance));

		pendingSituationMessage = situationMessage;
		pendingAttackerName = attackerName;
		pendingTargetName = targetName;
		pendingAttackName = attackName;
		pendingBeforeTargetDistance = beforeTargetDistance;
		pendingAfterTargetDistance = afterTargetDistance;
		attackFinished = false;

		setTitle("공격 장면 - " + situationMessage);
		attackerNameLabel.setText(attackerName);
		targetNameLabel.setText(targetName);
		attackNameLabel.setText("");
		setImage(attackerImageLabel, getAttackImagePath(attackerName), attackerName + " 공격");
		setImage(targetImageLabel, getHitImagePath(targetName), targetName + " 피격");
		attackerProgressBar.setValue(attackerDistance);
		targetProgressBar.setValue(beforeDistance);
		attackerProgressBar.setString(attackerDistance + " / " + MAX_DISTANCE);
		targetProgressBar.setString(beforeDistance + " / " + MAX_DISTANCE);
		arrowButton.setEnabled(true);
		clearConsole();
		appendConsoleMessage(attackerName + "이(가) " + targetName + "을(를) 공격하려고 합니다.");
		appendConsoleMessage("가운데 화살표 버튼을 누르면 공격이 실행됩니다.");
	}

	public void setBattleInfo(String attackerName, String targetName, String attackName,
			int beforeTargetDistance, int afterTargetDistance) {
		setBattleInfo("공격이 발생했습니다.", attackerName, targetName,
				attackName, beforeTargetDistance, beforeTargetDistance, afterTargetDistance);
	}

	private void setImage(JLabel label, String imagePath, String fallbackText) {
		File imageFile = new File(imagePath);
		if (!imageFile.exists()) {
			label.setIcon(null);
			label.setText(fallbackText);
			return;
		}

		ImageIcon originalIcon = new ImageIcon(imagePath);
		Image scaledImage = originalIcon.getImage().getScaledInstance(
				label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		label.setText("");
		label.setIcon(new ImageIcon(scaledImage));
	}

	private String getAttackImagePath(String animalName) {
		return ATTACK_IMAGE_DIR + File.separator + animalName + "공격.png";
	}

	private String getHitImagePath(String animalName) {
		return HIT_IMAGE_DIR + File.separator + animalName + "공격받음.png";
	}

	public void clearConsole() {
		logTextArea.setText("");
	}

	public void appendConsoleMessage(String message) {
		if (logTextArea.getText().length() > 0) {
			logTextArea.append("\n");
		}
		logTextArea.append(message);
		logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
	}

	private void playAttack() {
		if (attackFinished) {
			return;
		}

		int afterDistance = Math.max(0, Math.min(MAX_DISTANCE, pendingAfterTargetDistance));
		targetProgressBar.setValue(afterDistance);
		targetProgressBar.setString(afterDistance + " / " + MAX_DISTANCE);
		appendConsoleMessage(pendingSituationMessage);
		appendConsoleMessage(pendingAttackerName + " 공격(" + pendingAttackName + ") : "
				+ pendingTargetName + " 거리 " + pendingBeforeTargetDistance + " -> " + pendingAfterTargetDistance);
		attackFinished = true;
		arrowButton.setEnabled(false);
	}

	private String getAttackName(String animalName) {
		if ("코끼리".equals(animalName)) {
			return "코로 때리기";
		}
		if ("기린".equals(animalName)) {
			return "꼬리로 때리기";
		}
		if ("원숭이".equals(animalName)) {
			return "바나나 껍질 던지기";
		}
		if ("타조".equals(animalName)) {
			return "부리로 쪼기";
		}
		if ("알파카".equals(animalName)) {
			return "침뱉기";
		}
		return DEFAULT_ATTACK_NAME;
	}
}


