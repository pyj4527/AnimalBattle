package smarthome;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
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

import animal.Animal;

public class Play extends JFrame {
	private static final long serialVersionUID = 1L;

	private JLabel attackerImageLabel;
	private JLabel targetImageLabel;
	private JLabel attackNameLabel;
	private JLabel attackerNameLabel;
	private JLabel targetNameLabel;
	private JProgressBar attackerProgressBar;
	private JProgressBar targetProgressBar;
	private JTextArea logTextArea;
	private JButton arrowButton;

	private String pendingSituationMessage;
	private String pendingAttackerName;
	private String pendingTargetName;
	private String pendingAttackName;
	private int pendingBeforeTargetDistance;
	private int pendingAfterTargetDistance;
	private boolean attackFinished;
	private Runnable attackAction;

	public Play() {
		this(null);
	}

	public Play(JFrame mainFrame) {
		setTitle("공격 장면");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 680);
		setLocationRelativeTo(null);

		JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel attackerTitleLabel = new JLabel("공격하는 동물", SwingConstants.CENTER);
		attackerTitleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 22));
		attackerTitleLabel.setBorder(new LineBorder(Color.BLACK, 2));
		attackerTitleLabel.setBounds(65, 55, 260, 34);
		contentPane.add(attackerTitleLabel);

		JLabel targetTitleLabel = new JLabel("공격당하는 동물", SwingConstants.CENTER);
		targetTitleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 22));
		targetTitleLabel.setBorder(new LineBorder(Color.BLACK, 2));
		targetTitleLabel.setBounds(620, 55, 270, 34);
		contentPane.add(targetTitleLabel);

		attackerImageLabel = new JLabel("공격 이미지", SwingConstants.CENTER);
		attackerImageLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
		attackerImageLabel.setBorder(new LineBorder(Color.BLACK, 2));
		attackerImageLabel.setBounds(60, 110, 275, 270);
		contentPane.add(attackerImageLabel);

		targetImageLabel = new JLabel("공격받음 이미지", SwingConstants.CENTER);
		targetImageLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
		targetImageLabel.setBorder(new LineBorder(Color.BLACK, 2));
		targetImageLabel.setBounds(620, 110, 275, 270);
		contentPane.add(targetImageLabel);

		attackNameLabel = new JLabel("", SwingConstants.CENTER);
		attackNameLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
		attackNameLabel.setBounds(395, 200, 180, 65);
		contentPane.add(attackNameLabel);

		arrowButton = new JButton(">");
		arrowButton.setFont(new Font("Malgun Gothic", Font.BOLD, 68));
		arrowButton.setBorderPainted(false);
		arrowButton.setContentAreaFilled(false);
		arrowButton.setFocusPainted(false);
		arrowButton.setBounds(408, 193, 140, 105);
		arrowButton.addActionListener(e -> playAttack());
		contentPane.add(arrowButton);

		attackerNameLabel = new JLabel("", SwingConstants.CENTER);
		attackerNameLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
		attackerNameLabel.setBorder(new LineBorder(Color.BLACK, 2));
		attackerNameLabel.setBounds(145, 392, 95, 28);
		contentPane.add(attackerNameLabel);

		targetNameLabel = new JLabel("", SwingConstants.CENTER);
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

		attackerProgressBar = new JProgressBar(0, Animal.GOAL_DISTANCE);
		attackerProgressBar.setStringPainted(true);
		attackerProgressBar.setBounds(115, 430, 200, 22);
		contentPane.add(attackerProgressBar);

		targetProgressBar = new JProgressBar(0, Animal.GOAL_DISTANCE);
		targetProgressBar.setStringPainted(true);
		targetProgressBar.setBounds(675, 430, 200, 22);
		contentPane.add(targetProgressBar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 485, 905, 100);
		scrollPane.setBorder(new LineBorder(Color.BLACK, 2));
		contentPane.add(scrollPane);

		logTextArea = new JTextArea();
		logTextArea.setEditable(false);
		logTextArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 16));
		logTextArea.setLineWrap(true);
		logTextArea.setWrapStyleWord(true);
		scrollPane.setViewportView(logTextArea);

		JButton closeButton = new JButton("닫기");
		closeButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
		closeButton.setBounds(835, 600, 105, 30);
		closeButton.addActionListener(e -> dispose());
		contentPane.add(closeButton);
	}

	public void setBattleInfo(String situationMessage, String attackerName, String targetName, String attackName,
			int attackerDistanceValue, int beforeTargetDistance, int afterTargetDistance) {
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
		setProgress(attackerProgressBar, attackerDistanceValue);
		setProgress(targetProgressBar, beforeTargetDistance);
		arrowButton.setEnabled(true);
		clearConsole();
		appendConsoleMessage(attackerName + "이(가) " + targetName + "을(를) 공격하려고 합니다.");
		appendConsoleMessage("가운데 버튼을 누르면 공격이 실행됩니다.");
	}

	public void setAttackAction(Runnable attackAction) {
		this.attackAction = attackAction;
	}

	private void playAttack() {
		if (attackFinished) {
			return;
		}
		attackNameLabel.setText(pendingAttackName);
		setProgress(targetProgressBar, pendingAfterTargetDistance);
		appendConsoleMessage(pendingSituationMessage);
		appendConsoleMessage(pendingAttackerName + " 공격(" + pendingAttackName + ") : "
				+ pendingTargetName + " 거리 " + pendingBeforeTargetDistance + " -> " + pendingAfterTargetDistance);
		if (attackAction != null) {
			attackAction.run();
		}
		attackFinished = true;
		arrowButton.setEnabled(false);
	}

	private void setProgress(JProgressBar progressBar, int distance) {
		int value = Math.max(0, Math.min(Animal.GOAL_DISTANCE, distance));
		progressBar.setValue(value);
		progressBar.setString(value + " / " + Animal.GOAL_DISTANCE);
	}

	private void setImage(JLabel label, String imagePath, String fallbackText) {
		if (imagePath == null || imagePath.trim().isEmpty()) {
			label.setIcon(null);
			label.setText(fallbackText);
			return;
		}
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
		return findImagePath("attack", animalName + "공격.png");
	}

	private String getHitImagePath(String animalName) {
		return findImagePath("hit", animalName + "공격받음.png");
	}

	private String findImagePath(String type, String fileName) {
		File projectRootPath = new File(new File(new File(new File("src", "혜주"), "images"), type), fileName);
		if (projectRootPath.exists()) {
			return projectRootPath.getPath();
		}
		return new File(new File(new File("혜주", "images"), type), fileName).getPath();
	}

	private void clearConsole() {
		logTextArea.setText("");
	}

	private void appendConsoleMessage(String message) {
		if (logTextArea.getText().length() > 0) {
			logTextArea.append("\n");
		}
		logTextArea.append(message);
	}
}
