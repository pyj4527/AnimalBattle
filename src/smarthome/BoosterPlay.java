package smarthome;

import java.awt.Color;
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
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import animal.Animal;

public class BoosterPlay extends JFrame {
	private static final long serialVersionUID = 1L;

	private JLabel animalImageLabel;
	private JLabel animalNameLabel;
	private JLabel distanceLabel;
	private JProgressBar distanceProgressBar;
	private JTextArea logTextArea;
	private JButton boosterButton;

	private String animalName;
	private int beforeDistance;
	private int afterDistance;
	private boolean boosterFinished;
	private Runnable boosterAction;

	public BoosterPlay() {
		this(null);
	}

	public BoosterPlay(JFrame mainFrame) {
		setTitle("부스터 장면");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 680);
		setLocationRelativeTo(null);

		JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel titleLabel = new JLabel("부스터 사용", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 34));
		titleLabel.setBounds(0, 38, 984, 48);
		contentPane.add(titleLabel);

		JLabel guideLabel = new JLabel("에너지를 모아 앞으로 2칸 이동합니다", SwingConstants.CENTER);
		guideLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
		guideLabel.setBounds(0, 90, 984, 28);
		contentPane.add(guideLabel);

		animalImageLabel = new JLabel("동물 이미지", SwingConstants.CENTER);
		animalImageLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
		animalImageLabel.setBorder(new LineBorder(Color.BLACK, 2));
		animalImageLabel.setBounds(365, 132, 255, 255);
		contentPane.add(animalImageLabel);

		animalNameLabel = new JLabel("", SwingConstants.CENTER);
		animalNameLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
		animalNameLabel.setBounds(392, 400, 200, 34);
		contentPane.add(animalNameLabel);

		distanceLabel = new JLabel("", SwingConstants.CENTER);
		distanceLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
		distanceLabel.setBounds(312, 442, 360, 28);
		contentPane.add(distanceLabel);

		distanceProgressBar = new JProgressBar(0, Animal.GOAL_DISTANCE);
		distanceProgressBar.setStringPainted(true);
		distanceProgressBar.setBounds(242, 478, 500, 28);
		contentPane.add(distanceProgressBar);

		logTextArea = new JTextArea();
		logTextArea.setEditable(false);
		logTextArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
		logTextArea.setLineWrap(true);
		logTextArea.setWrapStyleWord(true);
		logTextArea.setBorder(new LineBorder(Color.BLACK, 1));
		logTextArea.setBounds(232, 520, 520, 58);
		contentPane.add(logTextArea);

		boosterButton = new JButton("BOOST");
		boosterButton.setFont(new Font("Malgun Gothic", Font.BOLD, 20));
		boosterButton.setForeground(Color.WHITE);
		boosterButton.setBackground(new Color(0, 135, 255));
		boosterButton.setFocusPainted(false);
		boosterButton.setBounds(362, 590, 220, 44);
		boosterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playBooster();
			}
		});
		contentPane.add(boosterButton);

		JButton closeButton = new JButton("닫기");
		closeButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
		closeButton.setBounds(795, 590, 105, 34);
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(closeButton);
	}

	public void showBooster(String animalName, int beforeDistance, int afterDistance) {
		this.animalName = animalName;
		this.beforeDistance = normalizeDistance(beforeDistance);
		this.afterDistance = normalizeDistance(afterDistance);
		boosterFinished = false;

		setTitle("부스터 장면 - " + animalName);
		animalNameLabel.setText(animalName);
		setImage(animalImageLabel, Animal.findAnimalImagePath(animalName), animalName);
		distanceProgressBar.setValue(this.beforeDistance);
		updateDistanceText(this.beforeDistance);
		boosterButton.setEnabled(true);
		clearConsole();
		appendConsoleMessage(animalName + "이(가) 부스터를 준비합니다.");
		appendConsoleMessage("BOOST 버튼을 누르면 거리 게이지가 올라갑니다.");
	}

	public void setBoosterAction(Runnable boosterAction) {
		this.boosterAction = boosterAction;
	}

	private void playBooster() {
		if (boosterFinished) {
			return;
		}

		boosterButton.setEnabled(false);
		appendConsoleMessage("부스터 발동!");

		Timer timer = new Timer(80, null);
		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int currentValue = distanceProgressBar.getValue();
				if (currentValue < afterDistance) {
					int nextValue = Math.min(afterDistance, currentValue + 1);
					distanceProgressBar.setValue(nextValue);
					updateDistanceText(nextValue);
					return;
				}

				((Timer) e.getSource()).stop();
				appendConsoleMessage(animalName + " 부스터 : 거리 " + beforeDistance + " -> " + afterDistance);
				if (boosterAction != null) {
					boosterAction.run();
				}
				boosterFinished = true;
			}
		});
		timer.start();
	}

	private void updateDistanceText(int currentDistance) {
		distanceLabel.setText("거리 " + beforeDistance + " / " + Animal.GOAL_DISTANCE
				+ " -> " + afterDistance + " / " + Animal.GOAL_DISTANCE);
		distanceProgressBar.setString(currentDistance + " / " + Animal.GOAL_DISTANCE);
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

	private int normalizeDistance(int distance) {
		return Math.max(0, Math.min(Animal.GOAL_DISTANCE, distance));
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
