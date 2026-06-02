package 여진;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
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
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int GOAL_DISTANCE = 30;
	private static final int ITEM_DISTANCE = 2;
	private static final int DICE_MIN = 1;
	private static final int DICE_MAX = 6;
	private static final int RIVAL_COUNT = 5;

	private final JPanel contentPane;
	private final Random random = new Random();
	private final List<Player> players = new ArrayList<Player>();
	private final List<Player> currentItemUsers = new ArrayList<Player>();
	private final List<PlayerView> rivalViews = new ArrayList<PlayerView>();

	private Player me;
	private JLabel myImageLabel;
	private JLabel myNameValueLabel;
	private JLabel myDistanceValueLabel;
	private JLabel turnInfoLabel;
	private JProgressBar courseProgressBar;
	private JButton attackButton;
	private JButton boosterButton;
	private JButton rollButton;
	private JButton resetButton;
	private JComboBox<Player> targetComboBox;
	private JPanel rivalsPanel;
	private boolean itemPhase;

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
		setBounds(100, 100, 844, 631);
		contentPane = new JPanel();
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

		while (players.size() < RIVAL_COUNT + 1) {
			players.add(new Player("플레이어" + players.size(), null));
		}

		me = players.get(0);
	}

	private void createLayout() {
		JPanel myPanel = new JPanel();
		myPanel.setLayout(null);
		myPanel.setBorder(BorderFactory.createTitledBorder("내 캐릭터"));
		myPanel.setBounds(12, 10, 249, 463);
		contentPane.add(myPanel);

		myImageLabel = new JLabel();
		myImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		myImageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		myImageLabel.setBounds(52, 35, 140, 138);
		myPanel.add(myImageLabel);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(null);
		infoPanel.setBorder(BorderFactory.createTitledBorder("내 정보"));
		infoPanel.setBounds(12, 195, 225, 108);
		myPanel.add(infoPanel);

		JLabel nameLabel = new JLabel("이름");
		nameLabel.setBounds(18, 38, 49, 15);
		infoPanel.add(nameLabel);

		JLabel distanceLabel = new JLabel("거리");
		distanceLabel.setBounds(18, 66, 49, 15);
		infoPanel.add(distanceLabel);

		myNameValueLabel = new JLabel();
		myNameValueLabel.setBounds(77, 38, 130, 15);
		infoPanel.add(myNameValueLabel);

		myDistanceValueLabel = new JLabel();
		myDistanceValueLabel.setBounds(77, 66, 130, 15);
		infoPanel.add(myDistanceValueLabel);

		attackButton = new JButton("공격하기");
		attackButton.setBounds(22, 324, 90, 63);
		attackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				attackByMe();
			}
		});
		myPanel.add(attackButton);

		boosterButton = new JButton("부스터 사용");
		boosterButton.setBounds(123, 324, 100, 63);
		boosterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				useBooster(me);
			}
		});
		myPanel.add(boosterButton);

		targetComboBox = new JComboBox<Player>();
		targetComboBox.setBounds(52, 401, 167, 22);
		myPanel.add(targetComboBox);

		JLabel courseTitleLabel = new JLabel("코스");
		courseTitleLabel.setBounds(273, 10, 49, 14);
		contentPane.add(courseTitleLabel);

		JPanel coursePanel = new JPanel();
		coursePanel.setLayout(null);
		coursePanel.setBounds(273, 29, 545, 91);
		contentPane.add(coursePanel);

		courseProgressBar = new JProgressBar(0, GOAL_DISTANCE);
		courseProgressBar.setStringPainted(true);
		courseProgressBar.setBounds(32, 38, 480, 27);
		coursePanel.add(courseProgressBar);

		JLabel startLabel = new JLabel("0");
		startLabel.setBounds(32, 14, 49, 14);
		coursePanel.add(startLabel);

		JLabel endLabel = new JLabel(String.valueOf(GOAL_DISTANCE));
		endLabel.setBounds(484, 14, 49, 14);
		coursePanel.add(endLabel);

		turnInfoLabel = new JLabel("주사위를 굴려주세요.");
		turnInfoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		turnInfoLabel.setBounds(510, 10, 308, 14);
		contentPane.add(turnInfoLabel);

		JLabel rivalsTitleLabel = new JLabel("다른 플레이어");
		rivalsTitleLabel.setBounds(273, 128, 160, 14);
		contentPane.add(rivalsTitleLabel);

		rivalsPanel = new JPanel();
		rivalsPanel.setLayout(null);
		rivalsPanel.setBounds(273, 152, 545, 432);
		contentPane.add(rivalsPanel);
		createRivalViews();

		rollButton = new JButton("주사위 굴리기");
		rollButton.setBounds(124, 493, 127, 37);
		rollButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rollDiceTurn();
			}
		});
		contentPane.add(rollButton);

		resetButton = new JButton("초기화");
		resetButton.setBounds(124, 540, 127, 37);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetGame();
			}
		});
		contentPane.add(resetButton);

		JPanel selectedTargetPanel = new JPanel();
		selectedTargetPanel.setLayout(new BorderLayout());
		selectedTargetPanel.setBounds(22, 483, 90, 101);
		selectedTargetPanel.setBorder(BorderFactory.createTitledBorder("타겟"));
		contentPane.add(selectedTargetPanel);

		JLabel targetHintLabel = new JLabel("선택", SwingConstants.CENTER);
		selectedTargetPanel.add(targetHintLabel, BorderLayout.CENTER);
		targetComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Player target = (Player) targetComboBox.getSelectedItem();
				targetHintLabel.setText(target == null ? "선택" : target.name);
			}
		});

		setMyItemButtonsEnabled(false);
	}

	private void createRivalViews() {
		rivalsPanel.removeAll();
		rivalViews.clear();

		int addedCount = 0;
		for (Player player : players) {
			if (player == me) {
				continue;
			}
			PlayerView view = new PlayerView(player);
			view.panel.setBounds(0, addedCount * 86, 545, 82);
			rivalViews.add(view);
			rivalsPanel.add(view.panel);
			addedCount++;
			if (addedCount == RIVAL_COUNT) {
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
		setMyItemButtonsEnabled(currentItemUsers.contains(me));

		for (Player player : currentItemUsers) {
			if (player == me) {
				turnInfoLabel.setText("내 아이템을 선택하세요.");
				return;
			}

			useRandomItem(player);
			Player winner = findWinner();
			if (winner != null) {
				openResultPage(winner);
				return;
			}
		}

		finishItemPhase();
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

		attack(me, target);
		finishMyItemAndRunOthers();
	}

	private void finishMyItemAndRunOthers() {
		setMyItemButtonsEnabled(false);
		boolean afterMe = false;

		for (Player player : currentItemUsers) {
			if (player == me) {
				afterMe = true;
				continue;
			}
			if (!afterMe) {
				continue;
			}

			useRandomItem(player);
			Player winner = findWinner();
			if (winner != null) {
				openResultPage(winner);
				return;
			}
		}

		finishItemPhase();
	}

	private void useRandomItem(Player attacker) {
		if (random.nextBoolean()) {
			Player target = chooseAttackTarget(attacker);
			if (target != null) {
				attack(attacker, target);
			}
		} else {
			useBooster(attacker);
		}
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

	private void attack(Player attacker, Player target) {
		target.distance = Math.max(0, target.distance - ITEM_DISTANCE);
		target.displayDistance = target.distance;
		if (!openFrameIfAvailable("혜주.Play")) {
			openAttackPage(attacker, target);
		}
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
		setMyItemButtonsEnabled(false);
		turnInfoLabel.setText("턴 종료. 주사위를 굴려주세요.");

		Player winner = findWinner();
		if (winner != null) {
			openResultPage(winner);
		}
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
				if (!openFrameIfAvailable("채연.Start_End_page")) {
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
		setPlayerImage(myImageLabel, me, 140, 138);
		myNameValueLabel.setText(me.name);
		myDistanceValueLabel.setText(me.distance + " / " + GOAL_DISTANCE);
		courseProgressBar.setValue(me.displayDistance);
		courseProgressBar.setString(me.displayDistance + " / " + GOAL_DISTANCE);

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
	}

	private void setMyItemButtonsEnabled(boolean enabled) {
		attackButton.setEnabled(enabled);
		boosterButton.setEnabled(enabled);
		targetComboBox.setEnabled(enabled);
		rollButton.setEnabled(!enabled && !itemPhase);
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

	private static List<Player> createSamplePlayers() {
		List<Player> samplePlayers = new ArrayList<Player>();
		samplePlayers.add(new Player("나", null));
		samplePlayers.add(new Player("플레이어1", null));
		samplePlayers.add(new Player("플레이어2", null));
		samplePlayers.add(new Player("플레이어3", null));
		samplePlayers.add(new Player("플레이어4", null));
		samplePlayers.add(new Player("플레이어5", null));
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

			imageLabel = new JLabel();
			imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
			imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			imageLabel.setBounds(10, 8, 115, 68);
			panel.add(imageLabel);

			nameLabel = new JLabel();
			nameLabel.setFont(new Font("Dialog", Font.BOLD, 13));
			nameLabel.setBounds(145, 8, 260, 20);
			panel.add(nameLabel);

			distanceValueLabel = new JLabel();
			distanceValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			distanceValueLabel.setBounds(415, 8, 110, 20);
			panel.add(distanceValueLabel);

			progressBar = new JProgressBar(0, GOAL_DISTANCE);
			progressBar.setStringPainted(true);
			progressBar.setBounds(145, 34, 380, 22);
			panel.add(progressBar);

			startLabel = new JLabel("0");
			startLabel.setBounds(145, 60, 40, 15);
			panel.add(startLabel);

			endLabel = new JLabel(String.valueOf(GOAL_DISTANCE), SwingConstants.RIGHT);
			endLabel.setBounds(485, 60, 40, 15);
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
