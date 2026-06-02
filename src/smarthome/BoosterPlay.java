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

public class BoosterPlay extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int MAX_DISTANCE = 30;
	private static final String ANIMAL_IMAGE_DIR = "src" + File.separator + "채연";

	private JPanel contentPane;
	private JFrame mainFrame;

	private JLabel beforeImageLabel;
	private JLabel afterImageLabel;
	private JLabel boosterNameLabel;
	private JLabel beforeNameLabel;
	private JLabel afterNameLabel;
	private JProgressBar beforeProgressBar;
	private JProgressBar afterProgressBar;
	private JTextArea logTextArea;
	private JButton arrowButton;
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
					frame.showBooster("코끼리", 4, 6);
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

		JLabel beforeTitleLabel = new JLabel("부스터 사용 전");
		beforeTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		beforeTitleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 22));
		beforeTitleLabel.setBorder(new LineBorder(Color.BLACK, 2));
		beforeTitleLabel.setBounds(65, 55, 260, 34);
		contentPane.add(beforeTitleLabel);

		JLabel afterTitleLabel = new JLabel("부스터 사용 후");
		afterTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		afterTitleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 22));
		afterTitleLabel.setBorder(new LineBorder(Color.BLACK, 2));
		afterTitleLabel.setBounds(620, 55, 270, 34);
		contentPane.add(afterTitleLabel);

		beforeImageLabel = new JLabel("부스터 전 이미지");
		beforeImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		beforeImageLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
		beforeImageLabel.setBorder(new LineBorder(Color.BLACK, 2));
		beforeImageLabel.setBounds(60, 110, 275, 270);
		contentPane.add(beforeImageLabel);

		afterImageLabel = new JLabel("부스터 후 이미지");
		afterImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		afterImageLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
		afterImageLabel.setBorder(new LineBorder(Color.BLACK, 2));
		afterImageLabel.setBounds(620, 110, 275, 270);
		contentPane.add(afterImageLabel);

		boosterNameLabel = new JLabel("부스터");
		boosterNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		boosterNameLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 24));
		boosterNameLabel.setBounds(395, 200, 180, 65);
		contentPane.add(boosterNameLabel);

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
				playBooster();
			}
		});

		beforeNameLabel = new JLabel("코끼리");
		beforeNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		beforeNameLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
		beforeNameLabel.setBorder(new LineBorder(Color.BLACK, 2));
		beforeNameLabel.setBounds(145, 392, 95, 28);
		contentPane.add(beforeNameLabel);

		afterNameLabel = new JLabel("코끼리");
		afterNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		afterNameLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
		afterNameLabel.setBorder(new LineBorder(Color.BLACK, 2));
		afterNameLabel.setBounds(710, 392, 95, 28);
		contentPane.add(afterNameLabel);

		JLabel beforeDistanceTextLabel = new JLabel("거리");
		beforeDistanceTextLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
		beforeDistanceTextLabel.setBounds(70, 428, 44, 26);
		contentPane.add(beforeDistanceTextLabel);

		JLabel afterDistanceTextLabel = new JLabel("거리");
		afterDistanceTextLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
		afterDistanceTextLabel.setBounds(630, 428, 44, 26);
		contentPane.add(afterDistanceTextLabel);

		beforeProgressBar = new JProgressBar(0, MAX_DISTANCE);
		beforeProgressBar.setStringPainted(true);
		beforeProgressBar.setBounds(115, 430, 200, 22);
		contentPane.add(beforeProgressBar);

		afterProgressBar = new JProgressBar(0, MAX_DISTANCE);
		afterProgressBar.setStringPainted(true);
		afterProgressBar.setBounds(675, 430, 200, 22);
		contentPane.add(afterProgressBar);

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

	public void showBooster(String animalName, int beforeDistance, int afterDistance) {
		this.animalName = animalName;
		this.beforeDistance = beforeDistance;
		this.afterDistance = afterDistance;
		boosterFinished = false;

		int beforeValue = normalizeDistance(beforeDistance);
		setTitle("부스터 장면 - " + animalName);
		beforeNameLabel.setText(animalName);
		afterNameLabel.setText(animalName);
		boosterNameLabel.setText("부스터");
		setImage(beforeImageLabel, getAnimalImagePath(animalName), animalName);
		setImage(afterImageLabel, getAnimalImagePath(animalName), animalName);
		beforeProgressBar.setValue(beforeValue);
		afterProgressBar.setValue(beforeValue);
		beforeProgressBar.setString(beforeValue + " / " + MAX_DISTANCE);
		afterProgressBar.setString(beforeValue + " / " + MAX_DISTANCE);
		arrowButton.setEnabled(true);
		clearConsole();
		appendConsoleMessage(animalName + "이(가) 부스터를 사용하려고 합니다.");
		appendConsoleMessage("가운데 화살표 버튼을 누르면 앞으로 2칸 이동합니다.");
	}

	public void setBoosterAction(Runnable boosterAction) {
		this.boosterAction = boosterAction;
	}

	private void playBooster() {
		if (boosterFinished) {
			return;
		}

		int afterValue = normalizeDistance(afterDistance);
		afterProgressBar.setValue(afterValue);
		afterProgressBar.setString(afterValue + " / " + MAX_DISTANCE);
		appendConsoleMessage("부스터를 사용했습니다.");
		appendConsoleMessage(animalName + " 부스터 : 거리 " + beforeDistance + " -> " + afterDistance);
		if (boosterAction != null) {
			boosterAction.run();
		}
		boosterFinished = true;
		arrowButton.setEnabled(false);
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
		logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
	}
}
