package smarthome;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int GOAL_DISTANCE = 30;
	private static final int ITEM_DISTANCE = 2;
	private static final int DICE_MIN = 1;
	private static final int DICE_MAX = 6;
	private static final int PLAYER_COUNT = 5;
	private static final String MAIN_BACKGROUND_IMAGE_PATH = "src/채연/시작화면.png";

	private final JPanel contentPane;
	private final Random random = new Random();
	private final List<Player> players = new ArrayList<Player>();
	private final List<Player> currentItemUsers = new ArrayList<Player>();
	private final List<PlayerView> rivalViews = new ArrayList<PlayerView>();

	private Player me;
	private JLabel currentImageLabel;
	private JLabel currentNameValueLabel;
	private JLabel currentDistanceValueLabel;
	private JTextArea actionMessageTextArea;
	private JLabel targetGuideLabel;
	private JLabel targetHintLabel;
	private JLabel turnInfoLabel;
	private JButton attackButton;
	private JButton boosterButton;
	private JButton rollButton;
	private JButton resetButton;
	private JComboBox<Player> targetComboBox;
	private JPanel rivalsPanel;
	private JPanel itemActionPanel;
	private JPanel itemMessagePanel;
	private boolean itemPhase;
	private int itemUserIndex;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		this(createSamplePlayers());
	}

	public Main(List<?> selectedPlayers) {
		setTitle("공격 대상 선택");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1250, 770);
		contentPane = new BackgroundPanel(MAIN_BACKGROUND_IMAGE_PATH);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		setPlayers(selectedPlayers);
		createLayout();
		refreshScreen();
	}

	private void setPlayers(List<?> selectedPlayers) {
		players.clear();
		if (selectedPlayers == null || selectedPlayers.isEmpty()) {
			players.addAll(createSamplePlayers());
		} else {
			for (Object selectedPlayer : selectedPlayers) {
				players.add(createPlayer(selectedPlayer));
			}
		}

		while (players.size() < PLAYER_COUNT) {
			players.add(new Player("플레이어" + players.size(), null));
		}

		me = players.get(0);
	}

	private void createLayout() {
		JPanel itemTurnPanel = new JPanel();
		itemTurnPanel.setLayout(null);
		itemTurnPanel.setBorder(BorderFactory.createTitledBorder("현재 플레이어"));
		itemTurnPanel.setBounds(12, 10, 249, 646);
		itemTurnPanel.setOpaque(false);
		contentPane.add(itemTurnPanel);

		currentImageLabel = new JLabel();
		currentImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		currentImageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		currentImageLabel.setBounds(52, 35, 140, 138);
		itemTurnPanel.add(currentImageLabel);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(null);
		infoPanel.setBorder(BorderFactory.createTitledBorder("플레이어 정보"));
		infoPanel.setBounds(12, 195, 225, 108);
		infoPanel.setOpaque(false);
		itemTurnPanel.add(infoPanel);

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
		itemActionPanel.setBounds(12, 324, 225, 260);
		itemActionPanel.setOpaque(false);
		itemTurnPanel.add(itemActionPanel);

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
				useBooster(me);
			}
		});
		itemActionPanel.add(boosterButton);

		targetGuideLabel = new JLabel("공격 대상을 선택해 주세요");
		targetGuideLabel.setHorizontalAlignment(SwingConstants.CENTER);
		targetGuideLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 13));
		targetGuideLabel.setBounds(10, 74, 205, 20);
		itemActionPanel.add(targetGuideLabel);

		targetComboBox = new JComboBox<Player>();
		targetComboBox.setBounds(10, 102, 205, 28);
		itemActionPanel.add(targetComboBox);
		targetComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Player target = (Player) targetComboBox.getSelectedItem();
				targetHintLabel.setText(target == null ? "선택" : target.name);
			}
		});

		JPanel selectedTargetPanel = new JPanel();
		selectedTargetPanel.setLayout(new BorderLayout());
		selectedTargetPanel.setBounds(10, 144, 205, 53);
		selectedTargetPanel.setBorder(BorderFactory.createTitledBorder("선택한 공격 대상"));
		selectedTargetPanel.setOpaque(false);
		itemActionPanel.add(selectedTargetPanel);

		targetHintLabel = new JLabel("선택", SwingConstants.CENTER);
		selectedTargetPanel.add(targetHintLabel, BorderLayout.CENTER);

		itemMessagePanel = new JPanel();
		itemMessagePanel.setLayout(new BorderLayout());
		itemMessagePanel.setBounds(12, 324, 225, 260);
		itemMessagePanel.setBorder(BorderFactory.createTitledBorder("진행 안내"));
		itemMessagePanel.setOpaque(false);
		itemTurnPanel.add(itemMessagePanel);

		actionMessageTextArea = new JTextArea();
		actionMessageTextArea.setEditable(false);
		actionMessageTextArea.setOpaque(false);
		actionMessageTextArea.setLineWrap(true);
		actionMessageTextArea.setWrapStyleWord(true);
		actionMessageTextArea.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
		actionMessageTextArea.setFocusable(false);
		itemMessagePanel.add(actionMessageTextArea, BorderLayout.CENTER);

		turnInfoLabel = new JLabel("주사위를 굴려주세요.");
		turnInfoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		turnInfoLabel.setBounds(850, 10, 364, 14);

		JLabel rivalsTitleLabel = new JLabel("전체 플레이어");
		rivalsTitleLabel.setBounds(273, 10, 160, 14);
		contentPane.add(rivalsTitleLabel);

		rivalsPanel = new JPanel();
		rivalsPanel.setLayout(null);
		rivalsPanel.setBounds(273, 34, 944, 622);
		rivalsPanel.setOpaque(false);
		contentPane.add(rivalsPanel);
		createRivalViews();

		rollButton = new JButton("주사위 굴리기");
		rollButton.setBounds(12, 676, 127, 37);
		rollButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rollDiceTurn();
			}
		});
		contentPane.add(rollButton);

		resetButton = new JButton("초기화");
		resetButton.setBounds(149, 676, 105, 37);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetGame();
			}
		});
		contentPane.add(resetButton);

		setMyItemButtonsEnabled(false);
	}

	private void createRivalViews() {
		rivalsPanel.removeAll();
		rivalViews.clear();

		int addedCount = 0;
		for (Player player : players) {
			PlayerView view = new PlayerView(player);
			view.panel.setBounds(0, addedCount * 84, 944, 80);
			rivalViews.add(view);
			rivalsPanel.add(view.panel);
			addedCount++;
			if (addedCount == PLAYER_COUNT) {
				break;
			}
		}
	}

	private void rollDiceTurn() {
		if (itemPhase) {
			JOptionPane.showMessageDialog(this, "이번 턴 아이템 사용이 끝난 뒤 다시 굴릴 수 있어요.");
			return;
		}

		StringBuilder diceMessage = new StringBuilder();
		for (Player player : players) {
			int dice = random.nextInt(DICE_MAX - DICE_MIN + 1) + DICE_MIN;
			player.addDiceDistance(dice);
			diceMessage.append(player.name).append(" ").append(dice).append("칸  ");
		}

		animateProgressBars(new Runnable() {
			public void run() {
				for (Player player : players) {
					player.distanceAfterDice = player.distance;
				}
				currentItemUsers.clear();
				currentItemUsers.addAll(getItemUsersAfterDice());
				refreshScreen();

				Player winner = findWinner();
				if (winner != null) {
					openResultPage(winner);
					return;
				}

				itemPhase = true;
				turnInfoLabel.setText(diceMessage.toString());
				runItemPhase();
			}
		});
	}

	private List<Player> getItemUsersAfterDice() {
		List<Player> rankedPlayers = new ArrayList<Player>(players);
		rankedPlayers.sort(new Comparator<Player>() {
			public int compare(Player first, Player second) {
				return second.distanceAfterDice - first.distanceAfterDice;
			}
		});

		Player firstPlace = rankedPlayers.get(0);
		List<Player> itemUsers = new ArrayList<Player>();

		if (me != firstPlace) {
			itemUsers.add(me);
		}

		for (Player player : players) {
			if (player != me && player != firstPlace) {
				itemUsers.add(player);
			}
		}

		return itemUsers;
	}

	private void runItemPhase() {
		itemUserIndex = 0;
		processCurrentItemUser();
	}

	private void processCurrentItemUser() {
		if (!itemPhase || itemUserIndex >= currentItemUsers.size()) {
			finishItemPhase();
			return;
		}

		Player player = currentItemUsers.get(itemUserIndex);
		showCurrentItemPlayer(player);

		if (player == me) {
			turnInfoLabel.setText("내 아이템을 선택하세요.");
			setMyItemButtonsEnabled(true);
			return;
		}

		setMyItemButtonsEnabled(false);
		String actionMessage = useRandomItem(player);
		showItemMessage(actionMessage);

		Player winner = findWinner();
		if (winner != null) {
			openResultPage(winner);
			return;
		}

		Timer nextPlayerTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((Timer) e.getSource()).stop();
				itemUserIndex++;
				processCurrentItemUser();
			}
		});
		nextPlayerTimer.setRepeats(false);
		nextPlayerTimer.start();
	}

	private void attackByMe() {
		Player target = (Player) targetComboBox.getSelectedItem();
		if (!itemPhase || !attackButton.isEnabled()) {
			return;
		}
		if (target == null) {
			JOptionPane.showMessageDialog(this, "공격 대상을 선택해주세요.");
			return;
		}

		setMyItemButtonsEnabled(false);
		attack(me, target, true, new Runnable() {
			public void run() {
				itemUserIndex++;
				processCurrentItemUser();
			}
		});
	}

	private void finishMyItemAndRunOthers() {
		setMyItemButtonsEnabled(false);
		itemUserIndex++;
		processCurrentItemUser();
	}

	private String useRandomItem(Player attacker) {
		if (random.nextBoolean()) {
			Player target = chooseAttackTarget(attacker);
			if (target != null) {
				attack(attacker, target, false);
				return attacker.name + "가 " + target.name + "을(를) 공격합니다.";
			}
		}
		useBooster(attacker);
		return attacker.name + "가 부스터를 선택했습니다. 2칸 앞으로 갑니다.";
	}

	private Player chooseAttackTarget(Player attacker) {
		List<Player> targets = new ArrayList<Player>();
		for (Player player : players) {
			if (player != attacker) {
				targets.add(player);
			}
		}
		if (targets.isEmpty()) {
			return null;
		}
		return targets.get(random.nextInt(targets.size()));
	}

	private void attack(Player attacker, Player target, boolean showAttackFrame) {
		attack(attacker, target, showAttackFrame, null);
	}

	private void attack(Player attacker, Player target, boolean showAttackFrame, Runnable afterAttackFrameClosed) {
		int beforeTargetDistance = target.distance;
		int afterTargetDistance = Math.max(0, target.distance - ITEM_DISTANCE);
		if (showAttackFrame) {
			Play attackFrame = new Play(this);
			attackFrame.showAttack(attacker.name, attacker.distance, target.name,
					beforeTargetDistance, afterTargetDistance);
			attackFrame.setAttackAction(new Runnable() {
				public void run() {
					applyAttackResult(target, afterTargetDistance);
					Player winner = findWinner();
					if (winner != null) {
						openResultPage(winner);
					}
				}
			});
			if (afterAttackFrameClosed != null) {
				attackFrame.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						if (itemPhase) {
							afterAttackFrameClosed.run();
						}
					}
				});
			}
			attackFrame.setVisible(true);
		} else {
			applyAttackResult(target, afterTargetDistance);
		}
	}

	private void applyAttackResult(Player target, int afterTargetDistance) {
		target.distance = afterTargetDistance;
		target.displayDistance = target.distance;
		refreshScreen();
	}

	private void useBooster(Player player) {
		if (player == me && (!itemPhase || !boosterButton.isEnabled())) {
			return;
		}
		player.move(ITEM_DISTANCE);
		refreshScreen();
		if (player == me) {
			finishMyItemAndRunOthers();
		}
	}

	private void finishItemPhase() {
		itemPhase = false;
		currentItemUsers.clear();
		itemUserIndex = 0;
		setMyItemButtonsEnabled(false);
		clearCurrentItemPlayer();
		turnInfoLabel.setText("턴 종료. 주사위를 굴려주세요.");

		Player winner = findWinner();
		if (winner != null) {
			openResultPage(winner);
		}
	}

	private void showCurrentItemPlayer(Player player) {
		setPlayerImage(currentImageLabel, player, 140, 138);
		currentNameValueLabel.setText(player.name);
		currentDistanceValueLabel.setText(player.distance + " / " + GOAL_DISTANCE);
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

	private void showMyItemControls(boolean visible) {
		itemActionPanel.setVisible(visible);
		itemMessagePanel.setVisible(!visible);
	}

	private void showItemMessage(String message) {
		actionMessageTextArea.setText(message);
		actionMessageTextArea.setCaretPosition(0);
		showMyItemControls(false);
	}

	private void animateProgressBars(Runnable afterAnimation) {
		Timer timer = new Timer(15, null);
		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean allArrived = true;
				for (Player player : players) {
					if (player.displayDistance < player.distance) {
						player.displayDistance++;
						allArrived = false;
					}
				}
				refreshScreen();
				if (allArrived) {
					timer.stop();
					afterAnimation.run();
				}
			}
		});
		timer.start();
	}

	private Player findWinner() {
		for (Player player : players) {
			if (player.distance >= GOAL_DISTANCE) {
				return player;
			}
		}
		return null;
	}

	private void openAttackPage(Player attacker, Player target) {
		JFrame attackFrame = new JFrame("공격 장면");
		attackFrame.setSize(360, 180);
		attackFrame.setLocationRelativeTo(this);
		attackFrame.setLayout(new GridLayout(1, 2));
		attackFrame.add(createSimplePlayerPanel("공격자", attacker));
		attackFrame.add(createSimplePlayerPanel("공격 타겟", target));
		attackFrame.setVisible(true);
	}

	private void openResultPage(Player winner) {
		itemPhase = false;
		rollButton.setEnabled(false);
		setMyItemButtonsEnabled(false);

		String resultPageClassName = winner == me ? "smarthome.승리엔딩" : "smarthome.패배엔딩";
		if (!openEndingPage(resultPageClassName, me.name)) {
			String title = winner == me ? "위너 페이지" : "루저 페이지";
			String message = winner == me ? "승리했습니다!" : winner.name + " 승리. 패배했습니다.";
			JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		dispose();
	}

	private boolean openEndingPage(String className, String animalName) {
		try {
			Class<?> pageClass = Class.forName(className);
			Object page = pageClass.getDeclaredConstructor(String.class).newInstance(animalName);
			if (page instanceof JFrame) {
				((JFrame) page).setVisible(true);
				return true;
			}
		} catch (Exception exception) {
			return false;
		}
		return false;
	}

	private JPanel createSimplePlayerPanel(String title, Player player) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder(title));
		JLabel imageLabel = new JLabel();
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		setPlayerImage(imageLabel, player, 90, 80);
		panel.add(imageLabel, BorderLayout.CENTER);
		panel.add(new JLabel(player.name, SwingConstants.CENTER), BorderLayout.SOUTH);
		return panel;
	}

	private void resetGame() {
		dispose();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				if (!openFrameIfAvailable("smarthome.start")) {
					new Main(createSamplePlayers()).setVisible(true);
				}
			}
		});
	}

	private boolean openFrameIfAvailable(String className) {
		try {
			Class<?> pageClass = Class.forName(className);
			Object page = pageClass.getDeclaredConstructor().newInstance();
			if (page instanceof JFrame) {
				((JFrame) page).setVisible(true);
				return true;
			}
		} catch (Exception exception) {
			return false;
		}
		return false;
	}

	private void refreshScreen() {
		Object selectedTarget = targetComboBox.getSelectedItem();
		DefaultComboBoxModel<Player> comboModel = new DefaultComboBoxModel<Player>();
		for (Player player : players) {
			if (player != me) {
				comboModel.addElement(player);
			}
		}
		targetComboBox.setModel(comboModel);
		if (selectedTarget instanceof Player) {
			targetComboBox.setSelectedItem(selectedTarget);
		}

		for (PlayerView view : rivalViews) {
			view.refresh();
		}

		if (itemPhase && itemUserIndex < currentItemUsers.size()) {
			Player currentPlayer = currentItemUsers.get(itemUserIndex);
			currentDistanceValueLabel.setText(currentPlayer.distance + " / " + GOAL_DISTANCE);
		}
	}

	private void setMyItemButtonsEnabled(boolean enabled) {
		attackButton.setEnabled(enabled);
		boosterButton.setEnabled(enabled);
		targetComboBox.setEnabled(enabled);
		rollButton.setEnabled(!enabled && !itemPhase);
		showMyItemControls(enabled);
	}

	private void setPlayerImage(JLabel label, Player player, int width, int height) {
		if (player.imagePath == null || player.imagePath.trim().isEmpty()) {
			label.setIcon(null);
			label.setText(player.name);
			return;
		}

		ImageIcon icon = new ImageIcon(player.imagePath);
		if (icon.getIconWidth() <= 0) {
			label.setIcon(null);
			label.setText(player.name);
			return;
		}

		Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		label.setText("");
		label.setIcon(new ImageIcon(image));
	}

	private Player createPlayer(Object selectedPlayer) {
		if (selectedPlayer instanceof Player) {
			return (Player) selectedPlayer;
		}

		String animalName = selectedPlayer == null ? "플레이어" : selectedPlayer.getClass().getSimpleName();
		return new Player(animalName, getAnimalImagePath(animalName));
	}

	private String getAnimalImagePath(String animalName) {
		if ("코끼리".equals(animalName) || "원숭이".equals(animalName) || "타조".equals(animalName)
				|| "기린".equals(animalName) || "알파카".equals(animalName)) {
			return "src/채연/" + animalName + ".jpg";
		}
		return null;
	}

	private static class BackgroundPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private final Image backgroundImage;

		private BackgroundPanel(String imagePath) {
			backgroundImage = new ImageIcon(imagePath).getImage();
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (backgroundImage != null) {
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		}
	}

	private static List<Player> createSamplePlayers() {
		List<Player> samplePlayers = new ArrayList<Player>();
		samplePlayers.add(new Player("나", null));
		samplePlayers.add(new Player("플레이어1", null));
		samplePlayers.add(new Player("플레이어2", null));
		samplePlayers.add(new Player("플레이어3", null));
		samplePlayers.add(new Player("플레이어4", null));
		return samplePlayers;
	}

	public static class Player {
		private final String name;
		private final String imagePath;
		private int distance;
		private int displayDistance;
		private int distanceAfterDice;

		public Player(String name, String imagePath) {
			this.name = name;
			this.imagePath = imagePath;
		}

		private void move(int value) {
			distance = Math.min(GOAL_DISTANCE, distance + value);
			displayDistance = distance;
		}

		private void addDiceDistance(int value) {
			distance = Math.min(GOAL_DISTANCE, distance + value);
		}

		public String toString() {
			return name;
		}
	}

	private class PlayerView {
		private final Player player;
		private final JPanel panel;
		private final JLabel imageLabel;
		private final JLabel nameLabel;
		private final JLabel distanceValueLabel;
		private final JLabel startLabel;
		private final JLabel endLabel;
		private final JProgressBar progressBar;

		private PlayerView(Player player) {
			this.player = player;
			panel = new JPanel();
			panel.setLayout(null);
			panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			panel.setOpaque(false);

			imageLabel = new JLabel();
			imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
			imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			imageLabel.setBounds(10, 8, 115, 68);
			panel.add(imageLabel);

			nameLabel = new JLabel();
			nameLabel.setFont(new Font("Dialog", Font.BOLD, 13));
			nameLabel.setBounds(145, 8, 560, 20);
			panel.add(nameLabel);

			distanceValueLabel = new JLabel();
			distanceValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			distanceValueLabel.setBounds(785, 8, 110, 20);
			panel.add(distanceValueLabel);

			progressBar = new JProgressBar(0, GOAL_DISTANCE);
			progressBar.setStringPainted(true);
			progressBar.setBounds(145, 34, 750, 22);
			panel.add(progressBar);

			startLabel = new JLabel("0");
			startLabel.setBounds(145, 60, 40, 15);
			panel.add(startLabel);

			endLabel = new JLabel(String.valueOf(GOAL_DISTANCE), SwingConstants.RIGHT);
			endLabel.setBounds(855, 60, 40, 15);
			panel.add(endLabel);

			refresh();
		}

		private void refresh() {
			setPlayerImage(imageLabel, player, 115, 68);
			nameLabel.setText(player.name);
			progressBar.setValue(player.displayDistance);
			progressBar.setString(player.displayDistance + " / " + GOAL_DISTANCE);
			distanceValueLabel.setText("거리 " + player.distance + "칸");
		}
	}
}
