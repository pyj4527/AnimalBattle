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
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class BoosterPlay extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int MAX_DISTANCE = 30;
	private static final String ANIMAL_IMAGE_DIR = "src" + File.separator + "채연";

	private JPanel contentPane;
	private JFrame mainFrame;

	private JLabel animalImageLabel;
	private JLabel animalNameLabel;
	private JLabel distanceLabel;
	private JProgressBar distanceProgressBar;
	private JTextArea logTextArea;
	private JButton boosterButton;
	private JButton closeButton;

	private String animalName;
	private int beforeDistance;
	private int afterDistance;
	private boolean boosterFinished;
	private Runnable boosterAction;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BoosterPlay frame = new BoosterPlay();
					frame.showBooster("타조", 5, 7);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public BoosterPlay() {
		this(null);
	}

	public BoosterPlay(JFrame mainFrame) {
		this.mainFrame = mainFrame;
		initialize();
	}

	private void initialize() {
		setTitle("부스터 장면");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 680);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel titleLabel = new JLabel("부스터 사용");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(Color.BLACK);
		titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 34));
		titleLabel.setBounds(0, 38, 984, 48);
		contentPane.add(titleLabel);

		JLabel guideLabel = new JLabel("에너지를 모아 앞으로 2칸 이동합니다");
		guideLabel.setHorizontalAlignment(SwingConstants.CENTER);
		guideLabel.setForeground(new Color(45, 45, 45));
		guideLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
		guideLabel.setBounds(0, 90, 984, 28);
		contentPane.add(guideLabel);

		animalImageLabel = new JLabel("동물 이미지");
		animalImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		animalImageLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
		animalImageLabel.setOpaque(true);
		animalImageLabel.setBackground(Color.WHITE);
		animalImageLabel.setBorder(new LineBorder(Color.BLACK, 2));
		animalImageLabel.setBounds(365, 132, 255, 255);
		contentPane.add(animalImageLabel);

		animalNameLabel = new JLabel("타조");
		animalNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		animalNameLabel.setForeground(Color.BLACK);
		animalNameLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
		animalNameLabel.setBounds(392, 400, 200, 34);
		contentPane.add(animalNameLabel);

		distanceLabel = new JLabel("거리 5 / 30 -> 7 / 30");
		distanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		distanceLabel.setForeground(Color.BLACK);
		distanceLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
		distanceLabel.setBounds(312, 442, 360, 28);
		contentPane.add(distanceLabel);

		distanceProgressBar = new JProgressBar(0, MAX_DISTANCE);
		distanceProgressBar.setStringPainted(true);
		distanceProgressBar.setForeground(new Color(0, 220, 255));
		distanceProgressBar.setBackground(new Color(235, 235, 235));
		distanceProgressBar.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
		distanceProgressBar.setBounds(242, 478, 500, 28);
		contentPane.add(distanceProgressBar);

		boosterButton = new JButton("BOOST");
		boosterButton.setFont(new Font("Malgun Gothic", Font.BOLD, 20));
		boosterButton.setForeground(Color.WHITE);
		boosterButton.setBackground(new Color(0, 135, 255));
		boosterButton.setFocusPainted(false);
		boosterButton.setBounds(362, 590, 220, 44);
		contentPane.add(boosterButton);

		boosterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playBooster();
			}
		});

		logTextArea = new JTextArea();
		logTextArea.setEditable(false);
		logTextArea.setBackground(Color.WHITE);
		logTextArea.setForeground(Color.BLACK);
		logTextArea.setCaretColor(Color.BLACK);
		logTextArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
		logTextArea.setLineWrap(true);
		logTextArea.setWrapStyleWord(true);
		logTextArea.setBorder(new LineBorder(Color.BLACK, 1));
		logTextArea.setBounds(232, 520, 520, 58);
		contentPane.add(logTextArea);

		closeButton = new JButton("close");
		closeButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
		closeButton.setBounds(795, 590, 105, 34);
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

	public void showBooster(String animalName, int beforeDistance, int afterDistance) {
		this.animalName = animalName;
		this.beforeDistance = normalizeDistance(beforeDistance);
		this.afterDistance = normalizeDistance(afterDistance);
		boosterFinished = false;

		setTitle("부스터 장면 - " + animalName);
		animalNameLabel.setText(animalName);
		setImage(animalImageLabel, getAnimalImagePath(animalName), animalName);
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

				timer.stop();
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
		distanceLabel.setText("거리 " + beforeDistance + " / " + MAX_DISTANCE
				+ " -> " + afterDistance + " / " + MAX_DISTANCE);
		distanceProgressBar.setString(currentDistance + " / " + MAX_DISTANCE);
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

	private String getAnimalImagePath(String animalName) {
		return ANIMAL_IMAGE_DIR + File.separator + animalName + ".jpg";
	}

	private int normalizeDistance(int distance) {
		return Math.max(0, Math.min(MAX_DISTANCE, distance));
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
