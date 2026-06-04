package smarthome;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import animal.Animal;
import animal.GameManager;

public class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int PLAYER_COUNT = 5;

	private final GameManager gameManager;
	private final AnimalView[] animalViews = new AnimalView[PLAYER_COUNT];
	private int animalViewCount;

	private JPanel contentPane;
	private JPanel animalsPanel;
	private JPanel itemActionPanel;
	private JPanel itemMessagePanel;
	private JLabel currentImageLabel;
	private JLabel currentNameValueLabel;
	private JLabel currentDistanceValueLabel;
	private JLabel targetHintLabel;
	private JLabel turnInfoLabel;
	private JTextArea actionMessageTextArea;
	private JButton attackButton;
	private JButton boosterButton;
	private JButton rollButton;
	private JComboBox<Animal> targetComboBox;
	private boolean gameEnded;

	public Main() {
		this(GameManager.createDefaultAnimals());
	}

	public Main(Animal[] selectedAnimals) {
		gameManager = new GameManager(selectedAnimals);
		setTitle("공격 대상 선택");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 920, 690);
		setLocationRelativeTo(null);

		contentPane = new BackgroundPanel(findAssetPath("시작화면.png"));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		createLayout();
		refreshScreen();
	}

	private void createLayout() {
		JPanel currentPanel = new JPanel();
		currentPanel.setLayout(null);
		currentPanel.setBorder(BorderFactory.createTitledBorder("현재 플레이어"));
		currentPanel.setBounds(12, 10, 250, 570);
		currentPanel.setOpaque(false);
		contentPane.add(currentPanel);

		currentImageLabel = new JLabel();
		currentImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		currentImageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		currentImageLabel.setBounds(52, 35, 140, 138);
		currentPanel.add(currentImageLabel);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(null);
		infoPanel.setBorder(BorderFactory.createTitledBorder("선수 정보"));
		infoPanel.setBounds(12, 195, 225, 108);
		infoPanel.setOpaque(false);
		currentPanel.add(infoPanel);

		JLabel nameLabel = new JLabel("이름");
		nameLabel.setBounds(18, 38, 49, 15);
		infoPanel.add(nameLabel);

		JLabel distanceLabel = new JLabel("거리");
		distanceLabel.setBounds(18, 66, 49, 15);
		infoPanel.add(distanceLabel);

		currentNameValueLabel = new JLabel();
		currentNameValueLabel.setBounds(77, 38, 130, 15);
		infoPanel.add(currentNameValueLabel);

		currentDistanceValueLabel = new JLabel();
		currentDistanceValueLabel.setBounds(77, 66, 130, 15);
		infoPanel.add(currentDistanceValueLabel);

		itemActionPanel = new JPanel();
		itemActionPanel.setLayout(null);
		itemActionPanel.setBounds(12, 324, 225, 214);
		itemActionPanel.setOpaque(false);
		currentPanel.add(itemActionPanel);

		attackButton = new JButton("공격하기");
		attackButton.setBounds(10, 0, 90, 55);
		attackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				attackByMe();
			}
		});
		itemActionPanel.add(attackButton);

		boosterButton = new JButton("부스터 사용");
		boosterButton.setBounds(111, 0, 104, 55);
		boosterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				useBoosterByMe();
			}
		});
		itemActionPanel.add(boosterButton);

		JLabel targetGuideLabel = new JLabel("공격 대상을 선택하세요");
		targetGuideLabel.setHorizontalAlignment(SwingConstants.CENTER);
		targetGuideLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 13));
		targetGuideLabel.setBounds(10, 74, 205, 20);
		itemActionPanel.add(targetGuideLabel);

		targetComboBox = new JComboBox<Animal>();
		targetComboBox.setBounds(10, 102, 205, 28);
		targetComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Animal target = (Animal) targetComboBox.getSelectedItem();
				targetHintLabel.setText(target == null ? "선택" : target.getName());
			}
		});
		itemActionPanel.add(targetComboBox);

		JPanel selectedTargetPanel = new JPanel(new BorderLayout());
		selectedTargetPanel.setBounds(10, 144, 205, 53);
		selectedTargetPanel.setBorder(BorderFactory.createTitledBorder("선택한 공격 대상"));
		selectedTargetPanel.setOpaque(false);
		itemActionPanel.add(selectedTargetPanel);

		targetHintLabel = new JLabel("선택", SwingConstants.CENTER);
		selectedTargetPanel.add(targetHintLabel, BorderLayout.CENTER);

		itemMessagePanel = new JPanel(new BorderLayout());
		itemMessagePanel.setBounds(12, 324, 225, 214);
		itemMessagePanel.setBorder(BorderFactory.createTitledBorder("진행 안내"));
		itemMessagePanel.setBackground(Color.WHITE);
		itemMessagePanel.setOpaque(true);
		currentPanel.add(itemMessagePanel);

		actionMessageTextArea = new JTextArea();
		actionMessageTextArea.setEditable(false);
		actionMessageTextArea.setBackground(Color.WHITE);
		actionMessageTextArea.setOpaque(true);
		actionMessageTextArea.setLineWrap(true);
		actionMessageTextArea.setWrapStyleWord(true);
		actionMessageTextArea.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
		itemMessagePanel.add(actionMessageTextArea, BorderLayout.CENTER);

		JLabel animalsTitleLabel = new JLabel("전체 플레이어");
		animalsTitleLabel.setBounds(273, 10, 160, 14);
		contentPane.add(animalsTitleLabel);

		animalsPanel = new JPanel();
		animalsPanel.setLayout(null);
		animalsPanel.setBounds(273, 34, 620, 536);
		animalsPanel.setOpaque(false);
		contentPane.add(animalsPanel);
		createAnimalViews();

		turnInfoLabel = new JLabel("주사위를 굴려주세요.");
		turnInfoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		turnInfoLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
		turnInfoLabel.setForeground(Color.BLACK);
		turnInfoLabel.setBounds(273, 585, 480, 32);
		contentPane.add(turnInfoLabel);

		rollButton = new JButton("주사위 굴리기");
		rollButton.setBounds(12, 598, 127, 37);
		rollButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rollDiceTurn();
			}
		});
		contentPane.add(rollButton);

		JButton resetButton = new JButton("초기화");
		resetButton.setBounds(149, 598, 105, 37);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetGame();
			}
		});
		contentPane.add(resetButton);

		setMyItemButtonsEnabled(false);
	}

	private void createAnimalViews() {
		animalsPanel.removeAll();
		animalViewCount = 0;

		int index = 0;
		for (Animal animal : gameManager.getAnimals()) {
			AnimalView view = new AnimalView(animal);
			int y = index == 0 ? 0 : index * 104 + 14;
			view.panel.setBounds(0, y, 620, 96);
			animalViews[animalViewCount] = view;
			animalViewCount++;
			animalsPanel.add(view.panel);
			if (index == 0) {
				JLabel separatorLine = new JLabel();
				separatorLine.setOpaque(true);
				separatorLine.setBackground(Color.LIGHT_GRAY);
				separatorLine.setBounds(0, 105, 620, 3);
				animalsPanel.add(separatorLine);
			}
			index++;
			if (index == PLAYER_COUNT) {
				break;
			}
		}
	}

	private void rollDiceTurn() {
		if (gameEnded) {
			return;
		}
		if (gameManager.isItemPhase()) {
			JOptionPane.showMessageDialog(this, "이번 턴 아이템 사용이 끝난 뒤 다시 굴릴 수 있습니다.");
			return;
		}

		GameManager.DiceTurnResult diceResult = gameManager.rollDiceTurn();
		animateProgressBars(new Runnable() {
			public void run() {
				gameManager.prepareItemPhaseAfterDice();
				refreshScreen();

				Animal winner = gameManager.findWinner();
				if (winner != null) {
					openResultPage(winner);
					return;
				}

				turnInfoLabel.setText(diceResult.toMessage());
				processCurrentItemUser();
			}
		});
	}

	private void processCurrentItemUser() {
		if (!gameManager.hasCurrentItemUser()) {
			finishItemPhase();
			return;
		}

		Animal itemUser = gameManager.getCurrentItemUser();
		showCurrentItemPlayer(itemUser);

		if (itemUser == gameManager.getMe()) {
			turnInfoLabel.setText("아이템을 선택하세요.");
			setMyItemButtonsEnabled(true);
			return;
		}

		setMyItemButtonsEnabled(false);
		GameManager.ItemResult itemResult = gameManager.useRandomItem(itemUser);
		showItemMessage(itemResult.toMessage());
		refreshScreen();

		Animal winner = gameManager.findWinner();
		if (winner != null) {
			openResultPage(winner);
			return;
		}

		Timer nextPlayerTimer = new Timer(900, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((Timer) e.getSource()).stop();
				gameManager.moveToNextItemUser();
				processCurrentItemUser();
			}
		});
		nextPlayerTimer.setRepeats(false);
		nextPlayerTimer.start();
	}

	private void attackByMe() {
		if (!attackButton.isEnabled()) {
			return;
		}
		Animal target = (Animal) targetComboBox.getSelectedItem();
		if (target == null) {
			JOptionPane.showMessageDialog(this, "공격 대상을 선택해주세요.");
			return;
		}

		Animal attacker = gameManager.getMe();
		int beforeDistance = target.getDistance();
		int afterDistance = Math.max(0, beforeDistance - attacker.getAttackPower());
		setMyItemButtonsEnabled(false);

		Play attackFrame = new Play(this);
		attackFrame.setBattleInfo("내 캐릭터가 공격했습니다.", attacker.getName(), target.getName(),
				attacker.getAttackName(), attacker.getDistance(), beforeDistance, afterDistance);
		attackFrame.setAttackAction(new Runnable() {
			public void run() {
				gameManager.attack(attacker, target);
				refreshScreen();
				checkWinnerAfterMyItem();
			}
		});
		attackFrame.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				if (!gameEnded && gameManager.isItemPhase()) {
					gameManager.moveToNextItemUser();
					processCurrentItemUser();
				}
			}
		});
		attackFrame.setVisible(true);
	}

	private void useBoosterByMe() {
		if (!boosterButton.isEnabled()) {
			return;
		}
		Animal animal = gameManager.getMe();
		int beforeDistance = animal.getDistance();
		int afterDistance = Math.min(Animal.GOAL_DISTANCE, beforeDistance + animal.getBoosterSpeed());
		setMyItemButtonsEnabled(false);

		BoosterPlay boosterFrame = new BoosterPlay(this);
		boosterFrame.showBooster(animal.getName(), beforeDistance, afterDistance);
		boosterFrame.setBoosterAction(new Runnable() {
			public void run() {
				gameManager.useBooster(animal);
				refreshScreen();
				checkWinnerAfterMyItem();
			}
		});
		boosterFrame.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				if (!gameEnded && gameManager.isItemPhase()) {
					gameManager.moveToNextItemUser();
					processCurrentItemUser();
				}
			}
		});
		boosterFrame.setVisible(true);
	}

	private void checkWinnerAfterMyItem() {
		Animal winner = gameManager.findWinner();
		if (winner != null) {
			openResultPage(winner);
		}
	}

	private void finishItemPhase() {
		gameManager.finishItemPhase();
		setMyItemButtonsEnabled(false);
		clearCurrentItemPlayer();
		turnInfoLabel.setText("턴 종료. 주사위를 굴려주세요.");

		Animal winner = gameManager.findWinner();
		if (winner != null) {
			openResultPage(winner);
		}
	}

	private void animateProgressBars(Runnable afterAnimation) {
		Timer timer = new Timer(15, null);
		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean changed = gameManager.advanceDisplayDistances();
				refreshScreen();
				if (!changed) {
					timer.stop();
					afterAnimation.run();
				}
			}
		});
		timer.start();
	}

	private void showCurrentItemPlayer(Animal animal) {
		setAnimalImage(currentImageLabel, animal, 140, 138);
		currentNameValueLabel.setText(animal.getName());
		currentDistanceValueLabel.setText(animal.getDistance() + " / " + Animal.GOAL_DISTANCE);
	}

	private void clearCurrentItemPlayer() {
		currentImageLabel.setIcon(null);
		currentImageLabel.setText("");
		currentNameValueLabel.setText("");
		currentDistanceValueLabel.setText("");
		actionMessageTextArea.setText("");
		targetHintLabel.setText("선택");
		showMyItemControls(false);
	}

	private void showItemMessage(String message) {
		actionMessageTextArea.setText(message);
		actionMessageTextArea.setCaretPosition(0);
		showMyItemControls(false);
	}

	private void refreshScreen() {
		Animal selectedTarget = (Animal) targetComboBox.getSelectedItem();
		DefaultComboBoxModel<Animal> comboModel = new DefaultComboBoxModel<Animal>();
		for (Animal animal : gameManager.getAnimals()) {
			if (animal != gameManager.getMe()) {
				comboModel.addElement(animal);
			}
		}
		targetComboBox.setModel(comboModel);
		if (selectedTarget != null) {
			targetComboBox.setSelectedItem(selectedTarget);
		}

		for (int i = 0; i < animalViewCount; i++) {
			animalViews[i].refresh();
		}

		if (gameManager.hasCurrentItemUser()) {
			Animal current = gameManager.getCurrentItemUser();
			currentDistanceValueLabel.setText(current.getDistance() + " / " + Animal.GOAL_DISTANCE);
		}
	}

	private void setMyItemButtonsEnabled(boolean enabled) {
		attackButton.setEnabled(enabled);
		boosterButton.setEnabled(enabled);
		targetComboBox.setEnabled(enabled);
		rollButton.setEnabled(!enabled && !gameManager.isItemPhase() && !gameEnded);
		showMyItemControls(enabled);
	}

	private void showMyItemControls(boolean visible) {
		itemActionPanel.setVisible(visible);
		itemMessagePanel.setVisible(!visible);
	}

	private void openResultPage(Animal winner) {
		gameEnded = true;
		setMyItemButtonsEnabled(false);
		rollButton.setEnabled(false);
		if (winner == gameManager.getMe()) {
			new 승리엔딩(gameManager.getMe().getName()).setVisible(true);
		} else {
			new 패배엔딩(gameManager.getMe().getName()).setVisible(true);
		}
		dispose();
	}

	private void resetGame() {
		dispose();
		new start().setVisible(true);
	}

	private void setAnimalImage(JLabel label, Animal animal, int width, int height) {
		ImageIcon icon = new ImageIcon(animal.getImagePath());
		if (icon.getIconWidth() <= 0) {
			label.setIcon(null);
			label.setText(animal.getName());
			return;
		}
		Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		label.setText("");
		label.setIcon(new ImageIcon(image));
	}

	private class AnimalView {
		private final Animal animal;
		private final JPanel panel;
		private final JLabel imageLabel;
		private final JLabel nameLabel;
		private final JLabel distanceValueLabel;
		private final JProgressBar progressBar;

		private AnimalView(Animal animal) {
			this.animal = animal;
			panel = new JPanel();
			panel.setLayout(null);
			panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			panel.setOpaque(false);

			imageLabel = new JLabel();
			imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
			imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			imageLabel.setBounds(10, 8, 115, 78);
			panel.add(imageLabel);

			nameLabel = new JLabel();
			nameLabel.setFont(new Font("Dialog", Font.BOLD, 13));
			nameLabel.setBounds(145, 8, 300, 20);
			panel.add(nameLabel);

			distanceValueLabel = new JLabel();
			distanceValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			distanceValueLabel.setBounds(475, 8, 90, 20);
			panel.add(distanceValueLabel);

			progressBar = new JProgressBar(0, Animal.GOAL_DISTANCE);
			progressBar.setStringPainted(true);
			progressBar.setBounds(145, 42, 420, 22);
			panel.add(progressBar);
		}

		private void refresh() {
			setAnimalImage(imageLabel, animal, 115, 78);
			if (animal == gameManager.getMe()) {
				nameLabel.setText("내 캐릭터 : " + animal.getName());
			} else {
				nameLabel.setText(animal.getName());
			}
			progressBar.setValue(animal.getDisplayDistance());
			progressBar.setString(animal.getDisplayDistance() + " / " + Animal.GOAL_DISTANCE);
			distanceValueLabel.setText("거리 " + animal.getDistance() + "칸");
		}
	}

	private static String findAssetPath(String fileName) {
		return findImagePath("채연", fileName);
	}

	private static String findImagePath(String folderName, String fileName) {
		File projectRootPath = new File(new File("src", folderName), fileName);
		if (projectRootPath.exists()) {
			return projectRootPath.getPath();
		}
		return new File(new File(folderName), fileName).getPath();
	}

	private static class BackgroundPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private final Image backgroundImage;

		private BackgroundPanel(String imagePath) {
			backgroundImage = imagePath == null || imagePath.trim().isEmpty()
					? null : new ImageIcon(imagePath).getImage();
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (backgroundImage != null) {
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		}
	}
}
